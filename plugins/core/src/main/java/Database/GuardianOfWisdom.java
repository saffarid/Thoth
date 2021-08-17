package Database;

import DataBase.DataBaseInfo;
import DataBase.TableColumn;

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
    public ArrayList<String> getTablesList(Connection conn)
            throws SQLException {
        tablesList.clear();
        ResultSet select = DataBaseWrapper.select(DataBaseInfo.Information.Tables.NAME, new String[]{"*"}, null, conn);
        while (select.next()) {
            String string = select.getString(DataBaseInfo.Information.Tables.TABLE_NAME);
            tablesList.add(string);
        }

        Collections.sort(tablesList);
        return tablesList;
    }

    private void readColumnData(String tableName, Connection informConn) throws SQLException {

        if (!columnsData.containsKey(tableName)) {
            List<TableColumn> columns = new LinkedList<>();
            columnsData.put(tableName, columns);
        }

        List<TableColumn> columns = columnsData.get(tableName);
        columns.clear();
        WhereValues whereValues = new WhereValues();
        whereValues.put(DataBaseInfo.Information.Columns.TABLE_NAME, tableName);

        ResultSet column = DataBaseWrapper.select(DataBaseInfo.Information.Columns.NAME, new String[]{"*"}, whereValues, informConn);

        while (column.next()) {
            columns.add(new TableColumn(column.getString(DataBaseInfo.Information.Columns.COLUMN_NAME),
                    column.getString(DataBaseInfo.Information.Columns.COLUMN_TYPE),
                    column.getInt(DataBaseInfo.Information.Columns.IS_UNIQUE) == 1,
                    column.getInt(DataBaseInfo.Information.Columns.IS_PRIMARY_KEY) == 1,
                    column.getInt(DataBaseInfo.Information.Columns.IS_NOT_NULL) == 1));
        }
    }

    /**
     * Функция добавляет данные в локальное хранилище
     */
    private void readDataFromDataBase(Connection inform, Connection conn, String tableName)
            throws SQLException {

        if (!tableData.containsKey(tableName)) {
            List<LinkedHashMap<TableColumn, Object>> table = new LinkedList<>();
            tableData.put(tableName, table);
        }

        List<LinkedHashMap<TableColumn, Object>> table = tableData.get(tableName);
        table.clear();

        ResultSet select = DataBaseWrapper.select(tableName,
                new String[]{"*"},
                null,
                conn);

        while (select.next()) {
            table.add(readRow(select, tableName, inform));
        }
    }

    /**
     * Функция возвращает строку данных в виде объекта HasMap
     */
    private LinkedHashMap<TableColumn, Object> readRow(ResultSet select, String tableName, Connection informConn)
            throws SQLException {

        LinkedHashMap<TableColumn, Object> row = new LinkedHashMap<>();
        //Определяем сколько столбцов в таблице
        int columnCount = select.getMetaData().getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            //Определяем наименование столбца
            String columnName = select.getMetaData().getColumnName(i);
            //Формируем объект WhereValues для поиска всей информации о столбце в системной таблице
            for (TableColumn tableColumn : columnsData.get(tableName)) {
                if (tableColumn.getName().equals(columnName)) {
                    //Считываем и записываем данные
                    Object value = select.getObject(i);
                    row.put(tableColumn, value);
                }
            }
        }
        return row;
    }


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
