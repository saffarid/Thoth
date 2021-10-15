package ThothCore.ThothLite.Timer;

import ThothCore.ThothLite.DBData.DBDataElement.Finishable;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.*;

public class ThothTimer
        implements Traceable, NotifySubscribers {

    private final int POOL_SIZE = 5;

    private SubmissionPublisher publisher;
    private HashMap<Finishable, ScheduledFuture> taskMap;
    private List<Finishable> buffer;

    public ThothTimer() {
        publisher = new SubmissionPublisher(ForkJoinPool.commonPool(), POOL_SIZE);
        taskMap = new HashMap<>();
        buffer = new LinkedList<>();
    }

    @Override
    public void subscribe(Flow.Subscriber subscriber) {
        boolean hadSubscribers = publisher.hasSubscribers();
        publisher.subscribe(subscriber);
        if(!hadSubscribers){
            for (Finishable finishable : buffer){
                notifySubscribers(finishable);
                buffer.remove(finishable);
            }
        }
    }

    @Override
    public void setTraceableObjects(List<Finishable> finishables) {
        ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(POOL_SIZE);
        LocalDate currentDate = LocalDate.now();

        for(Finishable finishable : finishables){
            String format = DateFormat.getDateInstance().format(
                    finishable.finishDate()
            );
            LocalDate finishDate = new java.sql.Date(finishable.finishDate().getTime()).toLocalDate();

            ScheduledTask scheduledTask = new ScheduledTask(this::notifySubscribers, finishable);

            int daysDelay = Period.between(currentDate, finishDate).getDays();

            if( (daysDelay > 0) && (finishDate.isAfter(currentDate)) ){
                taskMap.put(
                        finishable, poolExecutor.schedule(scheduledTask, daysDelay, TimeUnit.DAYS)
                );
            }else {
                poolExecutor.schedule(scheduledTask, 2, TimeUnit.SECONDS);
            }
        }
    }

    @Override
    public void close(Finishable finishable) {

        taskMap.get(finishable).cancel(false);
        taskMap.remove(finishable);
        finishable.finish();

    }

    @Override
    public void notifySubscribers(Finishable finishable) {
        if(publisher.hasSubscribers()){
            publisher.submit(finishable);
        }else{
            buffer.add(finishable);
        }
    }
}
