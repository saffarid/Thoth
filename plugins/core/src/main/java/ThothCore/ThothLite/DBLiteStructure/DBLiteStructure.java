package ThothCore.ThothLite.DBLiteStructure;

import Database.DataBaseSQL;
import Database.Table;
import Database.TableColumn;
import ThothCore.ThothLite.StructureDescription;

import java.sql.SQLException;
import java.util.logging.Logger;

public class DBLiteStructure extends DataBaseSQL {

    private static Logger LOG;

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

    }

    /**
     * Таблица едениц измерения
     */
    class CountTypes extends Table {
        public CountTypes() {
            super();
            name = StructureDescription.CountTypes.TABLE_NAME;
            type = StructureDescription.GUIDE;

            addColumn(new TableColumn(
                    StructureDescription.CountTypes.COUNT_TYPE, "varchar(10)", true, true
            ));
        }
    }

    /**
     * Таблица типов продуктов
     */
    class ProductTypes extends Table {
        public ProductTypes() {
            super();
            name = StructureDescription.ProductTypes.TABLE_NAME;
            type = StructureDescription.GUIDE;

            addColumn(new TableColumn(
                    StructureDescription.ProductTypes.PRODUCT_TYPES, "varchar(255)", true, true
            ));
        }
    }

    /**
     * Таблица статусов заказа
     */
    class OrderStatus extends Table {
        public OrderStatus() {
            super();
            name = StructureDescription.OrderStatus.TABLE_NAME;
            type = StructureDescription.GUIDE;

            addColumn(new TableColumn(
                    StructureDescription.OrderStatus.ORDER_STATUS, "varchar(255)", true, true
            ));
        }
    }

    /**
     * Таблица партнеров
     */
    class Partners extends Table {
        public Partners() {
            super();
            name = StructureDescription.Partners.TABLE_NAME;
            type = StructureDescription.GUIDE;

            addColumn(new TableColumn(
                    StructureDescription.Partners.NAME, "text", true, true
            ));
            addColumn(new TableColumn(
                    StructureDescription.Partners.PHONE, "varchar(20)", false, false
            ));
            addColumn(new TableColumn(
                    StructureDescription.Partners.WEB, "varchar(255)", false, false
            ));
        }
    }

    /**
     * Таблица категорий доходов
     */
    class IncomeTypes extends Table {
        public IncomeTypes() {
            super();
            name = StructureDescription.IncomeTypes.TABLE_NAME;
            type = StructureDescription.GUIDE;

            addColumn(new TableColumn(
                    StructureDescription.IncomeTypes.INCOME_TYPE, "varchar(255)", true, true
            ));
        }
    }

    /**
     * Таблица список валют
     */
    class Currency extends Table {
        public Currency() {
            super();
            name = StructureDescription.Currency.TABLE_NAME;
            type = StructureDescription.GUIDE;

            addColumn(new TableColumn(
                    StructureDescription.Currency.CURRENCY, "varchar(100)", true, true
            ));
            addColumn(new TableColumn(
                    StructureDescription.Currency.COURSE, "double(100, 2)", false, true
            ));
        }
    }

    /**
     * Таблица для хранения неиспользуемых продуктов
     */
    class NotUsed extends Table {
        public NotUsed() {
            super();
            name = StructureDescription.NotUsed.TABLE_NAME;
            type = StructureDescription.TABLE;

            addColumn(new TableColumn(
                    StructureDescription.NotUsed.PRODUCT_ID, "integer", true, true, getTable(StructureDescription.Products.TABLE_NAME).getTableCol(Products.ID)
            ));
            addColumn(new TableColumn(
                    StructureDescription.NotUsed.CAUSE, "text", false, false
            ));
        }
    }

    /**
     * Таблица для хранения продуктов
     */
    class Products extends Table {
        public Products() {
            super();
            name = StructureDescription.Products.TABLE_NAME;
            type = StructureDescription.TABLE;

            addColumn(new TableColumn(
                    StructureDescription.Products.ARTICLE, "varchar(255)", true, true
            ));
            addColumn(new TableColumn(
                    StructureDescription.Products.NAME, "varchar(255)", false, true
            ));
            addColumn(new TableColumn(
                    StructureDescription.Products.PRODUCT_TYPE_ID, "integer", false, true, getTable(StructureDescription.ProductTypes.TABLE_NAME).getTableCol(StructureDescription.ProductTypes.PRODUCT_TYPES)
            ));
            addColumn(new TableColumn(
                    StructureDescription.Products.PRICE, "double(100000000, 2)", false, true
            ));
            addColumn(new TableColumn(
                    StructureDescription.Products.CURRENCY_ID, "integer", false, true, getTable(StructureDescription.Currency.TABLE_NAME).getTableCol(StructureDescription.Currency.CURRENCY)
            ));
            addColumn(new TableColumn(
                    StructureDescription.Products.NOTE, "text", false, false
            ));
        }
    }

    /**
     * Таблица для хранения содержимого склада
     */
    class Storage extends Table {
        public Storage() {
            super();
            name = StructureDescription.Storage.TABLE_NAME;
            type = StructureDescription.TABLE;

            addColumn(new TableColumn(
                    StructureDescription.Storage.COUNT, "integer", false, true
            ));
            addColumn(new TableColumn(
                    StructureDescription.Storage.COUNT_TYPE_ID, "integer", false, true, getTable(StructureDescription.CountTypes.TABLE_NAME).getTableCol(StructureDescription.CountTypes.COUNT_TYPE)
            ));
            addColumn(new TableColumn(
                    StructureDescription.Storage.PRODUCT_ID, "integer", true, true, getTable(StructureDescription.Products.TABLE_NAME).getTableCol(StructureDescription.Products.ID)
            ));
        }
    }

    /**
     * Таблица для хранения покупок
     */
    class Purchases extends Table {
        public Purchases() {
            super();
            name = StructureDescription.Purchases.TABLE_NAME;
            type = StructureDescription.TABLE;

            addColumn(new TableColumn(
                    StructureDescription.Purchases.ORDER_ID, "varchar(255)", false, true
            ));
            addColumn(new TableColumn(
                    StructureDescription.Purchases.STORE_ID, "integer", false, true, getTable(StructureDescription.Partners.TABLE_NAME).getTableCol(Partners.ID)
            ));
            addColumn(new TableColumn(
                    StructureDescription.Purchases.PRODUCT_ID, "integer", false, true, getTable(StructureDescription.Products.TABLE_NAME).getTableCol(Products.ID)
            ));
            addColumn(new TableColumn(
                    StructureDescription.Purchases.COUNT, "double(1000000000, 1000)", false, true
            ));
            addColumn(new TableColumn(
                    StructureDescription.Purchases.COUNT_TYPE_ID, "integer", false, true, getTable(StructureDescription.CountTypes.TABLE_NAME).getTableCol(StructureDescription.CountTypes.COUNT_TYPE)
            ));
            addColumn(new TableColumn(
                    StructureDescription.Purchases.DELIVERY_DATE, "varchar(255)", false, true
            ));
            addColumn(new TableColumn(
                    StructureDescription.Purchases.IS_DELIVERED, "tinyint", false, true
            ));
        }
    }

    /**
     * Таблица заказов от клиентов
     */
    class Orders extends Table {
        public Orders() {
            super();
            name = StructureDescription.Orders.TABLE_NAME;
            type = StructureDescription.TABLE;

            addColumn(new TableColumn(
                    StructureDescription.Orders.CUSTOMER_ID, "integer", false, true
            ));
            addColumn(new TableColumn(
                    StructureDescription.Orders.PROJECT_ID, "integer", false, true
            ));
            addColumn(new TableColumn(
                    StructureDescription.Orders.IS_MONTHLY, "integer", false, true
            ));
            addColumn(new TableColumn(
                    StructureDescription.Orders.DATE_START, "varchar(255)", false, true
            ));
            addColumn(new TableColumn(
                    StructureDescription.Orders.DATE_FINISH, "varchar(255)", false, true
            ));
            addColumn(new TableColumn(
                    StructureDescription.Orders.STATUS_ID, "integer", false, true, getTable(StructureDescription.OrderStatus.TABLE_NAME).getTableCol(StructureDescription.OrderStatus.ORDER_STATUS)
            ));
            addColumn(new TableColumn(
                    StructureDescription.Orders.AUTOFINISH, "integer", false, true
            ));
        }
    }

    /**
     * Таблица списка проектов
     */
    class ProjectsList extends Table {
        public ProjectsList() {
            super();
            name = StructureDescription.ProjectsList.TABLE_NAME;
            type = StructureDescription.TABLE;

            addColumn(new TableColumn(
                    StructureDescription.ProjectsList.NAME, "varchar(255)", false, true
            ));
            addColumn(new TableColumn(
                    StructureDescription.ProjectsList.DATE, "varchar(255)", false, true
            ));
        }
    }

    /**
     * Таблица доходов
     */
    class Incomes extends Table {
        public Incomes() {
            super();
            name = StructureDescription.Incomes.TABLE_NAME;
            type = StructureDescription.TABLE;

            addColumn(new TableColumn(
                    StructureDescription.Incomes.ORDER_ID, "integer", false, true
            ));
        }
    }

}
