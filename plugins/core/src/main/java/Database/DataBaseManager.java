package Database;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

public class DataBaseManager {

    private static Logger LOG;
    private static DataBaseManager dbManager;
    private Connection conn;
    private GuardianOfWisdom guardianOfWisdom;

    static {
        LOG = Logger.getLogger(DataBaseManager.class.getName());
    }

    public DataBaseManager() throws SQLException, ClassNotFoundException {
        guardianOfWisdom = new GuardianOfWisdom();
    }

    public static DataBaseManager getDbManager() throws SQLException, ClassNotFoundException {
        if (dbManager == null) {
            dbManager = new DataBaseManager();
        }
        return dbManager;
    }

    /**
     * Функция создает новую БД
     * */
    public void createDatabase(File db) throws SQLException, ClassNotFoundException {
        DataBaseWrapper.openConnetion(db).close();
    }

    /**
     * Функция закрывает соединение с БД
     * */
    public void closeConnection() throws SQLException {
        conn.close();
        conn = null;
    }

    /**
     * Функция удаляет все записи из таблицы
     */
    public void clear(String tableName)
            throws SQLException {
    }

    /**
     * Функция отвечает за создание новой таблицы в БД
     */
    public void createTable(Table table) throws SQLException {
        DataBaseWrapper.createTable(table.getName(), table.getColumns(), conn);
    }

    /**
     * Функция отвечает за удаление записи из таблицы БД
     */
    public void delete(Table table,
                       WhereValues whereValues)
            throws SQLException {
        DataBaseWrapper.delete(conn, table, whereValues);
    }

    /**
     * Функция отвечает за вставку записей в таблицу БД
     *
     * @param tableName     наименование таблицы для вставки записей
     * @param contentValues Объект вставляемых данных
     */
    public void insert(String tableName,
                       ContentValues contentValues)
                       throws SQLException {
        DataBaseWrapper.insert(tableName, contentValues, conn);
    }

    /**
     * Функция открывает соединение с БД/
     * @param db Путь до базы данных
     * */
    public void openConnection(File db) throws SQLException, ClassNotFoundException {
        conn = DataBaseWrapper.openConnetion(db);
    }

    /**
     * Функция отвечает за переименовывание колонок в таблице
     * */
    public void renameColumn(String tableName,
                             TableColumn oldTableColumn,
                             TableColumn newTableColumn) throws SQLException {
    }

    /**
     * Функция отвечает за удаление колонки из таблицы
     * */
    public void removeColumn(String tableName,
                             TableColumn tableColumn) throws SQLException {
    }

    /**
     * Функция отвечает за удаление таблицы
     */
    public void removeTables(ArrayList<String> tablesName) {
    }

    /**
     * Функция отвечает за переименование таблицы
     */
    public void renameTable(String oldName,
                            String newName) throws SQLException {

    }

    /**
     * Функция отвечает за перемещение компонента из одной таблицы в другую
     */
    public void replace(String fromTable,
                        String toTable,
                        ContentValues contentValues,
                        WhereValues whereValues) throws SQLException{
    }

    /**
     * Функция возвращает выборку строк из таблицы
     */
    public List<LinkedHashMap<TableColumn, Object>> select(Table table,
                                                           List<TableColumn> columns,
                                                           WhereValues where) throws SQLException {
        ResultSet select = DataBaseWrapper.select(table, columns, where, conn);
        List<LinkedHashMap<TableColumn, Object>> res = new LinkedList<>();
        while (select.next()){
            LinkedHashMap<TableColumn, Object> row = new LinkedHashMap<>();
            for (TableColumn tableColumn : columns) {
                row.put(tableColumn, select.getObject(tableColumn.getName()));
            }
            res.add(row);
        }
        return res;
    }

    /**
     * Функция отвечает за обновление щаписи в таблице
     */
    public void update(Table table,
                       ContentValues contentValues,
                       WhereValues whereValues)
            throws SQLException {
        DataBaseWrapper.update(table, contentValues, whereValues, conn);
    }
}
