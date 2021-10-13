package ThothCore.ThothLite;

import ThothCore.ThothLite.DBData.DBData;
import ThothCore.ThothLite.DBData.DBDataElement.Order;
import ThothCore.ThothLite.DBData.DBDataElement.Purchase;
import ThothCore.ThothLite.DBLiteStructure.StructureDescription;
import ThothCore.ThothLite.Timer.ThothTimer;
import ThothCore.ThothLite.Timer.Traceable;

import java.sql.SQLException;
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

    public void purchasesSubscribe(Flow.Subscriber<Finishable> subscriber){
        watcherPurchasesFinish.subscribe(subscriber);
    }

    public void orderSubscribe(Flow.Subscriber<Finishable> subscriber){
        watcherOrdersFinish.subscribe(subscriber);
    }
}
