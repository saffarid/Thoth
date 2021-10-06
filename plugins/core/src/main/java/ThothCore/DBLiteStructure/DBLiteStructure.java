package ThothCore.DBLiteStructure;

import Database.DataBaseManager;
import Database.DataBaseSQL;
import Database.Table;
import Database.TableColumn;

import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBLiteStructure extends DataBaseSQL {

    private static Logger LOG;

    public final String TABLE = "table";
    public final String PROJECT_TABLE = "project_table";
    public final String GUIDE = "guide";
    public final String CONSTANTS = "constants";
    public final String SYSTEM_TABLE = "system_table";
    public final String SYSTEM_GUIDE = "system_guide";
    public final String SYSTEM_CONSTANTS = "system_constants";

    public final String URL_DB = "db/storage.tho";

    static {
        LOG = Logger.getLogger(DBLiteStructure.class.getName());
    }

    public DBLiteStructure() throws SQLException, ClassNotFoundException {
        super();

        //Таблицы типа GUIDE
        tables.add(new CountTypes());
        tables.add(new ProductTypes());
        tables.add(new OrderStatus());
        tables.add(new Partners());
        tables.add(new IncomeTypes());
        //Таблица типа SYSTEM_GUIDE
        tables.add(new Currency());
        //Таблица типа TABLE
        tables.add(new Products());
        tables.add(new NotUsed());
        tables.add(new Storage());
        tables.add(new Purchases());
        tables.add(new Orders());
        tables.add(new ProjectsList());
        tables.add(new Incomes());

        if(!new File(URL_DB).exists()){
            firstInit();
        }else{

        }
    }

    /**
     * Таблица едениц измерения
     */
    class CountTypes extends Table{
        public static final String TABLE_NAME = "count_type";
        public static final String COUNT_TYPE = "count_type";
        public CountTypes() {
            super();
            name = TABLE_NAME;
            type = GUIDE;

            addColumn(new TableColumn(
                    COUNT_TYPE, "varchar(10)", true, true
            ));

        }
    }

    /**
     * Таблица типов продуктов
     */
    class ProductTypes extends Table{
        public static final String TABLE_NAME = "product_types";
        public static final String PRODUCT_TYPES = "product_types";

        public ProductTypes() {
            super();
            name = TABLE_NAME;
            type = GUIDE;

            addColumn(new TableColumn(
                    PRODUCT_TYPES, "varchar(255)", true, true
            ));
        }
    }

    /**
     * Таблица статусов заказа
     */
    class OrderStatus extends Table{
        public static final String TABLE_NAME = "order_status";
        public static final String ORDER_STATUS = "order_status";

        public OrderStatus() {
            super();
            name = TABLE_NAME;
            type = GUIDE;

            addColumn(new TableColumn(
                    ORDER_STATUS, "varchar(255)", true, true
            ));
        }
    }

    /**
     * Таблица партнеров
     */
    class Partners extends Table{
        public static final String TABLE_NAME = "partners";
        public static final String NAME = "name";
        public static final String PHONE = "phone";
        public static final String WEB = "web";

        public Partners() {
            super();
            name = TABLE_NAME;
            type = GUIDE;

            addColumn(new TableColumn(
                    NAME, "text", true, true
            ));
            addColumn(new TableColumn(
                    PHONE, "varchar(20)", false, false
            ));
            addColumn(new TableColumn(
                    WEB, "varchar(255)", false, false
            ));
        }
    }

    /**
     * Таблица категорий доходов
     */
    class IncomeTypes extends Table{

        public static final String TABLE_NAME = "income_types";
        public static final String INCOME_TYPE = "income_type";

        public IncomeTypes() {
            super();
            name = TABLE_NAME;
            type = GUIDE;

            addColumn(new TableColumn(
                    INCOME_TYPE, "varchar(255)", true, true
            ));
        }
    }

    /**
     * Таблица список валют
     * */
    class Currency extends Table{
        public static final String TABLE_NAME = "currency";
        public static final String CURRENCY = "currency";
        public static final String COURSE = "course";

        public Currency() {
            super();
            name = TABLE_NAME;
            type = SYSTEM_GUIDE;

            addColumn(new TableColumn(
                    CURRENCY, "varchar(100)", true, true
            ));
            addColumn(new TableColumn(
                    COURSE, "double(100, 2)", false, true
            ));
        }
    }

    /**
     * Таблица для хранения неиспользуемых продуктов
     * */
    class NotUsed extends Table{
        public static final String TABLE_NAME = "not_used";
        public static final String ARTICLE = "article";
        public static final String NAME = "name";
        public static final String CAUSE = "cause";
        public static final String PRODUCT_ID = "product_id";

        public NotUsed() {
            super();
            name = TABLE_NAME;
            type = DBLiteStructure.this.TABLE;

//            addColumn(new TableColumn(
//                    ARTICLE, "varchar(255)", true, true
//            ));
//            addColumn(new TableColumn(
//                    NAME, "varchar(255)", true, true
//            ));
            addColumn(new TableColumn(
                    PRODUCT_ID, "integer", true, true, getTable(Products.TABLE_NAME).getTableCol(Products.ID)
            ));
            addColumn(new TableColumn(
                    CAUSE, "text", false, false
            ));
        }
    }

    /**
     * Таблица для хранения продуктов
     */
    class Products extends Table{
        public static final String TABLE_NAME = "products";
        public static final String ARTICLE = "article";
        public static final String NAME = "name";
        public static final String PRODUCT_TYPE_ID = "product_type_id";
        public static final String PRICE = "price";
        public static final String CURRENCY_ID = "currency_id";

        public Products() {
            super();
            name = TABLE_NAME;
            type = DBLiteStructure.this.TABLE;

            addColumn(new TableColumn(
                    ARTICLE, "varchar(255)", true, true
            ));
            addColumn(new TableColumn(
                    NAME, "varchar(255)", false, true
            ));
            addColumn(new TableColumn(
                    PRODUCT_TYPE_ID, "integer", false, true, getTable(ProductTypes.TABLE_NAME).getTableCol(ProductTypes.PRODUCT_TYPES)
            ));
            addColumn(new TableColumn(
                    PRICE, "double(100000000, 2)", false, true
            ));
            addColumn(new TableColumn(
                    CURRENCY_ID, "integer", false, true, getTable(Currency.TABLE_NAME).getTableCol(Currency.CURRENCY)
            ));
        }
    }

    /**
     * Таблица для хранения содержимого склада
     */
    class Storage extends Table{
        public static final String TABLE_NAME = "storage";
        public static final String COUNT = "count";
        public static final String COUNT_TYPE_ID = "count_type_id";
        public static final String NOTE = "note";
        public static final String PRODUCT_ID = "product_id";

        public Storage() {
            super();
            name = TABLE_NAME;
            type = DBLiteStructure.this.TABLE;

            addColumn(new TableColumn(
                    COUNT, "integer", false, true
            ));
            addColumn(new TableColumn(
                    COUNT_TYPE_ID, "integer", false, true, getTable(CountTypes.TABLE_NAME).getTableCol(CountTypes.COUNT_TYPE)
            ));
            addColumn(new TableColumn(
                    NOTE, "text", false, false
            ));
            addColumn(new TableColumn(
                    PRODUCT_ID, "integer", true, true, getTable(Products.TABLE_NAME).getTableCol(Products.ID)
            ));
        }
    }

    /**
     * Таблица для хранения покупок
     */
    class Purchases extends Table{
        public static final String TABLE_NAME = "purchases";
        public static final String ORDER_ID = "order_id";
        public static final String STORE_ID = "store_id";
        public static final String PRODUCT_ID = "product_id";
        public static final String COUNT = "count";
        public static final String COUNT_TYPE_ID = "count_type_id";
        public static final String DELIVERY_DATE = "delivery_date";
        public static final String IS_DELIVERED = "is_delivered";

        public Purchases() {
            super();
            name = TABLE_NAME;
            type = DBLiteStructure.this.TABLE;

            addColumn(new TableColumn(
                    ORDER_ID, "varchar(255)", false, true
            ));
            addColumn(new TableColumn(
                    STORE_ID, "integer", false, true, getTable(Partners.TABLE_NAME).getTableCol(Partners.ID)
            ));
            addColumn(new TableColumn(
                    PRODUCT_ID, "integer", false, true, getTable(Products.TABLE_NAME).getTableCol(Products.ID)
            ));
            addColumn(new TableColumn(
                    COUNT, "double(1000000000, 1000)", false, true
            ));
            addColumn(new TableColumn(
                    COUNT_TYPE_ID, "integer", false, true, getTable(CountTypes.TABLE_NAME).getTableCol(CountTypes.COUNT_TYPE)
            ));
            addColumn(new TableColumn(
                    DELIVERY_DATE, "date", false, true
            ));
            addColumn(new TableColumn(
                    IS_DELIVERED, "tinyint", false, true
            ));
        }
    }

    /**
     * Таблица заказов от клиентов
     * */
    class Orders extends Table{
        public static final String TABLE_NAME = "orders";
        public static final String CUSTOMER_ID = "customer_id";
        public static final String PROJECT_ID = "project_id";
        public static final String IS_MONTHLY = "is_monthly";
        public static final String DATE_START = "date_start";
        public static final String DATE_FINISH = "date_finish";
        public static final String STATUS_ID = "status_id";
        public static final String AUTOFINISH = "autofinish";

        public Orders() {
            super();
            name = TABLE_NAME;
            type = DBLiteStructure.this.TABLE;

            addColumn(new TableColumn(
                    CUSTOMER_ID, "integer", false, true
                    ));
            addColumn(new TableColumn(
                    PROJECT_ID, "integer", false, true
                    ));
            addColumn(new TableColumn(
                    IS_MONTHLY, "integer", false, true
                    ));
            addColumn(new TableColumn(
                    DATE_START, "date", false, true
                    ));
            addColumn(new TableColumn(
                    DATE_FINISH, "date", false, true
                    ));
            addColumn(new TableColumn(
                    STATUS_ID, "integer", false, true, getTable(OrderStatus.TABLE_NAME).getTableCol(OrderStatus.ORDER_STATUS)
                    ));
            addColumn(new TableColumn(
                    AUTOFINISH, "integer", false, true
                    ));
        }
    }

    /**
     * Таблица списка проектов
     * */
    class ProjectsList extends Table{
        public static final String TABLE_NAME = "projects_list";
        public static final String NAME = "name";
        public static final String DATE = "date";

        public ProjectsList() {
            super();
            name = TABLE_NAME;
            type = DBLiteStructure.this.TABLE;

            addColumn(new TableColumn(
                    NAME, "varchar(255)", false, true
            ));
            addColumn(new TableColumn(
                    DATE, "date", false, true
            ));
        }
    }

    /**
     * Таблица доходов
     * */
    class Incomes extends Table{
        public static final String TABLE_NAME = "incomes";
        public static final String ORDER_ID = "order_id";

        public Incomes() {
            super();
            name = TABLE_NAME;
            type = DBLiteStructure.this.TABLE;

            addColumn(new TableColumn(
                    ORDER_ID, "integer", false, true
            ));
        }
    }

    private void firstInit() throws SQLException, ClassNotFoundException {
        DataBaseManager dbManager = DataBaseManager.getDbManager();
        File db = new File(URL_DB);
        LOG.log(Level.INFO, "Создаю БД");
        dbManager.createDatabase(db);

        LOG.log(Level.INFO, "Добавляю таблицы в БД");
        for(Table table : tables){
            dbManager.createTable(table, db);
        }
        LOG.log(Level.INFO, "Создание структуры успешно пройдено");

    }

    private void secondInit(){
        
    }
}
