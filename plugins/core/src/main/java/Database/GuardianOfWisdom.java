package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GuardianOfWisdom {

    /**
     * Список созданных таблиц
     */
    private ArrayList<String> tablesList;
    /**
     * Карта хранения считанных данных. Где ключ - наименование таблицы, Значение - список значений по строкам.
     * Данные таблицы хранятся в списке,
     * где каждый элемент преставляет строку в виде карты ключ-наименование колонки,
     * ключ - наименование столбца, значение-значение колонки в данной строке.
     */
    private LinkedHashMap<String, List<LinkedHashMap<TableColumn, Object>>> tableData;
    /**
     * Карта хранения типов столбцов для считанных таблиц. Где ключ - наименование таблицы, Значение - карта столбцов и их типов.
     */
    private HashMap<String, List<TableColumn>> columnsData;


    public GuardianOfWisdom() {
        tableData = new LinkedHashMap<>();
        columnsData = new HashMap<>();
        tablesList = new ArrayList<>();
    }


    public List<TableColumn> getColumns(String tableName, Connection informConn) throws SQLException {
        readColumnData(tableName, informConn);
        return columnsData.get(tableName);
    }

    /**
     * Функция возвращает данные для представления в таблице
     */
    public List<LinkedHashMap<TableColumn, Object>> getTableData(String tableName,
                                                                 Connection conn,
                                                                 Connection inform) throws SQLException {
        readDataFromDataBase(inform, conn, tableName);
        return tableData.get(tableName);
    }

    /**
     * Функция возвращает список созданных таблиц
     */
//    public ArrayList<String> getTablesList(Connection conn)
//            throws SQLException {
//
//    }

    private void readColumnData(String tableName, Connection informConn) throws SQLException {

    }

    /**
     * Функция добавляет данные в локальное хранилище
     */
    private void readDataFromDataBase(Connection inform, Connection conn, String tableName)
            throws SQLException {


    }

    /**
     * Функция возвращает строку данных в виде объекта HasMap
     */
//    private LinkedHashMap<TableColumn, Object> readRow(ResultSet select, String tableName, Connection informConn)
//            throws SQLException {
//
//
//    }


    /**
     * Функция удаляет все упоминания о таблице
     */
    public void removeTable(ArrayList<String> tablesList) {
        tablesList.stream().forEach(table -> {
            this.tablesList.remove(table);
            this.tableData.remove(table);
        });
    }
}
