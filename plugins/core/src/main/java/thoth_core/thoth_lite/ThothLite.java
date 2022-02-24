package thoth_core.thoth_lite;

import thoth_core.thoth_lite.config.Config;
import thoth_core.thoth_lite.config.Configuration;
import thoth_core.thoth_lite.config.PeriodAutoupdateDatabase;
import thoth_core.thoth_lite.db_data.DBData;
import thoth_core.thoth_lite.db_data.db_data_element.implement.FinancialOperation;
import thoth_core.thoth_lite.db_data.db_data_element.properties.*;
import thoth_core.thoth_lite.db_data.db_data_element.properties.parts.Composite;
import thoth_core.thoth_lite.db_data.tables.Data;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_core.thoth_lite.db_lite_structure.full_structure.StructureDescription;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_core.thoth_lite.timer.CheckerFinishable;
import thoth_core.thoth_lite.timer.Traceable;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
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

        try {
            config = Config.getInstance();
        } catch (IOException e) {
            LOG.log(Level.INFO, "Ошибка открытия файла конфигурации.");
        } catch (ParseException e) {
            LOG.log(Level.INFO, "Ошибка чтения файла конфигурации.");
        }
        //Инициализация пустой локальной БД
        dbData = DBData.getInstance();
        //Инициализация системы оповещения
        watcherPurchasesFinish = new CheckerFinishable();
        watcherOrdersFinish = new CheckerFinishable();
        DBData.getInstance().getTable(getTableName(AvaliableTables.PURCHASABLE)).subscribe((Flow.Subscriber) watcherPurchasesFinish);

        //Инициализация и считывание БД
        database = new DataBaseLite();

        reReader = new ReReadDatabase();
