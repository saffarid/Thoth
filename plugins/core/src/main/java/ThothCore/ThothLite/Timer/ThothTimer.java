package ThothCore.ThothLite.Timer;

import ThothCore.ThothLite.Finishable;

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
    private List<Finishable> table;

    public ThothTimer() {
        publisher = new SubmissionPublisher();
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
            LocalDate finishDate = LocalDate.parse(
                    DateFormat.getDateInstance().format(
                            finishable.finishDate()
                    )
            );

            ScheduledTask scheduledTask = new ScheduledTask(this::notifySubscribers, finishable);

            int daysDelay = Period.between(finishDate, currentDate).getDays() + 1;

            if(daysDelay == 0){
                poolExecutor.execute(scheduledTask);
            }else {
                taskMap.put(
                        finishable, poolExecutor.schedule(scheduledTask, daysDelay, TimeUnit.DAYS)
                );
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
