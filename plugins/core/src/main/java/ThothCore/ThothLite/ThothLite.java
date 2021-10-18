package ThothCore.ThothLite;

import ThothCore.ThothLite.DBData.DBData;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Finishable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.Timer.ThothTimer;
import ThothCore.ThothLite.Timer.Traceable;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Flow;

public class ThothLite {

    private DataBaseLite database;
    private DBData dbData;

    private Traceable watcherPurchasesFinish;
    private Traceable watcherOrdersFinish;

    public ThothLite() throws SQLException, ClassNotFoundException {

        dbData = DBData.getInstance();
        database = new DataBaseLite();

        watcherPurchasesFinish = new ThothTimer();
        watcherOrdersFinish = new ThothTimer();

        watcherPurchasesFinish.setTraceableObjects(dbData.getTable(StructureDescription.Purchases.TABLE_NAME).getDatas());
        watcherOrdersFinish.setTraceableObjects(dbData.getTable(StructureDescription.Orders.TABLE_NAME).getDatas());

    }

    /**
     * Подписка на публикацию наступления плановой даты завершения заказа
     * */
    public void orderSubscribe(Flow.Subscriber<Finishable> subscriber){
        watcherOrdersFinish.subscribe(subscriber);
    }

    /**
     * Подписка на публикацию наступления даты получения покупки
     * */
    public void purchasesSubscribe(Flow.Subscriber<Finishable> subscriber){
        watcherPurchasesFinish.subscribe(subscriber);
    }

    public List<? extends Identifiable> getDataFromTable(String tableName){
        return dbData.getTable(tableName).getDatas();
    }


}
