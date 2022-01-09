package thoth_core.thoth_lite.db_lite_structure.full_structure;

import database.Column.*;
import database.DataBaseSQL;
import database.Exceptions.NotSupportedOperation;
import database.Table;

import java.util.logging.Logger;

public class DBLiteStructure extends DataBaseSQL {

    private static Logger LOG;

    static {
        LOG = Logger.getLogger(DBLiteStructure.class.getName());
    }

    public DBLiteStructure() {
        super();
        tables.add(new Info());
        //Таблицы типа GUIDE
        tables.add(new CountTypes());
        tables.add(new ExpensesTypes());
        tables.add(new IncomesTypes());
        tables.add(new OrderStatus());
        tables.add(new ProductTypes());
        tables.add(new ProjectTypes());
        tables.add(new Partners());
        tables.add(new Storage());
        //Таблица типа SYSTEM_TABLE
        tables.add(new Currency());
        //Таблица типа TABLE
        tables.add(new Expenses());
        tables.add(new Incomes());
        tables.add(new NotUsed());
        tables.add(new Products());
        tables.add(new PurchaseDesc());
//        tables.add(new Orders());
//        tables.add(new ProjectsDesc());
        //Таблицы типа COMPOSITE
//        tables.add(new ProjectsComposition());
        tables.add(new PurchaseComposite());
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
            if (foreignKey != null) column.setForeignKey(foreignKey);
        } catch (NotSupportedOperation notSupportedOperation) {
            notSupportedOperation.printStackTrace();
        }

