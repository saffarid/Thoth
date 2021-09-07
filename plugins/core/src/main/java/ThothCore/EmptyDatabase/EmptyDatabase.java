package ThothCore.EmptyDatabase;

import Database.ContentValues;
import Database.DataBaseManager;
import Database.Table;
import Database.TableColumn;

import java.io.File;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Класс описывает пустую пользовательскую БД
 */
public class EmptyDatabase {

    protected static final String ID = "integer";
    protected static final String TEXT = "varchar(255)";
    protected static final String BOOL = "tinyint";
    protected static final String NUMBER = "double(10,5)";

    private TableTypes tableTypes;
    private TablesList tablesList;
    private TableDesc tableDesc;
    private DataTypes dataTypes;
    private ProductsCategory productsCategory;
    private Currency currency;
    private UnitMeas unitMeas;

    private TestTable testTable;

    public EmptyDatabase() {

        tableTypes = new TableTypes();
        dataTypes = new DataTypes();
        productsCategory = new ProductsCategory();
        currency = new Currency();
        unitMeas = new UnitMeas();

        tablesList = new TablesList();
        tableDesc = new TableDesc();

        testTable = new TestTable();
    }

    /**
     * Класс определяет таблицу "Типы таблиц"
     */
    private class TableTypes extends Table {
        public static final String TABLE_TYPE = "table_type";
        private List<ContentValues> contentValues;

        public TableTypes() {
            name = "Table types";
            type = Table.SYSTEM_TABLE_NA;

            addColumn(new TableColumn(TABLE_TYPE, EmptyDatabase.TEXT, true, false, true));

            contentValues = new LinkedList<>();
            contentValues.add(getValues(Table.TABLE));
            contentValues.add(getValues(Table.SYSTEM_TABLE_RO));
            contentValues.add(getValues(Table.SYSTEM_TABLE_RW));
            contentValues.add(getValues(Table.SYSTEM_TABLE_NA));
        }
        private ContentValues getValues(String tableType) {
            ContentValues contentValues = new ContentValues();
            Database.TableColumn tableTypeCol = columns
                    .stream()
                    .filter(column -> column.getName().equals(TABLE_TYPE))
                    .collect(Collectors.toList()).get(0);
            contentValues.put(tableTypeCol, tableType);
            return contentValues;
        }
        public List<ContentValues> getContentValues() {
            return contentValues;
        }
    }

    /**
     * Класс определяет таблицу "Список таблиц"
     */
    private class TablesList extends Table {
        public static final String TABLE_NAME = "table_name";
        public static final String TABLE_TYPE_ID = "table_type_id";
        private List<ContentValues> contentValues;
        public TablesList() {
            name = "Tables list";
            type = Table.SYSTEM_TABLE_NA;

            addColumn(new TableColumn(TABLE_NAME, EmptyDatabase.TEXT, false, false, true));
            addColumn(new TableColumn(TABLE_TYPE_ID, EmptyDatabase.ID, false, false, true, tableTypes.getIdColumn()));

            contentValues = new LinkedList<>();
            contentValues.add(getValues(dataTypes));
            contentValues.add(getValues(productsCategory));
            contentValues.add(getValues(currency));
            contentValues.add(getValues(unitMeas));
        }
        public ContentValues getValues(Table table) {
            ContentValues contentValues = new ContentValues();
            Database.TableColumn tableTypeCol = columns
                    .stream()
                    .filter(column -> column.getName().equals(TABLE_TYPE_ID))
                    .collect(Collectors.toList()).get(0);
            Database.TableColumn tableNameCol = columns
                    .stream()
                    .filter(column -> column.getName().equals(TABLE_NAME))
                    .collect(Collectors.toList()).get(0);

            contentValues.put(tableNameCol, table.getName());
            contentValues.put(tableTypeCol, table.getType());
            return contentValues;
        }
        public List<ContentValues> getContentValues() {
            return contentValues;
        }
    }

