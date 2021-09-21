package ThothCore.EmptyDatabase;

import Database.*;

import java.io.File;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс описывает пустую пользовательскую БД
 */
public class EmptyDatabase {

    protected static final String ID = "integer";
    protected static final String TEXT = "varchar(255)";
    protected static final String BOOL = "tinyint";
    protected static final String NUMBER = "double(10,5)";

    private List<Table> tables;

    public EmptyDatabase() {
        tables = new LinkedList<>();

        tables.add(new TableTypes());
        tables.add(new DataTypes());
        tables.add(new ProductsCategory());
        tables.add(new Currency());
        tables.add(new UnitMeas());
        tables.add(new TablesList());
        tables.add(new TableDesc());
        tables.add(new TestTable());
    }

    public Table getTable(String name){
        return tables
                .stream()
                .filter(table -> table.getName().equals(name))
                .findFirst()
                .get();
    }

    /**
     * Класс определяет таблицу "Типы таблиц"
     */
    public class TableTypes extends Table {
        public static final String NAME = "table_types";
        public static final String TABLE_TYPE = "table_type";

        public TableTypes() {
            super();
            name = NAME;
            type = Table.SYSTEM_TABLE_NA;

            addColumn(new TableColumn(TABLE_TYPE, EmptyDatabase.TEXT, false, true));

            contentValues.add(getValues(Table.TABLE));
            contentValues.add(getValues(Table.SYSTEM_TABLE_RO));
            contentValues.add(getValues(Table.SYSTEM_TABLE_RW));
            contentValues.add(getValues(Table.SYSTEM_TABLE_NA));
        }
        private ContentValues getValues(String tableType) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(getTableCol(this, TABLE_TYPE), tableType);
            return contentValues;
        }
        public List<ContentValues> getContentValues() {
            return contentValues;
        }
    }

    /**
     * Класс определяет таблицу "Список таблиц"
     */
    public class TablesList extends Table {
        public static final String NAME = "tables_list";
        public static final String TABLE_NAME = "table_name";
        public static final String TABLE_TYPE_ID = "table_type_id";
        public TablesList() {
            super();
            name = NAME;
            type = Table.SYSTEM_TABLE_NA;

            addColumn(
                    new TableColumn(TABLE_NAME, EmptyDatabase.TEXT, false, true)
            );
            Table tableTypes = getTable(TableTypes.NAME);
            addColumn(
                    new TableColumn(
                            TABLE_TYPE_ID, EmptyDatabase.ID, false, true, tableTypes.getTableCol(tableTypes, TableTypes.TABLE_TYPE)
                    )
            );

            contentValues.add(
                    getValues(getTable(DataTypes.NAME))
            );
            contentValues.add(
                    getValues(getTable(ProductsCategory.NAME))
            );
            contentValues.add(
                    getValues(getTable(Currency.NAME))
            );
            contentValues.add(
                    getValues(getTable(UnitMeas.NAME))
            );
        }
        public ContentValues getValues(Table table) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(getTableCol(this, TABLE_TYPE_ID), table.getType());
            contentValues.put(getTableCol(this, TABLE_NAME), table.getName());
            return contentValues;
        }
        public List<ContentValues> getContentValues() {
            return contentValues;
        }
    }

    /**
     * Класс представляет таблицу с описанием таблиц
     */
    public class TableDesc extends Table {
        public static final String NAME = "table_desc";
        public static final String TABLE_ID = "table_id";
        public static final String COL_NAME = "col_name";
        public static final String TYPE_ID = "type_id";
        public static final String PK_CONSTR = "pk_constr";
        public static final String UNIQ_CONSTR = "uniq_constr";
        public static final String NOTNULL_CONSTR = "notnull_constr";
        public static final String FK_CONSTR = "fk_constr";
        public static final String FK_TABLE_ID = "fk_table_name_id";
        public static final String FK_COLUMN_ID = "fk_column_name_id";
        public TableDesc() {
            super();
            name = NAME;
            type = Table.SYSTEM_TABLE_NA;
            addColumn(
                    new TableColumn(TABLE_ID, EmptyDatabase.ID, false, true, getTableCol(getTable(TablesList.NAME), TablesList.TABLE_NAME))
            );
            addColumn(new TableColumn(COL_NAME, EmptyDatabase.TEXT, false, true));
            Table dataTypes = getTable(DataTypes.NAME);
            addColumn(
                    new TableColumn(
                            TYPE_ID, EmptyDatabase.ID, false, true, dataTypes.getTableCol(dataTypes, Table.ID)
                    )
            );
            addColumn(new TableColumn(PK_CONSTR, EmptyDatabase.BOOL, false, true));
            addColumn(new TableColumn(UNIQ_CONSTR, EmptyDatabase.BOOL, false, true));
            addColumn(new TableColumn(NOTNULL_CONSTR, EmptyDatabase.BOOL, false, true));
//            addColumn(new TableColumn(FK_CONSTR, EmptyDatabase.BOOL, false, false, true));
            addColumn(new TableColumn(FK_TABLE_ID, EmptyDatabase.ID, false, false, getTableCol(getTable(TablesList.NAME), TablesList.TABLE_NAME)));
            addColumn(new TableColumn(FK_COLUMN_ID, EmptyDatabase.ID, false, false, getTableCol(COL_NAME)));

            for (TableColumn column : getTable(DataTypes.NAME).getColumns()) {
                contentValues.add(getValues(column));
            }
            for(TableColumn column : getTable(ProductsCategory.NAME).getColumns()){
                contentValues.add(getValues(column));
            }
            for(TableColumn column : getTable(Currency.NAME).getColumns()){
                contentValues.add(getValues(column));
            }
            for(TableColumn column : getTable(UnitMeas.NAME).getColumns()){
                contentValues.add(getValues(column));
            }

        }
        public  ContentValues getValues(TableColumn tableColumn) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(getTableCol(TABLE_ID), tableColumn.getTableParent().getName());
            contentValues.put(getTableCol(COL_NAME), tableColumn.getName());
            contentValues.put(getTableCol(TYPE_ID), tableColumn.getType());
            contentValues.put(getTableCol(PK_CONSTR), (tableColumn.isPrimaryKey()) ? (1) : (0));
            contentValues.put(getTableCol(UNIQ_CONSTR), (tableColumn.isUnique()) ? (1) : (0));
            contentValues.put(getTableCol(NOTNULL_CONSTR), (tableColumn.isNotNull()) ? (1) : (0));
//            contentValues.put(getTableCol(this, FK_CONSTR), (tableColumn.getFKTable() != null) ? (1) : (0));
            contentValues.put(getTableCol(FK_TABLE_ID), (tableColumn.getFKTableCol() != null)?(tableColumn.getFKTableCol().getTableParent().getName()):(null));
            contentValues.put(getTableCol(FK_COLUMN_ID), (tableColumn.getFKTableCol() != null)?(tableColumn.getFKTableCol().getName()):(null));
            return contentValues;
        }
        public List<ContentValues> getContentValues() {
            return contentValues;
        }
    }

    /**
     * Класс определяет таблицу "Типы данных"
     */
    public class DataTypes extends Table {
        public static final String NAME = "data_types";
        public static final String USER_TYPE = "user_type";     //То значение, которое видит и выбирает пользователь
        public static final String JAVA_TYPE = "java_type";     //То значение, как этот тип записывается в java
        public static final String SQL_TYPE = "sql_type";       //То значение, которое используется в запросе SQL
        public DataTypes() {
            super();
            name = NAME;
            type = Table.SYSTEM_TABLE_RW;

            addColumn(new TableColumn(USER_TYPE, EmptyDatabase.TEXT, false, true));
            addColumn(new TableColumn(JAVA_TYPE, EmptyDatabase.TEXT, false, true));
            addColumn(new TableColumn(SQL_TYPE, EmptyDatabase.TEXT, false, true));

            contentValues.add(getValues("Текстовый", "String", "varchar(255)"));
            contentValues.add(getValues("Числовой", "Double", "double(10,5)"));
            contentValues.add(getValues("Денежный", "Double", "double(10,2)"));
            contentValues.add(getValues("Целочисленный", "Integer", "integer"));
        }
        private ContentValues getValues(String userType, String javaType, String sqlType) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(getTableCol(this, USER_TYPE), userType);
            contentValues.put(getTableCol(this, JAVA_TYPE), javaType);
            contentValues.put(getTableCol(this, SQL_TYPE), sqlType);
            return contentValues;
        }
        public List<ContentValues> getContentValues() {
            return contentValues;
        }
    }

    /**
     * Класс определяет таблицу "Категория товаров"
     */
    public class ProductsCategory extends Table {
        public static final String NAME = "products";
        public static final String GROUP = "group";
        public static final String CATEGORY = "category";

        public ProductsCategory() {
            name = NAME;
            type = Table.SYSTEM_TABLE_RW;

            addColumn(new TableColumn(GROUP, EmptyDatabase.TEXT, false, true));
            addColumn(new TableColumn(CATEGORY, EmptyDatabase.TEXT, false, true));
        }
    }

    /**
     * Класс определяет таблицу "Валюта"
     */
    public class Currency extends Table {
        public static final String NAME = "currency";
        public static final String CURRENCY = "currency";
        public static final String COURSE = "course";

        public Currency() {
            name = NAME;
            type = Table.SYSTEM_TABLE_RW;

            addColumn(new TableColumn(CURRENCY, EmptyDatabase.TEXT, false, true));
            addColumn(new TableColumn(COURSE, EmptyDatabase.NUMBER, false, true));
        }
    }

    /**
     * Класс определяет таблицу "Единицы измерения"
     */
    public class UnitMeas extends Table {
        public static final String NAME = "unit_meas";
        public static final String UNIT_MEAS = "unit_meas";

        public UnitMeas() {
            name = NAME;
            type = Table.SYSTEM_TABLE_RW;

            addColumn(new TableColumn(UNIT_MEAS, EmptyDatabase.TEXT, false, true));

            contentValues.add(getValues("м."));
            contentValues.add(getValues("мм."));
            contentValues.add(getValues("кг."));
            contentValues.add(getValues("г."));
            contentValues.add(getValues("шт."));
            contentValues.add(getValues("компл."));
        }
        private ContentValues getValues(String tableType) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(getTableCol(this, UNIT_MEAS), tableType);
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
        public static final String NAME = "Test table";
        public static final String COL1 = "col 1";
        public static final String COL2 = "col 2";
        public static final String COL3 = "col 3";
        private ContentValues forTableList;
        private List<ContentValues> forTableDesc;
        public TestTable() {
            name = NAME;
            type = Table.TABLE;

            forTableDesc = new LinkedList<>();
            addColumn(new TableColumn(COL1, EmptyDatabase.TEXT, false, false, getTableCol(getTable(UnitMeas.NAME), UnitMeas.UNIT_MEAS)));
            addColumn(new TableColumn(COL2, EmptyDatabase.NUMBER, false, false));
            addColumn(new TableColumn(COL3, EmptyDatabase.NUMBER, false, false));

            contentValues.add(getValues("м.", 666d, 333d));
            contentValues.add(getValues("мм.", 432186d, 8d));
            contentValues.add(getValues("компл.", 921d, 262d));

            TablesList tableList = (TablesList) getTable(TablesList.NAME);
            forTableList = tableList.getValues(this);

            TableDesc tableDesc = (TableDesc) getTable(TableDesc.NAME);
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

        for (Table table : tables){
            dbManager.createTable(table, dbUser);
        }
//        dbManager.createTable(tableTypes, dbUser);
//        dbManager.createTable(tablesList, dbUser);
//        dbManager.createTable(tableDesc, dbUser);
//        dbManager.createTable(dataTypes, dbUser);
//        dbManager.createTable(productsCategory, dbUser);
//        dbManager.createTable(currency, dbUser);
//        dbManager.createTable(unitMeas, dbUser);
//        dbManager.createTable(testTable, dbUser);

        TableTypes tableTypes = (TableTypes) getTable(TableTypes.NAME);
        for (ContentValues contentValues : tableTypes.getContentValues()) {
            dbManager.insert(tableTypes, contentValues, dbUser);
        }
        DataTypes dataTypes = (DataTypes) getTable(DataTypes.NAME);
        for (ContentValues contentValues : dataTypes.getContentValues()) {
            dbManager.insert(dataTypes, contentValues, dbUser);
        }

        UnitMeas unitMeas = (UnitMeas) getTable(UnitMeas.NAME);
        for (ContentValues contentValues : unitMeas.getContentValues()) {
            dbManager.insert(unitMeas, contentValues, dbUser);
        }

        TablesList tablesList = (TablesList) getTable(TablesList.NAME);
        for (ContentValues contentValues : tablesList.getContentValues()) {
            dbManager.insert(tablesList, contentValues, dbUser);
        }

        TableDesc tableDesc = (TableDesc) getTable(TableDesc.NAME);
        for (ContentValues contentValues : tableDesc.getContentValues()) {
            dbManager.insert(tableDesc, contentValues, dbUser);
        }

        TestTable testTable = (TestTable) getTable(TestTable.NAME);
        for (ContentValues contentValues : testTable.getContentValues()){
            dbManager.insert(testTable, contentValues, dbUser);
        }
        dbManager.insert(tablesList, testTable.forTableList, dbUser);
        for (ContentValues contentValues : testTable.forTableDesc){
            dbManager.insert(tableDesc, contentValues, dbUser);
        }
    }

    public List<Table> getTables() {
        return tables;
    }
}

