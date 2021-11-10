package ThothCore.ThothLite.DBLiteStructure.FullStructure;

import Database.Column.*;
import Database.DataBaseSQL;
import Database.Exceptions.NotSupportedOperation;
import Database.Table;

import java.util.logging.Logger;

public class DBLiteStructure extends DataBaseSQL {

    private static Logger LOG;

    static {
        LOG = Logger.getLogger(DBLiteStructure.class.getName());
    }

    public DBLiteStructure() {
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

    private TableColumn getCustomColumn(String name, DataTypes type, boolean isUnique, boolean isNotNull) {
        return getCustomColumn(name, type, isUnique, isNotNull, null);
    }

    private TableColumn getCustomColumn(String name, DataTypes type, boolean isUnique, boolean isNotNull, TableColumn foreignKey) {
        CustomColumn column = (CustomColumn) TableColumn.getInstance(ColumnTypes.CUSTOM_COLUMN);

        try {
            column.setName(name);
            column.setType(type);
            column.setUnique(isUnique);
            column.setNotNull(isNotNull);
            if(foreignKey != null) column.setForeignKey(foreignKey);
        } catch (NotSupportedOperation notSupportedOperation) {
            notSupportedOperation.printStackTrace();
        }

        return column;
    }

    private TableColumn getPrimaryKeyCustom(String name, DataTypes type) {
        TableColumn column = TableColumn.getInstance(ColumnTypes.PRIMARYKEY_CUSTOM);
        try {
            column.setName(name);
            column.setType(type);
        } catch (NotSupportedOperation notSupportedOperation) {
            notSupportedOperation.printStackTrace();
        }
        return column;
    }

    /**
     * Таблица едениц измерения
     */
    class CountTypes extends Table {
        public CountTypes() {
            super();
            name = StructureDescription.CountTypes.TABLE_NAME;
            type = StructureDescription.TableTypes.GUIDE.getType();

            addColumn(
                    TableColumn.getInstance(ColumnTypes.PRIMARYKEY_AUTOINCREMENT)
            );
            addColumn(getCustomColumn(
                    StructureDescription.CountTypes.COUNT_TYPE, DataTypes.NOTE, true, true
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
            type = StructureDescription.TableTypes.GUIDE.getType();

            addColumn(
                    TableColumn.getInstance(ColumnTypes.PRIMARYKEY_AUTOINCREMENT)
            );
            addColumn(getCustomColumn(
                    StructureDescription.ProductTypes.PRODUCT_TYPES, DataTypes.NOTE, true, true
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
            type = StructureDescription.TableTypes.GUIDE.getType();

            addColumn(
                    TableColumn.getInstance(ColumnTypes.PRIMARYKEY_AUTOINCREMENT)
            );
            addColumn(getCustomColumn(
                    StructureDescription.OrderStatus.ORDER_STATUS, DataTypes.NOTE, true, true
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
            type = StructureDescription.TableTypes.GUIDE.getType();

            addColumn(
                    TableColumn.getInstance(ColumnTypes.PRIMARYKEY_AUTOINCREMENT)
            );
            addColumn(getCustomColumn(
                    StructureDescription.Partners.NAME, DataTypes.TEXT, true, true
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Partners.PHONE, DataTypes.NOTE, false, false
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Partners.WEB, DataTypes.NOTE, false, false
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
            type = StructureDescription.TableTypes.GUIDE.getType();

            addColumn(TableColumn.getInstance(ColumnTypes.PRIMARYKEY_AUTOINCREMENT));
            addColumn(getCustomColumn(
                    StructureDescription.IncomeTypes.INCOME_TYPE, DataTypes.NOTE, true, true
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
            type = StructureDescription.TableTypes.GUIDE.getType();

            addColumn(TableColumn.getInstance(ColumnTypes.PRIMARYKEY_AUTOINCREMENT));
            addColumn(getCustomColumn(
                    StructureDescription.Currency.CURRENCY, DataTypes.NOTE, true, true
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Currency.COURSE, DataTypes.DOUBLE, false, true
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
            type = StructureDescription.TableTypes.TABLE.getType();

            addColumn(getPrimaryKeyCustom(
                    StructureDescription.NotUsed.PRODUCT_ID, DataTypes.INT
            ));
            addColumn(getCustomColumn(
                    StructureDescription.NotUsed.CAUSE, DataTypes.TEXT, false, false
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
            type = StructureDescription.TableTypes.TABLE.getType();

            addColumn( getPrimaryKeyCustom(
                    StructureDescription.Products.ARTICLE, DataTypes.TEXT
            ));
            addColumn( getCustomColumn(
                    StructureDescription.Products.NAME, DataTypes.TEXT, false, true
            ));
            addColumn( getCustomColumn(
                    StructureDescription.Products.PRODUCT_TYPE_ID, DataTypes.INT, false, true
                    , getTable(StructureDescription.ProductTypes.TABLE_NAME).getColumnByName(StructureDescription.ProductTypes.PRODUCT_TYPES)
            ));
            addColumn( getCustomColumn(
                    StructureDescription.Products.PRICE, DataTypes.DOUBLE, false, true
            ));
            addColumn( getCustomColumn(
                    StructureDescription.Products.CURRENCY_ID, DataTypes.INT, false, true
                    , getTable(StructureDescription.Currency.TABLE_NAME).getColumnByName(StructureDescription.Currency.CURRENCY)
            ));
            addColumn( getCustomColumn(
                    StructureDescription.Products.NOTE, DataTypes.TEXT, false, false
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
            type = StructureDescription.TableTypes.TABLE.getType();

            addColumn( getPrimaryKeyCustom(
                    StructureDescription.Storage.ADRESS, DataTypes.NOTE
            ) );
            addColumn( getCustomColumn(
                    StructureDescription.Storage.COUNT, DataTypes.INT, false, true
            ));
            addColumn( getCustomColumn(
                    StructureDescription.Storage.COUNT_TYPE_ID, DataTypes.INT, false, true
                    , getTable(StructureDescription.CountTypes.TABLE_NAME).getColumnByName(StructureDescription.CountTypes.COUNT_TYPE)
            ));
            addColumn( getCustomColumn(
                    StructureDescription.Storage.PRODUCT_ID, DataTypes.INT, true, true
                    , getTable(StructureDescription.Products.TABLE_NAME).getColumnByName(StructureDescription.Products.ARTICLE)
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
            type = StructureDescription.TableTypes.TABLE.getType();

            addColumn( TableColumn.getInstance(ColumnTypes.PRIMARYKEY_AUTOINCREMENT) );
            addColumn( getCustomColumn(
                    StructureDescription.Purchases.ORDER_ID, DataTypes.NOTE, false, true
            ));
            addColumn( getCustomColumn(
                    StructureDescription.Purchases.STORE_ID, DataTypes.INT, false, true
                    , getTable(StructureDescription.Partners.TABLE_NAME).getColumnByName(StructureDescription.Partners.ID)
            ));
            addColumn( getCustomColumn(
                    StructureDescription.Purchases.PRODUCT_ID, DataTypes.INT, false, true
                    , getTable(StructureDescription.Products.TABLE_NAME).getColumnByName(StructureDescription.Products.ARTICLE)
            ));
            addColumn( getCustomColumn(
                    StructureDescription.Purchases.COUNT, DataTypes.DOUBLE, false, true
            ));
            addColumn( getCustomColumn(
                    StructureDescription.Purchases.COUNT_TYPE_ID, DataTypes.DOUBLE, false, true
                    , getTable(StructureDescription.CountTypes.TABLE_NAME).getColumnByName(StructureDescription.CountTypes.COUNT_TYPE)
            ));
            addColumn( getCustomColumn(
                    StructureDescription.Purchases.DELIVERY_DATE, DataTypes.NOTE, false, true
            ));
            addColumn( getCustomColumn(
                    StructureDescription.Purchases.IS_DELIVERED, DataTypes.BOOL, false, true
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
            type = StructureDescription.TableTypes.TABLE.getType();

            addColumn( getPrimaryKeyCustom(
                    StructureDescription.Orders.ID, DataTypes.TEXT
            ));
            addColumn( getCustomColumn(
                    StructureDescription.Orders.CUSTOMER_ID, DataTypes.INT, false, true
            ));
            addColumn( getCustomColumn(
                    StructureDescription.Orders.PROJECT_ID, DataTypes.INT, false, true
            ));
            addColumn( getCustomColumn(
                    StructureDescription.Orders.IS_MONTHLY, DataTypes.BOOL, false, true
            ));
            addColumn( getCustomColumn(
                    StructureDescription.Orders.DATE_START, DataTypes.NOTE, false, true
            ));
            addColumn( getCustomColumn(
                    StructureDescription.Orders.DATE_FINISH, DataTypes.NOTE, false, true
            ));
            addColumn( getCustomColumn(
                    StructureDescription.Orders.STATUS_ID, DataTypes.INT, false, true
                    , getTable(StructureDescription.OrderStatus.TABLE_NAME).getColumnByName(StructureDescription.OrderStatus.ORDER_STATUS)
            ));
            addColumn( getCustomColumn(
                    StructureDescription.Orders.AUTOFINISH, DataTypes.BOOL, false, true
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
            type = StructureDescription.TableTypes.TABLE.getType();

            addColumn( TableColumn.getInstance(ColumnTypes.PRIMARYKEY_AUTOINCREMENT) );

            addColumn( getCustomColumn(
                    StructureDescription.ProjectsList.NAME, DataTypes.NOTE, false, true
            ));
            addColumn( getCustomColumn(
                    StructureDescription.ProjectsList.DATE, DataTypes.NOTE, false, true
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
            type = StructureDescription.TableTypes.TABLE.getType();

            addColumn( TableColumn.getInstance(ColumnTypes.PRIMARYKEY_AUTOINCREMENT) );

            addColumn( getCustomColumn(
                    StructureDescription.Incomes.ORDER_ID, DataTypes.INT, false, true
            ));
        }
    }

}