        return column;
    }

    private TableColumn getForeignColumnByName(String tableName, String article) {
        return getTable(tableName).getColumnByName(article);
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
     * Таблица системной информации
     */
    class Info extends Table {
        public Info() {
            super();
            name = StructureDescription.Info.TABLE_NAME;
            type = StructureDescription.TableTypes.SYSTEM_TABLE.getType();

            addColumn(
                    TableColumn.getInstance(ColumnTypes.PRIMARYKEY_AUTOINCREMENT)
            );
            addColumn(
                    getCustomColumn(
                            StructureDescription.Info.MODULE, DataTypes.NOTE, false, true
                    )
            );
            addColumn(
                    getCustomColumn(
                            StructureDescription.Info.VERSION, DataTypes.NOTE, false, true
                    )
            );
        }
    }

    /* --- Таблицы со списочными данными --- */

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
     * Таблица типов расходов
     */
    class ExpensesTypes extends Table {
        public ExpensesTypes() {
            name = StructureDescription.ExpensesTypes.TABLE_NAME;
            type = StructureDescription.TableTypes.TABLE.getType();

            addColumn(TableColumn.getInstance(ColumnTypes.PRIMARYKEY_AUTOINCREMENT));
            addColumn(getCustomColumn(
                    StructureDescription.ExpensesTypes.EXPENSES_TYPE, DataTypes.TEXT, true, true
            ));
        }
    }

    /**
     * Таблица категорий доходов
     */
    class IncomesTypes extends Table {
        public IncomesTypes() {
            super();
            name = StructureDescription.IncomesTypes.TABLE_NAME;
            type = StructureDescription.TableTypes.GUIDE.getType();

            addColumn(TableColumn.getInstance(ColumnTypes.PRIMARYKEY_AUTOINCREMENT));
            addColumn(getCustomColumn(
                    StructureDescription.IncomesTypes.INCOMES_TYPE, DataTypes.NOTE, true, true
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
     * Таблица типов проектов
     */
    class ProjectTypes extends Table {
        public ProjectTypes() {
            super();
            name = StructureDescription.ProjectTypes.TABLE_NAME;
            type = StructureDescription.TableTypes.GUIDE.getType();

            addColumn(TableColumn.getInstance(ColumnTypes.PRIMARYKEY_AUTOINCREMENT));
            addColumn(getCustomColumn(
                    StructureDescription.ProjectTypes.TABLE_NAME, DataTypes.NOTE, true, true
            ));
        }
    }

    /* --- Простые таблицы --- */

    /**
     * Таблица список валют
     */
    class Currency extends Table {
        public Currency() {
            super();
            name = StructureDescription.Currency.TABLE_NAME;
            type = StructureDescription.TableTypes.SYSTEM_TABLE.getType();

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
     * Таблица учета расходов
     */
    class Expenses extends Table {
        public Expenses() {
            super();
            name = StructureDescription.Expenses.TABLE_NAME;
            type = StructureDescription.TableTypes.TABLE.getType();

            addColumn(TableColumn.getInstance(ColumnTypes.PRIMARYKEY_AUTOINCREMENT));
            addColumn(getCustomColumn(
                    StructureDescription.Expenses.EXPENSES_TYPE_ID, DataTypes.INT, false, true,
                    getForeignColumnByName(StructureDescription.ExpensesTypes.TABLE_NAME, StructureDescription.ExpensesTypes.EXPENSES_TYPE)
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Expenses.VALUE, DataTypes.DOUBLE, false, true
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Expenses.DATE, DataTypes.TEXT, false, true
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Expenses.CURRENCY_ID, DataTypes.INT, false, true,
                    getForeignColumnByName(StructureDescription.Currency.TABLE_NAME, StructureDescription.Currency.CURRENCY)
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Expenses.COURSE, DataTypes.DOUBLE, false, true
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Expenses.COMMENT, DataTypes.TEXT, false, false
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

            addColumn(TableColumn.getInstance(ColumnTypes.PRIMARYKEY_AUTOINCREMENT));
            addColumn(getCustomColumn(
                    StructureDescription.Incomes.INCOMES_TYPE_ID, DataTypes.INT, false, true,
                    getForeignColumnByName(StructureDescription.IncomesTypes.TABLE_NAME, StructureDescription.IncomesTypes.INCOMES_TYPE)
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Incomes.VALUE, DataTypes.DOUBLE, false, true
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Incomes.DATE, DataTypes.TEXT, false, true
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Incomes.CURRENCY_ID, DataTypes.INT, false, true,
                    getForeignColumnByName(StructureDescription.Currency.TABLE_NAME, StructureDescription.Currency.CURRENCY)
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Incomes.COURSE, DataTypes.DOUBLE, false, true
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Incomes.COMMENT, DataTypes.TEXT, false, false
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
     * Таблица заказов от клиентов
     */
    class Orders extends Table {
        public Orders() {
            super();
            name = StructureDescription.Orders.TABLE_NAME;
            type = StructureDescription.TableTypes.TABLE.getType();

            addColumn(getPrimaryKeyCustom(
                    StructureDescription.Orders.ID, DataTypes.TEXT
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Orders.CUSTOMER_ID, DataTypes.INT, false, true
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Orders.PROJECT_ID, DataTypes.INT, false, true
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Orders.IS_MONTHLY, DataTypes.BOOL, false, true
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Orders.DATE_START, DataTypes.NOTE, false, true
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Orders.DATE_FINISH, DataTypes.NOTE, false, true
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Orders.STATUS_ID, DataTypes.INT, false, true
                    , getTable(StructureDescription.OrderStatus.TABLE_NAME).getColumnByName(StructureDescription.OrderStatus.ORDER_STATUS)
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Orders.AUTOFINISH, DataTypes.BOOL, false, true
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
     * Таблица для хранения продуктов
     */
    class Products extends Table {
        public Products() {
            super();
            name = StructureDescription.Products.TABLE_NAME;
            type = StructureDescription.TableTypes.TABLE.getType();

            addColumn(getPrimaryKeyCustom(
                    StructureDescription.Products.ARTICLE, DataTypes.TEXT
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Products.NAME, DataTypes.TEXT, false, true
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Products.PRODUCT_TYPE_ID, DataTypes.INT, false, true
                    , getTable(StructureDescription.ProductTypes.TABLE_NAME).getColumnByName(StructureDescription.ProductTypes.PRODUCT_TYPES)
            ));
//            addColumn( getCustomColumn(
//                    StructureDescription.Products.PRICE, DataTypes.DOUBLE, false, true
//            ));
//            addColumn( getCustomColumn(
//                    StructureDescription.Products.CURRENCY_ID, DataTypes.INT, false, true
//                    , getTable(StructureDescription.Currency.TABLE_NAME).getColumnByName(StructureDescription.Currency.CURRENCY)
//            ));
            addColumn(getCustomColumn(
                    StructureDescription.Products.COUNT, DataTypes.INT, false, true
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Products.COUNT_TYPE_ID, DataTypes.INT, false, true
                    , getTable(StructureDescription.CountTypes.TABLE_NAME).getColumnByName(StructureDescription.CountTypes.COUNT_TYPE)
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Products.ADRESS, DataTypes.INT, false, false
                    , getTable(StructureDescription.Storage.TABLE_NAME).getColumnByName(StructureDescription.Storage.ID)
            ));
            addColumn(getCustomColumn(
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
            type = StructureDescription.TableTypes.GUIDE.getType();

            addColumn(TableColumn.getInstance(ColumnTypes.PRIMARYKEY_AUTOINCREMENT));
            addColumn(getCustomColumn(
                    StructureDescription.Storage.ADRESS, DataTypes.NOTE, true, true
            ));
//            addColumn( getCustomColumn(
//                    StructureDescription.Storage.COUNT, DataTypes.INT, false, true
//            ));
//            addColumn( getCustomColumn(
//                    StructureDescription.Storage.COUNT_TYPE_ID, DataTypes.INT, false, true
//                    , getTable(StructureDescription.CountTypes.TABLE_NAME).getColumnByName(StructureDescription.CountTypes.COUNT_TYPE)
//            ));
//            addColumn( getCustomColumn(
//                    StructureDescription.Storage.PRODUCT_ID, DataTypes.INT, true, true
//                    , getTable(StructureDescription.Products.TABLE_NAME).getColumnByName(StructureDescription.Products.ARTICLE)
//            ));
        }
    }

    /**
     * Таблица-описание покупок
     */
    class PurchaseDesc extends Table {
        public PurchaseDesc() {
            super();
            name = StructureDescription.PurchaseDesc.TABLE_NAME;
            type = StructureDescription.TableTypes.DESC.getType();

            addColumn(getPrimaryKeyCustom(
                    StructureDescription.PurchaseDesc.PURCHASE_ID, DataTypes.TEXT
            ));
            addColumn(getCustomColumn(
                    StructureDescription.PurchaseDesc.STORE_ID, DataTypes.INT, false, true,
                    getTable(StructureDescription.Partners.TABLE_NAME).getColumnByName(StructureDescription.Partners.ID)
            ));
            addColumn(getCustomColumn(
                    StructureDescription.PurchaseDesc.DELIVERY_DATE, DataTypes.TEXT, false, true
            ));
            addColumn(getCustomColumn(
                    StructureDescription.PurchaseDesc.IS_DELIVERED, DataTypes.BOOL, false, true
            ));
        }
    }

    /**
     * Таблица-состав покупок
     */
    class PurchaseComposite extends Table {
        public PurchaseComposite() {
            super();
            name = StructureDescription.PurchaseComposition.TABLE_NAME;
            type = StructureDescription.TableTypes.COMPOSITE.getType();

            addColumn(TableColumn.getInstance(ColumnTypes.PRIMARYKEY_AUTOINCREMENT));
            addColumn(getCustomColumn(
                    StructureDescription.PurchaseComposition.PURCHASE_ID, DataTypes.INT, false, true,
                    getForeignColumnByName(StructureDescription.PurchaseDesc.TABLE_NAME, StructureDescription.PurchaseDesc.PURCHASE_ID)
            ));
            addColumn(getCustomColumn(
                    StructureDescription.PurchaseComposition.PRODUCT_ID, DataTypes.INT, false, true,
                    getForeignColumnByName(StructureDescription.Products.TABLE_NAME, StructureDescription.Products.ARTICLE)
            ));
            addColumn(getCustomColumn(
                    StructureDescription.PurchaseComposition.COUNT, DataTypes.DOUBLE, false, true
            ));
            addColumn(getCustomColumn(
                    StructureDescription.PurchaseComposition.COUNT_TYPE_ID, DataTypes.INT, false, true,
                    getForeignColumnByName(StructureDescription.CountTypes.TABLE_NAME, StructureDescription.CountTypes.COUNT_TYPE)
            ));
            addColumn(getCustomColumn(
                    StructureDescription.PurchaseComposition.PRICE, DataTypes.DOUBLE, false, true
            ));
            addColumn(getCustomColumn(
                    StructureDescription.PurchaseComposition.CURRENCY_ID, DataTypes.INT, false, true,
                    getForeignColumnByName(StructureDescription.Currency.TABLE_NAME, StructureDescription.Currency.CURRENCY)
            ));
            addColumn(getCustomColumn(
                    StructureDescription.PurchaseComposition.COURSE, DataTypes.DOUBLE, false, true
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

            addColumn(TableColumn.getInstance(ColumnTypes.PRIMARYKEY_AUTOINCREMENT));
            addColumn(getCustomColumn(
                    StructureDescription.Purchases.ORDER_ID, DataTypes.NOTE, false, true
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Purchases.STORE_ID, DataTypes.INT, false, true
                    , getTable(StructureDescription.Partners.TABLE_NAME).getColumnByName(StructureDescription.Partners.ID)
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Purchases.PRODUCT_ID, DataTypes.INT, false, true
                    , getTable(StructureDescription.Products.TABLE_NAME).getColumnByName(StructureDescription.Products.ARTICLE)
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Purchases.COUNT, DataTypes.DOUBLE, false, true
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Purchases.COUNT_TYPE_ID, DataTypes.INT, false, true
                    , getTable(StructureDescription.CountTypes.TABLE_NAME).getColumnByName(StructureDescription.CountTypes.COUNT_TYPE)
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Purchases.PRICE, DataTypes.DOUBLE, false, true
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Purchases.CURRENCY_ID, DataTypes.INT, false, true
                    , getTable(StructureDescription.Currency.TABLE_NAME).getColumnByName(StructureDescription.Currency.CURRENCY)
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Purchases.DELIVERY_DATE, DataTypes.NOTE, false, true
            ));
            addColumn(getCustomColumn(
                    StructureDescription.Purchases.IS_DELIVERED, DataTypes.BOOL, false, true
            ));

        }
    }

    /**
     * Таблица списка проектов
     */
    class ProjectsDesc extends Table {
        public ProjectsDesc() {
            super();
            name = StructureDescription.ProjectsDesc.TABLE_NAME;
            type = StructureDescription.TableTypes.DESC.getType();

            addColumn(getPrimaryKeyCustom(
                    StructureDescription.ProjectsDesc.ID, DataTypes.TEXT
            ));

            addColumn(getCustomColumn(
                    StructureDescription.ProjectsDesc.NAME, DataTypes.NOTE, false, true
            ));
            addColumn(getCustomColumn(
                    StructureDescription.ProjectsDesc.TYPE_ID, DataTypes.INT, false, true,
                    getTable(StructureDescription.ProjectTypes.TABLE_NAME).getColumnByName(StructureDescription.ProjectTypes.PROJECT_TYPE)
            ));
            addColumn(getCustomColumn(
                    StructureDescription.ProjectsDesc.DATE, DataTypes.NOTE, false, true
            ));
        }
    }

    /**
     * Таблица состава проектов
     */
    class ProjectsComposition extends Table {
        public ProjectsComposition() {
            super();
            name = StructureDescription.ProjectsComposition.TABLE_NAME;
            type = StructureDescription.TableTypes.COMPOSITE.getType();

            addColumn(TableColumn.getInstance(ColumnTypes.PRIMARYKEY_AUTOINCREMENT));
            addColumn(getCustomColumn(
                    StructureDescription.ProjectsComposition.PRODUCT_ID, DataTypes.TEXT, false, true,
                    getTable(StructureDescription.Products.TABLE_NAME).getColumnByName(StructureDescription.Products.ARTICLE)
            ));
            addColumn(getCustomColumn(
                    StructureDescription.ProjectsComposition.COUNT, DataTypes.DOUBLE, false, true
            ));
            addColumn(getCustomColumn(
                    StructureDescription.ProjectsComposition.COUNT_TYPE_ID, DataTypes.INT, false, true,
                    getTable(StructureDescription.CountTypes.TABLE_NAME).getColumnByName(StructureDescription.CountTypes.COUNT_TYPE)
            ));
        }
    }

}