    /**
     * Класс представляет таблицу с описанием таблиц
     */
    private class TableDesc extends Table {
        public static final String TABLE_ID = "table_id";
        public static final String COL_NAME = "col_name";
        public static final String TYPE_ID = "type_id";
        public static final String PK_CONSTR = "pk-constr";
        public static final String UNIQ_CONSTR = "uniq_constr";
        public static final String NOTNULL_CONSTR = "notnull_constr";
        public static final String FK_CONSTR = "fk_constr";
        public static final String FK_COLUMN = "fk_column";
        private List<ContentValues> contentValues;
        public TableDesc() {
            name = "Table description";
            type = Table.SYSTEM_TABLE_NA;
            addColumn(new TableColumn(TABLE_ID, EmptyDatabase.ID, false, false, true, getTableCol(tablesList, TablesList.TABLE_NAME)));
            addColumn(new TableColumn(COL_NAME, EmptyDatabase.TEXT, false, false, true));
            addColumn(new TableColumn(TYPE_ID, EmptyDatabase.ID, false, false, true, dataTypes.getIdColumn()));
            addColumn(new TableColumn(PK_CONSTR, EmptyDatabase.BOOL, false, false, true));
            addColumn(new TableColumn(UNIQ_CONSTR, EmptyDatabase.BOOL, false, false, true));
            addColumn(new TableColumn(NOTNULL_CONSTR, EmptyDatabase.BOOL, false, false, true));
//            addColumn(new TableColumn(FK_CONSTR, EmptyDatabase.BOOL, false, false, true));
            addColumn(new TableColumn(FK_COLUMN, EmptyDatabase.TEXT, false, false, false));

            contentValues = new LinkedList<>();

            for (TableColumn column : dataTypes.getColumns()) {
                contentValues.add(getValues(column));
            }
            for(TableColumn column : productsCategory.getColumns()){
                contentValues.add(getValues(column));
            }
            for(TableColumn column : currency.getColumns()){
                contentValues.add(getValues(column));
            }
            for(TableColumn column : unitMeas.getColumns()){
                contentValues.add(getValues(column));
            }

        }
        public  ContentValues getValues(TableColumn tableColumn) {
            String templateFk = "%1s.%2s";
            ContentValues contentValues = new ContentValues();
            contentValues.put(getTableCol(this, TABLE_ID), tableColumn.getTableParent().getName());
            contentValues.put(getTableCol(this, COL_NAME), tableColumn.getName());
            contentValues.put(getTableCol(this, TYPE_ID), tableColumn.getType());
            contentValues.put(getTableCol(this, PK_CONSTR), (tableColumn.isPrimaryKey()) ? (1) : (0));
            contentValues.put(getTableCol(this, UNIQ_CONSTR), (tableColumn.isUnique()) ? (1) : (0));
            contentValues.put(getTableCol(this, NOTNULL_CONSTR), (tableColumn.isNotNull()) ? (1) : (0));
//            contentValues.put(getTableCol(this, FK_CONSTR), (tableColumn.getFKTable() != null) ? (1) : (0));
            contentValues.put(getTableCol(this, FK_COLUMN), (tableColumn.getFKTable() != null)
                    ?(String.format(
                            templateFk, tableColumn.getFKTable().getTableParent().getName(), tableColumn.getFKTable().getName())
            )
                    :(null));
            return contentValues;
        }
        public List<ContentValues> getContentValues() {
            return contentValues;
        }
    }

    /**
     * Класс определяет таблицу "Типы данных"
     */
    private class DataTypes extends Table {
        public static final String USER_TYPE = "user_type";     //То значение, которое видит и выбирает пользователь
        public static final String JAVA_TYPE = "java_type";     //То значение, как этот тип записывается в java
        public static final String SQL_TYPE = "sql_type";       //То значение, которое используется в запросе SQL
        private List<ContentValues> contentValues;
        public DataTypes() {
            name = "Data types";
            type = Table.SYSTEM_TABLE_RW;

            addColumn(new TableColumn(USER_TYPE, EmptyDatabase.TEXT, true, false, true));
            addColumn(new TableColumn(JAVA_TYPE, EmptyDatabase.TEXT, false, false, true));
            addColumn(new TableColumn(SQL_TYPE, EmptyDatabase.TEXT, false, false, true));

            contentValues = new LinkedList<>();
            contentValues.add(getValues("Текстовый", "String", "varchar(255)"));
            contentValues.add(getValues("Числовой", "Double", "double(10,5)"));
            contentValues.add(getValues("Денежный", "Double", "double(10,2)"));
            contentValues.add(getValues("Целочисленный", "Integer", "integer"));
        }
        private ContentValues getValues(String userType, String javaType, String sqlType) {
            Database.TableColumn userTypeCol = columns.stream().filter(column -> column.getName().equals(USER_TYPE)).findAny().get();
            Database.TableColumn javaTypeCol = columns.stream().filter(column -> column.getName().equals(JAVA_TYPE)).findAny().get();
            Database.TableColumn sqlTypeCol = columns.stream().filter(column -> column.getName().equals(SQL_TYPE)).findAny().get();

            ContentValues contentValues = new ContentValues();
            contentValues.put(userTypeCol, userType);
            contentValues.put(javaTypeCol, javaType);
            contentValues.put(sqlTypeCol, sqlType);
            return contentValues;
        }
        public List<ContentValues> getContentValues() {
            return contentValues;
        }
    }

    /**
     * Класс определяет таблицу "Категория товаров"
     */
    private class ProductsCategory extends Table {
        public static final String GROUP = "group";
        public static final String CATEGORY = "category";

        public ProductsCategory() {
            name = "Products";
            type = Table.SYSTEM_TABLE_RW;

            addColumn(new TableColumn(GROUP, EmptyDatabase.TEXT, true, false, true));
            addColumn(new TableColumn(CATEGORY, EmptyDatabase.TEXT, true, false, true));
        }
    }

    /**
     * Класс определяет таблицу "Валюта"
     */
    private class Currency extends Table {
        public static final String CURRENCY = "currency";
        public static final String COURSE = "course";