//        periodReReadDb = new ScheduledThreadPoolExecutor(1);
//        scheduledFutureReReadDb = periodReReadDb.scheduleWithFixedDelay(reReader, 5, 5, TimeUnit.SECONDS);

    }

    public void acceptPurchase(Purchasable purchasable) throws NotContainsException, SQLException {

        /*   Приём покупки включает в себя:
         *  1. Обновление записей в таблице покупок - установка флага isDelivered.
         *  2. Обновление записей в таблице продуктов - обновление кол-ва продукта.
         * */
        //Определим список продуктов и обновим их кол-во.
        List<Storagable> listStoragable = new LinkedList<>();
        ;
        for (Storing storing : purchasable.getComposition()) {
            Storagable storagable = storing.getStoragable();
            /*--- При обновлении цены, необходимо проверять совпадение цены в БД с считанными данными. ---*/
            storagable.setCount(storagable.getCount() + storing.getCount());
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

    /**
     * Отмена периодического перечитывания базы
     */
    public void cancelAutoReReadDb() {
        LOG.log(Level.INFO, "Отменяем старую задачу");
        scheduledFutureReReadDb.cancel(false);
        LOG.log(Level.INFO, "Старая задача отменена");
    }

    /**
     * Изменение периода перечитывания базы
     */
    public void changeDelayReReadDb() {
        PeriodAutoupdateDatabase newDelay = config.getDatabase().getPeriod();
        if (config.getDatabase().isAutoupdate()) {
            cancelAutoReReadDb();
            if (newDelay != PeriodAutoupdateDatabase.NEVER) {
                LOG.log(Level.INFO, "Запускаем новую задачу");
                scheduledFutureReReadDb = periodReReadDb.scheduleWithFixedDelay(reReader, 5, newDelay.getValue(), TimeUnit.MINUTES);
            }
        }
    }

    /**
     * Функция завершает выполнение всех процессов
     */
    public void close() {
        config.exportConfig();
        LOG.log(Level.INFO, "Good bye, my friend. I will miss you.");
    }

    public void forceRereadDatabase()
            throws SQLException, ClassNotFoundException {
        database.readDataBase();
    }

    /**
     * Функция возвращает конфигурацию системы в формате json.
     */
    public Configuration getConfig() {
        return config;
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
        if (thoth == null) {
            thoth = new ThothLite();
        }
        return thoth;
    }

    public String getTableName(AvaliableTables table) {
        switch (table) {
            case COUNT_TYPES: {
                return StructureDescription.CountTypes.TABLE_NAME;
            }
            case EXPENSES_TYPES: {
                return StructureDescription.ExpensesTypes.TABLE_NAME;
            }
            case INCOMES_TYPES: {
                return StructureDescription.IncomesTypes.TABLE_NAME;
            }
            case ORDER_STATUS: {
                return StructureDescription.OrderStatus.TABLE_NAME;
            }
            case PRODUCT_TYPES: {
                return StructureDescription.ProductTypes.TABLE_NAME;
            }
            case STORING: {
                return StructureDescription.Storage.TABLE_NAME;
            }

            case CURRENCIES: {
                return StructureDescription.Currency.TABLE_NAME;
            }
            case EXPENSES: {
                return StructureDescription.Expenses.TABLE_NAME;
            }
            case INCOMES: {
                return StructureDescription.Incomes.TABLE_NAME;
            }
            case ORDERABLE: {
                return StructureDescription.Orders.TABLE_NAME;
            }
            case PARTNERS: {
                return StructureDescription.Partners.TABLE_NAME;
            }
            case STORAGABLE: {
                return StructureDescription.Products.TABLE_NAME;
            }
            case PROJECTABLE: {
                return StructureDescription.ProjectsDesc.TABLE_NAME;
            }
            case PURCHASABLE: {
                return StructureDescription.Purchases.TABLE_NAME;
            }
            default:
                return null;
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
//            Если объект обладает свойством составного проверяем его состав на наличие записей в БД
            if (data instanceof Composite) {
                Composite composite = (Composite) data;
                //Запрашиваем состав
                Data products = dbData.getTable(StructureDescription.Products.TABLE_NAME);
                List<Storagable> datasProducts = new LinkedList<>(); //Список продуктов, входящих в состав
                List<FinancialAccounting> finOps = new LinkedList<>(); //Список финансовых операций
                //Проходим по составу и добавляем каждую запись в список
                for (Storing storing : composite.getComposition()) {
                    Storagable storagable = storing.getStoragable(); //Продукт
                    finOps.add(
                            new FinancialOperation(
                                    "-1", storagable.getType(), storing.getCount(), LocalDate.now(), storing.getCurrency(), storing.getCurrency().getCourse(), ""
                            )
                    );
                    if (!products.contains(storagable)) {
                        datasProducts.add(storagable);
                    }
                }
                //Добавляем записи
                if (!datasProducts.isEmpty()) {
                    HashMap<String, List<HashMap<String, Object>>> compositeData = products.convertToMap(datasProducts);
                    for (String tableForInsert : compositeData.keySet()) {
                        database.insert(tableForInsert, compositeData.get(tableForInsert));
                    }
                }
                if (data instanceof Purchasable) {
                    HashMap<String, List<HashMap<String, Object>>> hashMap = DBData.getInstance().getTable(StructureDescription.Expenses.TABLE_NAME).convertToMap(finOps);
                    for (String key : hashMap.keySet()) {
                        database.insert(
                                StructureDescription.Expenses.TABLE_NAME,
                                hashMap.get(key)
                        );
                    }
                }
            } /////
        }

        if(tableName.equals(StructureDescription.ProductTypes.TABLE_NAME)){
            HashMap<String, List<HashMap<String, Object>>> expTypes = DBData.getInstance().getTable(StructureDescription.ExpensesTypes.TABLE_NAME).convertToMap(datas);
            HashMap<String, List<HashMap<String, Object>>> incTypes = DBData.getInstance().getTable(StructureDescription.IncomesTypes.TABLE_NAME).convertToMap(datas);

            for (String tableForInsert : expTypes.keySet()) {
                database.insert(tableForInsert, expTypes.get(tableForInsert));
            }
            for (String tableForInsert : incTypes.keySet()) {
                database.insert(tableForInsert, incTypes.get(tableForInsert));
            }
        }

        HashMap<String, List<HashMap<String, Object>>> data = table.convertToMap(datas);
        for (String tableForInsert : data.keySet()) {
            database.insert(tableForInsert, data.get(tableForInsert));
        }
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
        purchasableList.add((Purchasable) purchasesTable.getById(purchaseId));

        HashMap<String, List<HashMap<String, Object>>> datas = purchasesTable.convertToMap(purchasableList);
        for (String tableName : datas.keySet()) {
            database.update(tableName, datas.get(tableName));
        }

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
     */
    public void reReadDb() {
        if (config.getDatabase().isAutoupdate()) {
            if (config.getDatabase().isUpdateAfterTrans()) {
                LOG.log(Level.INFO, "enter to reReadDb");
                CompletableFuture.runAsync(reReader);
                LOG.log(Level.INFO, "exit from reReadDb");
            }
        }
    }

    /**
     * Функция удаляет данные из таблицы.
     *
     * @param table таблица из которой удаляются записи.
     * @param datas удаляемые записи.
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
        HashMap<String, List<HashMap<String, Object>>> data = dbData.getTable(tableName).convertToMap(datas);
        for (String name : data.keySet()) {
            database.remove(name, data.get(name));
        }
    }

    /**
     * Функция подписывает на изменения в таблице
     */
    public void subscribeOnTable(
            AvaliableTables table
            , Flow.Subscriber subscriber
    ) throws NotContainsException {
        DBData.getInstance().getTable(getTableName(table)).subscribe(subscriber);
    }

    public void updateInTable(AvaliableTables table, List<? extends Identifiable> datas)
            throws NotContainsException, SQLException {
        database.beginTransaction();
        updateInTable(getTableName(table), datas);
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
        HashMap<String, List<HashMap<String, Object>>> data = dbData.getTable(tableName).convertToMap(datas);
        for (String name : data.keySet()) {
            database.update(name, data.get(name));
        }
    }

    class ReReadDatabase
            implements Runnable {
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
