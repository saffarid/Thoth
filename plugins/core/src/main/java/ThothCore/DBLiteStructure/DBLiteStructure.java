package ThothCore.DBLiteStructure;

import Database.DataBaseSQL;
import Database.Table;
import Database.TableColumn;

public class DBLiteStructure extends DataBaseSQL {

    public final String TABLE = "table";
    public final String PROJECT_TABLE = "project_table";
    public final String GUIDE = "guide";
    public final String CONSTANTS = "constants";
    public final String SYSTEM_TABLE = "system_table";
    public final String SYSTEM_GUIDE = "system_guide";
    public final String SYSTEM_CONSTANTS = "system_constants";

    public DBLiteStructure() {
        super();

        //Таблицы типа GUIDE
        tables.add(new CountTypes());
        tables.add(new ProductTypes());
        tables.add(new OrderStatus());
        tables.add(new Partners());
        tables.add(new IncomeTypes());
        tables.add(new Currency());

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

        public NotUsed() {
            super();
            name = TABLE_NAME;
            type = DBLiteStructure.this.TABLE;

            addColumn(new TableColumn(
                    ARTICLE, "varchar(255)", true, true
            ));
            addColumn(new TableColumn(
                    NAME, "varchar(255)", true, true
            ));
            addColumn(new TableColumn(
                    CAUSE, "text", false, false
            ));
        }
    }

}
