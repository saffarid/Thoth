package ThothCore.ThothLite;

import ThothCore.ThothLite.DBData.DBData;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.*;
import ThothCore.ThothLite.DBData.Tables.Data;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothCore.ThothLite.DBLiteStructure.FullStructure.StructureDescription;
import ThothCore.ThothLite.Exceptions.NotContainsException;
import ThothCore.ThothLite.Timer.ThothTimer;
import ThothCore.ThothLite.Timer.Traceable;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Flow;

import static ThothCore.ThothLite.DBLiteStructure.AvaliableTables.*;

public class ThothLite {

    private static ThothLite thoth;

    private DataBaseLite database;
    private DBData dbData;

    private Traceable watcherPurchasesFinish;
    private Traceable watcherOrdersFinish;

    private ThothLite()
            throws SQLException, ClassNotFoundException, NotContainsException {

        dbData = DBData.getInstance();
        database = new DataBaseLite();

        watcherPurchasesFinish = new ThothTimer();
        watcherOrdersFinish = new ThothTimer();

        watcherPurchasesFinish.setTraceableObjects(dbData.getTable(StructureDescription.Purchases.TABLE_NAME).getDatas());
        watcherOrdersFinish.setTraceableObjects(dbData.getTable(StructureDescription.Orders.TABLE_NAME).getDatas());

    }

    /**
     * @param table запрашиваемая таблица.
     * @return список содержимого таблицы.
     */
    public List<? extends Identifiable> getDataFromTable(AvaliableTables table)
            throws NotContainsException {
        return dbData.getTable(getTableName(table)).getDatas();
    }

    public static ThothLite getInstance()
            throws SQLException, NotContainsException, ClassNotFoundException {
        if (thoth == null){
            thoth = new ThothLite();
        }
        return thoth;
    }

    public String getTableName(AvaliableTables table){
        switch (table){
            case COUNT_TYPES:{
                return StructureDescription.CountTypes.TABLE_NAME;
            }
            case CURRENCIES:{
                return StructureDescription.Currency.TABLE_NAME;
            }
            case INCOMES_TYPES:{
                return StructureDescription.IncomeTypes.TABLE_NAME;
            }
            case ORDERABLE:{
                return StructureDescription.Orders.TABLE_NAME;
            }
            case ORDER_STATUS:{
                return StructureDescription.OrderStatus.TABLE_NAME;
            }
            case PARTNERS:{
                return StructureDescription.Partners.TABLE_NAME;
            }
            case PROJECTABLE:{
                return StructureDescription.ProjectsList.TABLE_NAME;
            }
            case PRODUCT_TYPES:{
                return StructureDescription.ProductTypes.TABLE_NAME;
            }
            case PURCHASABLE:{
                return StructureDescription.Purchases.TABLE_NAME;
            }
            case STORAGABLE:{
                return StructureDescription.Products.TABLE_NAME;
            }
            case STORING:{
                return StructureDescription.Storage.TABLE_NAME;
            }
            default: return null;
        }
    }

    /**
     * Вставка новых записей в таблицы БД.
     *
     * @param tableName наименование таблицы для вставки новых записей.
     * @param datas     добавляемые данные.
     */
    public void insertToTable(String tableName, List<? extends Identifiable> datas)
            throws SQLException, NotContainsException {

        Data table = dbData.getTable(tableName);

        for (Identifiable data : datas) {
            //Если объект обладает свойствои составного проверяем его состав на наличие записей в БД
            if (data instanceof Composite) {
                Composite composite = (Composite) data;

                Data products = dbData.getTable(StructureDescription.Products.TABLE_NAME);
                List<Storagable> datasProducts = new LinkedList<>();

                for (Storing storing : composite.getComposition()) {
                    Storagable storagable = storing.getStoragable();
                    if (!products.contains(storagable)) {
                        datasProducts.add(storagable);
                    }
                }

                if (!datasProducts.isEmpty()) {
                    database.insert(products.getName(), products.convertToMap(datasProducts));
                }

            }

        }

        database.insert(table.getName(), table.convertToMap(datas));

    }

    /**
     * Подписка на публикацию наступления плановой даты завершения заказа
     */
    public void orderSubscribe(Flow.Subscriber<Finishable> subscriber) {
        watcherOrdersFinish.subscribe(subscriber);
    }

    public void purchaseComplete(String purchaseId)
            throws NotContainsException, SQLException {

        Data purchasesTable = dbData.getTable(StructureDescription.Purchases.TABLE_NAME);

        List<Purchasable> purchasableList = new LinkedList<>();
        purchasableList.add( (Purchasable) purchasesTable.getById(purchaseId) );

        database.update( purchasesTable.getName(), purchasesTable.convertToMap(purchasableList) );

        /*
        * if (продукт НЕ ХРАНИТСЯ в считанной БД и в SQLite){
        *   Вставляем новые записи в БД
        * }else{
        *   Обновляем существующие записи
        * }
        * */

    }

    /**
     * Подписка на публикацию наступления даты получения покупки
     */
    public void purchasesSubscribe(Flow.Subscriber<Finishable> subscriber) {
        watcherPurchasesFinish.subscribe(subscriber);
    }

    /**
     * Функция удаляет данные из таблицы.
     *
     * @param tableName наименование таблицы из которой удаляются записи.
     * @param datas     удаляемые записи.
     */
    public void removeFromTable(String tableName, List<? extends Identifiable> datas)
            throws SQLException, NotContainsException {
        Data table = dbData.getTable(tableName);
        database.remove(table.getName(), table.convertToMap(datas));
    }

    /**
     * Функция обновляет записи в таблице.
     *
     * @param tableName наименование таблицы в которой обновляются записи.
     * @param datas     обновлённые записи.
     */
    public void updateInTable(String tableName, List<? extends Identifiable> datas)
            throws SQLException, NotContainsException {
        Data table = dbData.getTable(tableName);
        database.update(tableName, table.convertToMap(datas));
    }

}
