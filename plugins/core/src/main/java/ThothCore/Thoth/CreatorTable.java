package ThothCore.Thoth;

import Database.ContentValues;
import Database.DataBaseManager;
import Database.Table;
import Database.TableColumn;
import ThothCore.EmptyDatabase.EmptyDatabase;

import java.io.File;
import java.sql.SQLException;

/**
 * Объект отвечает за создание новых таблиц в БД и
 * занесение информации о вновь созданной таблице в системные таблицы.
 * */
public class CreatorTable {

    private DataBase db;

    private DataBaseManager dbManager;

    /**
     * Файл пользовательской БД
     * */
    private File dbFile;

    /**
     * Создаваемая таблица
     * */
    private Table table;


    public CreatorTable(DataBase db,
                        File dbFile,
                        Table table) throws SQLException, ClassNotFoundException {
        this.dbFile = dbFile;
        this.table = table;

        dbManager = DataBaseManager.getDbManager();
    }

    /**
     * Функция отвечает за создание новой таблицы
     * */
    private void create() throws SQLException {
        dbManager.createTable(table, dbFile);
    }

    public void createTable() throws SQLException {
        //Сменить способ закрепления транзакции
        //Если на каком-то этапе произошла ошибка, вся транзакция должна отмениться
        create();
        insertIntoSysTable();
        //Иначе закрепляем транзакцию и спокойно работаем дальше
    }

    /**
     * Функция формирует запросы на вставку информации в системные таблицы: Tables list, Table desc,
     * */
    private void insertIntoSysTable() throws SQLException {
        insertIntoTablesList();
        insertIntoTableDesc();
    }

    /**
     * Функция формирцует запрос на вставку информации о таблицу в Table desc
     * */
    private void insertIntoTableDesc() throws SQLException {
        Table tableDesc = db.getTable(EmptyDatabase.TableDesc.NAME);

        /*
         *  Проходим по всем колонкам таблицы, формируем объект contentValues
         * */
        for(TableColumn column : table.getColumns()) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(tableDesc.getTableCol(EmptyDatabase.TableDesc.TABLE_ID),       column.getTableParent().getName());
            contentValues.put(tableDesc.getTableCol(EmptyDatabase.TableDesc.COL_NAME),       column.getName());
            contentValues.put(tableDesc.getTableCol(EmptyDatabase.TableDesc.TYPE_ID),        column.getType());
            contentValues.put(tableDesc.getTableCol(EmptyDatabase.TableDesc.PK_CONSTR),     (column.isPrimaryKey()) ? (1) : (0));
            contentValues.put(tableDesc.getTableCol(EmptyDatabase.TableDesc.UNIQ_CONSTR),   (column.isUnique()) ? (1) : (0));
            contentValues.put(tableDesc.getTableCol(EmptyDatabase.TableDesc.NOTNULL_CONSTR),(column.isNotNull()) ? (1) : (0));
            contentValues.put(tableDesc.getTableCol(EmptyDatabase.TableDesc.FK_COLUMN),     (column.getFKTableCol() != null)?(column.getFKTableCol().getFullName()):(null));

            dbManager.insert(tableDesc, contentValues, dbFile);
        }
    }

    /**
     * Функция формирует запрос на вставку информации о таблице в Tables list
     * */
    private void insertIntoTablesList() throws SQLException {
        Table tablesList = db.getTable(EmptyDatabase.TablesList.NAME);
        ContentValues contentValues = new ContentValues();

        contentValues.put(
                tablesList.getTableCol(EmptyDatabase.TablesList.TABLE_NAME), table.getName()
        );

        contentValues.put(
                tablesList.getTableCol(EmptyDatabase.TablesList.TABLE_TYPE_ID), table.getType()
        );

        dbManager.insert(tablesList, contentValues, dbFile);
    }
}
