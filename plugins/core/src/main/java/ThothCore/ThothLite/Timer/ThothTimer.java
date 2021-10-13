package ThothCore.ThothLite.Timer;

import ThothCore.ThothLite.Finishable;

import java.text.DateFormat;
import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;

public class ThothTimer
        implements Traceable, NotifySubscribers {

    private final int POOL_SIZE = 5;

    private SubmissionPublisher publisher;
    private HashMap<Finishable, ScheduledFuture> taskMap;
    private List<Finishable> table;

    public ThothTimer() {
        publisher = new SubmissionPublisher(ForkJoinPool.commonPool(), 5);
        taskMap = new HashMap<>();
        table = new LinkedList<>();

    }

    @Override
    public void subscribe(Flow.Subscriber subscriber) {
        publisher.subscribe(subscriber);
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
//                        finishable, poolExecutor.schedule(scheduledTask, daysDelay, TimeUnit.MILLISECONDS)
                );
            }else {
                poolExecutor.schedule(scheduledTask, 2, TimeUnit.SECONDS);
//                poolExecutor.execute(scheduledTask);
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
        publisher.submit(finishable);
    }
}
