package ThothCore.ThothLite.Timer;

import ThothCore.ThothLite.Config.Config;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Finishable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.Tables.Data;
import ThothCore.ThothLite.ThothLite;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class CheckerFinishable
        implements
        Traceable
        , Flow.Subscriber<List<Finishable>>
        , Closeable
{

    private final int POOL_SIZE = 5;

    private SubmissionPublisher publisher;
    private Flow.Subscription subscription;

    private HashMap<Finishable, ScheduledFuture> taskMap;
    private List<Finishable> buffer;
    private ScheduledThreadPoolExecutor poolExecutor;

    public CheckerFinishable() {
        publisher = new SubmissionPublisher(ForkJoinPool.commonPool(), POOL_SIZE);
        poolExecutor = new ScheduledThreadPoolExecutor(POOL_SIZE);
        taskMap = new HashMap<>();
        buffer = new LinkedList<>();
    }

    private boolean isFinishableNotificationPlanning(Identifiable identifiable){

        return taskMap.keySet()
                .stream()
                .filter( finishable -> ((Identifiable)finishable).getId().equals(identifiable.getId()) )
                .findAny()
                .isPresent();

    }

    /**
     * Функция распределяет запланированные задачи для оповещения наступления даты
     * */
    public void notificationPlanning(List<Finishable> finishables) {
        LocalDate currentDate = LocalDate.now();

        for(Finishable finishable : finishables){

            //Планируем задачу только если она ещё на запланирована
            if( !isFinishableNotificationPlanning( (Identifiable) finishable ) ){
                LocalDate finishDate = finishable.finishDate();
                int daysDelay = Period.between(currentDate, finishDate).getDays();

                Runnable task = () -> notifySubscribers(finishable);

                try {
                    if( (daysDelay > Config.getInstance().getDelivered().getDayBeforeDelivery().getDay()) && (finishDate.isAfter(currentDate)) ){
                        taskMap.put(
                                finishable, poolExecutor.schedule(task, daysDelay, TimeUnit.DAYS)
                        );
                    }else {
                        poolExecutor.schedule(task, 1, TimeUnit.SECONDS);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public void close(Finishable finishable) {
        //Завершаем выполнение задачи
        taskMap.get(finishable).cancel(false);
        //Удаляем из списка finishable-объект
        taskMap.remove(finishable);
        //Завершаем finishable-объект
        finishable.finish();
    }

    /* --- Функции публикатора --- */
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

    private void notifySubscribers(Finishable finishable) {
        if(publisher.hasSubscribers()){
            ThothLite.LOG.log(Level.INFO, "Публикация finishable");
            publisher.submit(finishable);
        }else{
            buffer.add(finishable);
        }
    }

    /* --- Функции подписчика на таблицу --- */
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(List<Finishable> item) {
        ThothLite.LOG.log(Level.INFO, "Получен новый finishable");
        notificationPlanning(item);
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
