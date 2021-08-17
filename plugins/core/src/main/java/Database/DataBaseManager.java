package Database;

import DataBase.DataBaseInfo;
import DataBase.TableColumn;
import Model.DataBase.Exceptions.InsertCollision;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseManager {

    private static Logger LOG;
    private static DataBaseManager dbManager;
    private Connection storeConn;
    private Connection informConn;
    private GuardianOfWisdom guardianOfWisdom;

    static {
        LOG = Main.LOG;
    }

    public DataBaseManager() throws SQLException, ClassNotFoundException {
        guardianOfWisdom = new GuardianOfWisdom();
        getStoreConn();
        createInformConn();
    }

    public static DataBaseManager getDbManager() throws SQLException, ClassNotFoundException {
        if (dbManager == null) {
            dbManager = new DataBaseManager();
        }
        return dbManager;
    }

    public Connection getStoreConn() throws SQLException, ClassNotFoundException {
        String dbName = DataBaseInfo.DB_NAME;
        storeConn = getConn(dbName);

        return storeConn;
    }

    /**
     * Функция удаляет все записи из таблицы
     */
    public void clear(String tableName)
            throws SQLException {
        DataBaseWrapper.clearTable(storeConn, tableName);
        guardianOfWisdom.getTableData(tableName, storeConn, informConn);
    }

    /**
     * Функция отвечает за создание новой таблицы в БД
     */
    private void createTable(String tableName,
                             List<TableColumn> column,
                             Connection conn) throws SQLException {

        DataBaseWrapper.createTable(tableName, column, conn);

        if (conn.equals(storeConn)) {
            ContentValues sysTableTables = new ContentValues();
            sysTableTables.put(new TableColumn(DataBaseInfo.Information.Tables.TABLE_NAME,
                                               DataBaseInfo.KEY_TYPE_TEXT,
                                               false, false, true),
                               tableName);
            DataBaseWrapper.insert(DataBaseInfo.Information.Tables.NAME, sysTableTables, informConn);

            column.stream().forEach(tableColumn -> {
                ContentValues sysTableColumns = new ContentValues();

                sysTableColumns.put(new TableColumn(DataBaseInfo.Information.Tables.TABLE_NAME,
                                                    DataBaseInfo.KEY_TYPE_TEXT,
                                                    false, false, true),
                                    tableName);
                sysTableColumns.put(new TableColumn(DataBaseInfo.Information.Columns.COLUMN_NAME,
                                                    DataBaseInfo.KEY_TYPE_TEXT,
                                                    false, false, true),
                                    tableColumn.getName());
                sysTableColumns.put(new TableColumn(DataBaseInfo.Information.Columns.COLUMN_TYPE,
                                                    DataBaseInfo.KEY_TYPE_TEXT,
                                                    false, false, true),
                                    tableColumn.getType());
                sysTableColumns.put(new TableColumn(DataBaseInfo.Information.Columns.IS_PRIMARY_KEY,
                                                    DataBaseInfo.KEY_TYPE_ID,
                                                    false, false, true),
                                    (tableColumn.isPrimaryKey()) ? (1) : (0));
                sysTableColumns.put(new TableColumn(DataBaseInfo.Information.Columns.IS_UNIQUE,
                                                    DataBaseInfo.KEY_TYPE_ID,
                                                    false, false, true),
                                    (tableColumn.isUnique()) ? (1) : (0));
                sysTableColumns.put(new TableColumn(DataBaseInfo.Information.Columns.IS_NOT_NULL,
                                                    DataBaseInfo.KEY_TYPE_ID,
                                                    false, false, true),
                                    (tableColumn.isNotNull()) ? (1) : (0));

                try {
                    DataBaseWrapper.insert(DataBaseInfo.Information.Columns.NAME, sysTableColumns, informConn);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
        }
    }

    /**
     * Функция отвечает за удаление записи из таблицы БД
     */
    public void delete(String tableName,
                       WhereValues whereValues)
            throws SQLException {
        DataBaseWrapper.delete(storeConn, tableName, whereValues);
        guardianOfWisdom.getTableData(tableName, storeConn, informConn);
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
        DataBaseWrapper.insert(tableName, contentValues, storeConn);
        guardianOfWisdom.getTableData(tableName, storeConn, informConn);
    }


    /**
     * Функция возвращает карту наименований столбцов и их типов. Где ключ - наименование столбца, значение - его тип.
     *
     * @return
     */
    public List<TableColumn> getColumnsType(String tableName) throws SQLException {
        return guardianOfWisdom.getColumns(tableName, informConn);
    }

    /**
     * Функция возвращает выборку строк из таблицы
     */
    public List<LinkedHashMap<TableColumn, Object>> getTableData(String tableName) throws SQLException {
        return guardianOfWisdom.getTableData(tableName, storeConn, informConn);
    }

    /**
     * Функция возвращает список таблиц БД
     *
     * @return Список таблиц БД
     */
    public ArrayList<String> getTablesList() throws SQLException {
        return guardianOfWisdom.getTablesList(informConn);
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
     * Функция отвечает за обновление щаписи в таблице
     */
    public void update(String tableName,
                       ContentValues contentValues,
                       WhereValues whereValues)
            throws SQLException {
    }
}
