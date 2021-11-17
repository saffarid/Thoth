package ThothCore.ThothLite.DBLiteStructure.FullStructure;

public class StructureDescription {

    public static enum TableTypes {
        TABLE("table"),
        PROJECT_TABLE("project_table"),
        GUIDE("guide"),
        CONSTANTS("constants"),
        SYSTEM_TABLE("system_table"),
        SYSTEM_GUIDE("system_guide"),
        SYSTEM_CONSTANTS("system_constants");

        private String type;

        TableTypes(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }


    /**
     * Таблица едениц измерения
     */
    public static class CountTypes {
        public static final String TABLE_NAME = "count_type";
        public static final String ID = "id";
        public static final String COUNT_TYPE = "count_type";
    }

    /**
     * Таблица типов продуктов
     */
    public static class ProductTypes {
        public static final String TABLE_NAME = "product_types";
        public static final String ID = "id";
        public static final String PRODUCT_TYPES = "product_types";
    }

    /**
     * Таблица статусов заказа
     */
    public static class OrderStatus {
        public static final String TABLE_NAME = "order_status";
        public static final String ID = "id";
        public static final String ORDER_STATUS = "order_status";
    }

    /**
     * Таблица партнеров
     */
    public static class Partners {
        public static final String TABLE_NAME = "partners";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String PHONE = "phone";
        public static final String WEB = "web";
    }

    /**
     * Таблица категорий доходов
     */
    public static class IncomeTypes {
        public static final String TABLE_NAME = "income_types";
        public static final String ID = "id";
        public static final String INCOME_TYPE = "income_type";
    }

    /**
     * Таблица список валют
     */
    public static class Currency {
        public static final String TABLE_NAME = "currency";
        public static final String ID = "id";
        public static final String CURRENCY = "currency";
        public static final String COURSE = "course";
    }

    /**
     * Таблица для хранения неиспользуемых продуктов
     */
    public static class NotUsed {
        public static final String TABLE_NAME = "not_used";
        public static final String ID = "id";
        //        public static final String ARTICLE = "article";
//        public static final String NAME = "name";
        public static final String CAUSE = "cause";
        public static final String PRODUCT_ID = "product_id";
    }

    /**
     * Таблица для хранения продуктов
     */
    public static class Products {
        public static final String TABLE_NAME = "products";
        //        public static final String ID = "id";
        public static final String ARTICLE = "article";
        public static final String NAME = "name";
        public static final String PRODUCT_TYPE_ID = "product_type_id";
        //        public static final String PRICE = "price";
//        public static final String CURRENCY_ID = "currency_id";
        public static final String ADRESS = "adress";
        public static final String COUNT = "count";
        public static final String COUNT_TYPE_ID = "count_type_id";
        public static final String NOTE = "note";
    }

    /**
     * Таблица для хранения списка мест хранения продуктов
     */
    public static class Storage {
        public static final String TABLE_NAME = "storage";
        public static final String ID = "id";
        public static final String ADRESS = "adress";
//        public static final String COUNT = "count";
//        public static final String COUNT_TYPE_ID = "count_type_id";
//        public static final String PRODUCT_ID = "product_id";
    }

    /**
     * Таблица для хранения покупок
     */
    public static class Purchases {
        public static final String TABLE_NAME = "purchases";
        public static final String ID = "id";
        public static final String ORDER_ID = "order_id";
        public static final String STORE_ID = "store_id";
        public static final String PRODUCT_ID = "product_id";
        public static final String COUNT = "count";
        public static final String COUNT_TYPE_ID = "count_type_id";
        public static final String PRICE = "price";
        public static final String CURRENCY_ID = "currency_id";
        public static final String DELIVERY_DATE = "delivery_date";
        public static final String IS_DELIVERED = "is_delivered";
    }

    /**
     * Таблица заказов от клиентов
     */
    public static class Orders {
        public static final String TABLE_NAME = "orders";
        public static final String ID = "order_id";
        public static final String CUSTOMER_ID = "customer_id";
        public static final String PROJECT_ID = "project_id";
        public static final String IS_MONTHLY = "is_monthly";
        public static final String DATE_START = "date_start";
        public static final String DATE_FINISH = "date_finish";
        public static final String STATUS_ID = "status_id";
        public static final String AUTOFINISH = "autofinish";
    }

    /**
     * Таблица списка проектов
     */
    public static class ProjectsList {
        public static final String TABLE_NAME = "projects_list";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DATE = "date";
    }

    /**
     * Таблица доходов
     */
    public static class Incomes {
        public static final String TABLE_NAME = "incomes";
        public static final String ID = "id";
        public static final String ORDER_ID = "order_id";
    }

}
