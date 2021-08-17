package Database;

import java.io.File;
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
     * Функция удаляет все записи из таблицы
     */
    public void clear(String tableName)
            throws SQLException {
    }

    /**
     * Функция отвечает за создание новой таблицы в БД
     */
    private void createTable(String tableName,
                             List<TableColumn> column,
                             Connection conn) throws SQLException {

    }

    /**
     * Функция отвечает за удаление записи из таблицы БД
     */
    public void delete(String tableName,
                       WhereValues whereValues)
            throws SQLException {

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

    }


    /**
     * Функция возвращает карту наименований столбцов и их типов. Где ключ - наименование столбца, значение - его тип.
     *
     * @return
     */
    public List<TableColumn> getColumnsType(String tableName) throws SQLException {

    }

    /**
     * Функция возвращает выборку строк из таблицы
     */
    public List<LinkedHashMap<TableColumn, Object>> getTableData(String tableName) throws SQLException {

    }

    /**
     * Функция возвращает список таблиц БД
     *
     * @return Список таблиц БД
     */
    public ArrayList<String> getTablesList() throws SQLException {

    }

    /**
     * Функция открывает соединение с БД/
     * @param db Путь до базы данных
     * */
    public void openConnection(File db) throws SQLException, ClassNotFoundException {
        conn = DataBaseWrapper.openConnetion(db.getAbsolutePath());
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
