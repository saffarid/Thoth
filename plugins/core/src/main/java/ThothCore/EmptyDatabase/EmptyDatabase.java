package ThothCore.EmptyDatabase;

import Database.ContentValues;
import Database.DataBaseManager;
import Database.Table;
import Database.TableColumn;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Класс описывает пустую пользовательскую БД
 * */
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

    public EmptyDatabase() {

        tableTypes = new TableTypes();
        dataTypes = new DataTypes();
        productsCategory = new ProductsCategory();
        currency = new Currency();
        unitMeas = new UnitMeas();

        tablesList = new TablesList();
        tableDesc = new TableDesc();

    }

    /**
     * Класс определяет таблицу "Типы таблиц"
     * */
    private class TableTypes extends Table{
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

        private ContentValues getValues(String tableType){
            ContentValues contentValues = new ContentValues();
            Database.TableColumn tableTypeCol = columns
                    .stream()
                    .filter(column -> column.getName().equals(TABLE_TYPE))
                    .collect(Collectors.toList()).get(0);
            contentValues.put(tableTypeCol, tableType);
            return contentValues;
        }
        public List<ContentValues> getContentValues(){
            return contentValues;
        }
    }

    /**
     * Класс определяет таблицу "Список таблиц"
     * */
    private class TablesList extends Table{
        public static final String TABLE_NAME = "table_name";
        public static final String TABLE_TYPE_ID = "table_type_id";
        private List<ContentValues> contentValues;
        public TablesList() {
            name = "Tables list";
            type = Table.SYSTEM_TABLE_NA;

            addColumn(new TableColumn(TABLE_NAME, EmptyDatabase.TEXT, false, false, true));
            addColumn(new TableColumn(TABLE_TYPE_ID, EmptyDatabase.ID, true, false, true, tableTypes.getIdColumn()));

            contentValues = new LinkedList<>();
            contentValues.add(getValues(dataTypes));
        }

        private ContentValues getValues(Table table){
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
        public List<ContentValues> getContentValues(){
            return contentValues;
        }
    }

    /**
     * Класс представляет таблицу с описанием таблиц
     * */
    private class TableDesc extends Table{
        public static final String TABLE_ID = "table_id";
        public static final String COL_NAME = "col_name";
        public static final String TYPE_ID = "type_id";
        public static final String PK_CONSTR = "pk-constr";
        public static final String UNIQ_CONSTR = "uniq_constr";
        public static final String NOTNULL_CONSTR = "notnull_constr";
        public static final String FK_CONSTR = "fk_constr";
        public TableDesc() {
            name = "Table description";
            type = Table.SYSTEM_TABLE_NA;
            addColumn(new TableColumn(TABLE_ID, EmptyDatabase.ID, true, false, true, tablesList.getIdColumn()));
            addColumn(new TableColumn(COL_NAME, EmptyDatabase.TEXT, false, false, true));
            addColumn(new TableColumn(TYPE_ID, EmptyDatabase.ID, true, false, true, tableTypes.getIdColumn()));
            addColumn(new TableColumn(PK_CONSTR, EmptyDatabase.BOOL, false, false, true));
            addColumn(new TableColumn(UNIQ_CONSTR, EmptyDatabase.BOOL, false, false, true));
            addColumn(new TableColumn(NOTNULL_CONSTR, EmptyDatabase.BOOL, false, false, true));
            addColumn(new TableColumn(FK_CONSTR, EmptyDatabase.BOOL, false, false, true));
        }
    }

    /**
     * Класс определяет таблицу "Типы данных"
     * */
    private class DataTypes extends Table{
        public static final String USER_TYPE = "user_type";     //То значение, которое видит и выбирает пользователь
        public static final String JAVA_TYPE = "java_type";     //То значение, как этот тип записывается в java
        public static final String SQL_TYPE = "sql_type";       //То значение, которое используется в запросе SQL
        private List<ContentValues> contentValuesList;
        public DataTypes() {
            name = "Data types";
            type = Table.SYSTEM_TABLE_RW;

            addColumn(new TableColumn(USER_TYPE, EmptyDatabase.TEXT, true, false, true));
            addColumn(new TableColumn(JAVA_TYPE, EmptyDatabase.TEXT, false, false, true));
            addColumn(new TableColumn(SQL_TYPE, EmptyDatabase.TEXT, false, false, true));

            contentValuesList = new LinkedList<>();
            contentValuesList.add(getContentValues("Текстовый", "String", "varchar(255)"));
            contentValuesList.add(getContentValues("Числовой", "Double", "double(10, 5)"));
            contentValuesList.add(getContentValues("Денежный", "Double", "double(10, 2)"));
        }

        private ContentValues getContentValues(String userType, String javaType, String sqlType){
            Database.TableColumn userTypeCol = columns.stream().filter(column -> column.getName().equals(USER_TYPE)).findAny().get();
            Database.TableColumn javaTypeCol = columns.stream().filter(column -> column.getName().equals(JAVA_TYPE)).findAny().get();
            Database.TableColumn sqlTypeCol  = columns.stream().filter(column -> column.getName().equals(SQL_TYPE)).findAny().get();

            ContentValues contentValues = new ContentValues();
            contentValues.put(userTypeCol, userType);
            contentValues.put(javaTypeCol, javaType);
            contentValues.put(sqlTypeCol, sqlType);
            return contentValues;
        }
        public List<ContentValues> getContentValuesList(){
            return contentValuesList;
        }
    }

    /**
     * Класс определяет таблицу "Категория товаров"
     * */
    private class ProductsCategory extends Table{
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
     * */
    private class Currency extends Table{
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
     * */
    private class UnitMeas extends Table{
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

        private ContentValues getValues(String tableType){
            ContentValues contentValues = new ContentValues();
            Database.TableColumn tableTypeCol = columns
                    .stream()
                    .filter(column -> column.getName().equals(UNIT_MEAS))
                    .collect(Collectors.toList()).get(0);
            contentValues.put(tableTypeCol, tableType);
            return contentValues;
        }
        public List<ContentValues> getContentValues(){
            return contentValues;
        }
    }

    /**
     * Функция заполняет БД
     * */
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

        for(ContentValues contentValues: tableTypes.getContentValues()){
            dbManager.insert(tableTypes, contentValues, dbUser);
        }

        for(ContentValues contentValues: dataTypes.getContentValuesList()){
            dbManager.insert(dataTypes, contentValues, dbUser);
        }

        for(ContentValues contentValues: unitMeas.getContentValues()){
            dbManager.insert(unitMeas, contentValues, dbUser);
        }

        for(ContentValues contentValues: tablesList.getContentValues()){
            dbManager.insert(tablesList, contentValues, dbUser);
        }

    }

}

