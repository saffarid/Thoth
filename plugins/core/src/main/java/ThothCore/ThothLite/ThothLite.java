package ThothCore.ThothLite;

import ThothCore.ThothLite.Config.Config;
import ThothCore.ThothLite.DBData.DBData;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.*;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Parts.Composite;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Finishable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;
import ThothCore.ThothLite.DBData.Tables.Data;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothCore.ThothLite.DBLiteStructure.FullStructure.StructureDescription;
import ThothCore.ThothLite.Exceptions.NotContainsException;
import ThothCore.ThothLite.Timer.ThothTimer;
import ThothCore.ThothLite.Timer.Traceable;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThothLite {

    public static Logger LOG;
    private static ThothLite thoth;
    private ScheduledFuture<?> scheduledFutureReReadDb;

    private Config config;

    private DataBaseLite database;
    private DBData dbData;

    private Traceable watcherPurchasesFinish;
    private Traceable watcherOrdersFinish;

    private ScheduledThreadPoolExecutor periodReReadDb;

    private Runnable reReader;

    static {
        LOG = Logger.getLogger(ThothLite.class.getName());
    }

    private ThothLite()
            throws SQLException, ClassNotFoundException, NotContainsException {

        dbData = DBData.getInstance();
        database = new DataBaseLite();

        watcherPurchasesFinish = new ThothTimer();
        watcherOrdersFinish = new ThothTimer();

        watcherPurchasesFinish.setTraceableObjects(dbData.getTable(StructureDescription.Purchases.TABLE_NAME).getDatas());
        watcherOrdersFinish.setTraceableObjects(dbData.getTable(StructureDescription.Orders.TABLE_NAME).getDatas());

        reReader = new ReReadDatabase();

        try {
            config = Config.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        periodReReadDb = new ScheduledThreadPoolExecutor(1);
//        scheduledFutureReReadDb = periodReReadDb.scheduleWithFixedDelay(reReader, 5, 5, TimeUnit.SECONDS);

    }

    public Config getConfig() {
        return config;
    }

    public void acceptPurchase(Purchasable purchasable) throws NotContainsException, SQLException {

        /*   Приём покупки включает в себя:
        *  1. Обновление записей в таблице покупок - установка флага isDelivered.
        *  2. Обновление записей в таблице продуктов - обновление кол-ва продукта.
        * */
        //Определим список продуктов и обновим их кол-во.
        List<Storagable> listStoragable = new LinkedList<>();;
        for(Storing storing : purchasable.getComposition()){
            Storagable storagable = storing.getStoragable();
            /*--- При обновлении цены, необходимо проверять совпадение цены в БД с считанными данными. ---*/
            storagable.setCount( storagable.getCount() + storing.getCount() );
            listStoragable.add(storagable);
        }

        //Упакуем покупку в список для дальнейшей обработки
        List<Purchasable> purchase = new LinkedList<>();
        purchase.add(purchasable);

        //Для атомарности операции стартуем транзакцию
        database.beginTransaction();
        updateInTable(getTableName(AvaliableTables.PURCHASABLE), purchase);
        updateInTable(getTableName(AvaliableTables.STORAGABLE), listStoragable);
        database.commitTransaction();
        //При удачном выполнении завершаем транзакцию
    }

    public void changeDelayReReadDb(PeriodAutoupdateDatabase newDelay){
        LOG.log(Level.INFO, "Отменяем старую задачу");
        scheduledFutureReReadDb.cancel(false);
        LOG.log(Level.INFO, "Старая задача отменена");
        if(newDelay != PeriodAutoupdateDatabase.NEVER) {
            LOG.log(Level.INFO, "Запускаем новую задачу");
            scheduledFutureReReadDb = periodReReadDb.scheduleWithFixedDelay(reReader, 5, newDelay.getPeriod(), TimeUnit.MINUTES);
        }
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

    public void insertToTable(AvaliableTables table, List<? extends Identifiable> datas)
            throws NotContainsException, SQLException, ClassNotFoundException {
        String tableName = getTableName(table);
        database.beginTransaction();
        insertToTable(tableName, datas);
        database.commitTransaction();
        database.readTable(tableName);
    }

    /**
     * Вставка новых записей в таблицы БД.
     * Отличие от public функции заключается в начале и закреплении транзакции.
     *
     * @param tableName наименование таблицы для вставки новых записей.
     * @param datas     добавляемые данные.
     */
    private void insertToTable(String tableName, List<? extends Identifiable> datas)
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
     * Функция для считывания БД
     * */
    public void reReadDb() {
        LOG.log(Level.INFO, "enter to reReadDb");
        CompletableFuture.runAsync(reReader);
        LOG.log(Level.INFO, "exit from reReadDb");
    }

    /**
     * Функция удаляет данные из таблицы.
     *
     * @param table  таблица из которой удаляются записи.
     * @param datas  удаляемые записи.
     */
    public void removeFromTable(AvaliableTables table, List<? extends Identifiable> datas)
            throws SQLException, NotContainsException, ClassNotFoundException {
        database.beginTransaction();
        String tableName = getTableName(table);
        removeFromTable(tableName, datas);
        database.commitTransaction();
        database.readTable(tableName);
    }

    /**
     * Функция удаляет данные из таблицы.
     * Отличие от public функции заключается в начале и закреплении транзакции.
     *
     * @param tableName наименование таблицы из которой удаляются записи.
     * @param datas     удаляемые записи.
     */
    private void removeFromTable(String tableName, List<? extends Identifiable> datas)
            throws SQLException, NotContainsException {
        Data table = dbData.getTable(tableName);
        database.remove(table.getName(), table.convertToMap(datas));
    }

    /**
     * Функция подписывает на изменения в таблице
     * */
    public void subscribeOnTable(
            AvaliableTables table
            , Flow.Subscriber subscriber
    ) throws NotContainsException {
        DBData.getInstance().getTable(getTableName(table)).subscribe(subscriber);
    }

    public void updateInTable(AvaliableTables table, List<? extends Identifiable> datas)
            throws NotContainsException, SQLException {
        database.beginTransaction();
        updateInTable(getTableName(table),datas);
        database.commitTransaction();
    }

    /**
     * Функция обновляет записи в таблице.
     * Отличие от public функции заключается в начале и закреплении транзакции.
     *
     * @param tableName наименование таблицы в которой обновляются записи.
     * @param datas     обновлённые записи.
     */
    private void updateInTable(String tableName, List<? extends Identifiable> datas)
            throws SQLException, NotContainsException {
        Data table = dbData.getTable(tableName);
        database.update(tableName, table.convertToMap(datas));
    }

    class ReReadDatabase
            implements Runnable{
        @Override
        public void run() {
            try {
                LOG.log(Level.INFO, "start reRead");
                database.readDataBase();
                LOG.log(Level.INFO, "finish successful reRead");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