        public Currency() {
            name = "Currency";
            type = Table.SYSTEM_TABLE_RW;

            addColumn(new TableColumn(CURRENCY, EmptyDatabase.TEXT, true, false, true));
            addColumn(new TableColumn(COURSE, EmptyDatabase.NUMBER, false, false, true));
        }
    }

    /**
     * Класс определяет таблицу "Единицы измерения"
     */
    private class UnitMeas extends Table {
        public static final String UNIT_MEAS = "unit_meas";
        private List<ContentValues> contentValues;

        public UnitMeas() {
            name = "Unit meas";
            type = Table.SYSTEM_TABLE_RW;

            addColumn(new TableColumn(UNIT_MEAS, EmptyDatabase.TEXT, true, false, true));

            contentValues = new LinkedList<>();

            contentValues.add(getValues("м."));
            contentValues.add(getValues("мм."));
            contentValues.add(getValues("кг."));
            contentValues.add(getValues("г."));
            contentValues.add(getValues("шт."));
            contentValues.add(getValues("компл."));
        }

        private ContentValues getValues(String tableType) {
            ContentValues contentValues = new ContentValues();
            Database.TableColumn tableTypeCol = columns
                    .stream()
                    .filter(column -> column.getName().equals(UNIT_MEAS))
                    .collect(Collectors.toList()).get(0);
            contentValues.put(tableTypeCol, tableType);
            return contentValues;
        }
        public List<ContentValues> getContentValues() {
            return contentValues;
        }
    }

    /**
     * Тестовая таблица
     * */
    private class TestTable extends Table{
        public static final String COL1 = "col 1";
        public static final String COL2 = "col 2";
        public static final String COL3 = "col 3";
        private List<ContentValues> contentValues;
        private ContentValues forTableList;
        private List<ContentValues> forTableDesc;
        public TestTable() {
            name = "Test table";
            type = Table.TABLE;
            contentValues = new LinkedList<>();
            forTableDesc = new LinkedList<>();
            addColumn(new TableColumn(COL1, EmptyDatabase.TEXT, false, false, false, getTableCol(unitMeas, UnitMeas.UNIT_MEAS)));
            addColumn(new TableColumn(COL2, EmptyDatabase.NUMBER, false, false, false));
            addColumn(new TableColumn(COL3, EmptyDatabase.NUMBER, false, false, false));

            contentValues.add(getValues("м.", 666d, 333d));
            contentValues.add(getValues("мм.", 432186d, 8d));
            contentValues.add(getValues("компл.", 921d, 262d));

            forTableList = tablesList.getValues(this);

            forTableDesc.add(tableDesc.getValues(getTableCol(this, COL1)));
            forTableDesc.add(tableDesc.getValues(getTableCol(this, COL2)));
            forTableDesc.add(tableDesc.getValues(getTableCol(this, COL3)));
        }

        private ContentValues getValues(String col1, Double col2, Double col3){
            ContentValues contentValues = new ContentValues();
            contentValues.put(getTableCol(this, COL1), col1);
            contentValues.put(getTableCol(this, COL2), col2);
            contentValues.put(getTableCol(this, COL3), col3);
            return contentValues;
        }
        public List<ContentValues> getContentValues(){
            return contentValues;
        }
    }

    /**
     * Функция заполняет БД
     */
    public void fill(File dbUser) throws SQLException, ClassNotFoundException {

        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Заполняю БД");
        DataBaseManager dbManager = DataBaseManager.getDbManager();

        dbManager.createTable(tableTypes, dbUser);
        dbManager.createTable(tablesList, dbUser);
        dbManager.createTable(tableDesc, dbUser);
        dbManager.createTable(dataTypes, dbUser);
        dbManager.createTable(productsCategory, dbUser);
        dbManager.createTable(currency, dbUser);
        dbManager.createTable(unitMeas, dbUser);
        dbManager.createTable(testTable, dbUser);

        for (ContentValues contentValues : tableTypes.getContentValues()) {
            dbManager.insert(tableTypes, contentValues, dbUser);
        }

        for (ContentValues contentValues : dataTypes.getContentValues()) {
            dbManager.insert(dataTypes, contentValues, dbUser);
        }

        for (ContentValues contentValues : unitMeas.getContentValues()) {
            dbManager.insert(unitMeas, contentValues, dbUser);
        }

        for (ContentValues contentValues : tablesList.getContentValues()) {
            dbManager.insert(tablesList, contentValues, dbUser);
        }

        for (ContentValues contentValues : tableDesc.getContentValues()) {
            dbManager.insert(tableDesc, contentValues, dbUser);
        }

        for (ContentValues contentValues : testTable.getContentValues()){
            dbManager.insert(testTable, contentValues, dbUser);
        }
        dbManager.insert(tablesList, testTable.forTableList, dbUser);
        for (ContentValues contentValues : testTable.forTableDesc){
            dbManager.insert(tableDesc, contentValues, dbUser);
        }
    }

}

