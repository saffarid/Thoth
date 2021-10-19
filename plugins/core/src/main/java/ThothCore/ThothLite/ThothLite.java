package ThothCore.ThothLite;

import ThothCore.ThothLite.DBData.DBData;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Composite;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Finishable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Storagable;
import ThothCore.ThothLite.DBData.DBDataElement.Storing;
import ThothCore.ThothLite.DBData.Tables.Data;
import ThothCore.ThothLite.Timer.ThothTimer;
import ThothCore.ThothLite.Timer.Traceable;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
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

    public List<? extends Identifiable> getDataFromTable(String tableName) {
        return dbData.getTable(tableName).getDatas();
    }

    /**
     * Вставка новых записей в таблицы БД.
     * @param tableName наименование таблицы для вставки новых записей.
     * @param datas добавляемые данные.
     * */
    public void insert(String tableName, List<? extends Identifiable> datas) throws SQLException {

        Data table = dbData.getTable(tableName);

        for (Identifiable data : datas) {
            //Если объект обладает свойствои составного проверяем его состав на наличие записей в БД
            if (data instanceof Composite) {
                Composite composite = (Composite) data;

                Data products = dbData.getTable(StructureDescription.Products.TABLE_NAME);
                List<Storagable> datasProducts = new LinkedList<>();

                for (Storing storing : composite.getComposition()){
                    Storagable storagable = storing.getStoragable();
                    if(!products.contains(storagable)){
                        datasProducts.add(storagable);
                    }
                }

                if(!datasProducts.isEmpty()){
                    database.insert(products.getName(), products.convertToMap(datasProducts));
                }

            }

        }

        database.insert( table.getName(), table.convertToMap(datas));

    }



    /**
     * Подписка на публикацию наступления плановой даты завершения заказа
     */
    public void orderSubscribe(Flow.Subscriber<Finishable> subscriber) {
        watcherOrdersFinish.subscribe(subscriber);
    }

    /**
     * Подписка на публикацию наступления даты получения покупки
     */
    public void purchasesSubscribe(Flow.Subscriber<Finishable> subscriber) {
        watcherPurchasesFinish.subscribe(subscriber);
    }


}
