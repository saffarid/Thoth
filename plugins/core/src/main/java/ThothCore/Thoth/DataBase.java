package ThothCore.Thoth;

import Database.*;
import ThothCore.EmptyDatabase.EmptyDatabase;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class DataBase extends DataBaseSQL {

    private DataBaseManager dbManager;

    private File dbFile;

    public DataBase(EmptyDatabase template,
                    File dbFile) throws SQLException, ClassNotFoundException {
        super();
        this.dbFile = dbFile;
        copyTemplate(template);
        dbManager = DataBaseManager.getDbManager();
    }

    /**
     * Копирование структуры шаблона в текущую БД
     */
    public void copyTemplate(EmptyDatabase template) {
        tables.add(new Table().copy(template.getTable(EmptyDatabase.TablesList.NAME)));
        tables.add(new Table().copy(template.getTable(EmptyDatabase.TableDesc.NAME)));
        tables.add(new Table().copy(template.getTable(EmptyDatabase.TableTypes.NAME)));
        tables.add(new Table().copy(template.getTable(EmptyDatabase.DataTypes.NAME)));
    }

    /**
     * Функция отвечает за создание новой таблицы
     */
    public void createTable(Table table) throws SQLException {
        //Сменить способ закрепления транзакции
        //Если на каком-то этапе произошла ошибка, вся транзакция должна отмениться
        dbManager.createTable(table, dbFile);
        insertIntoSysTable(table);
        //Иначе закрепляем транзакцию и спокойно работаем дальше
    }

    public void insertData(Table table, ContentValues contentValues) throws SQLException {
        dbManager.insert(
                table, contentValues, dbFile
        );
    }

    /**
     * Функция формирует запросы на вставку информации в системные таблицы: Tables list, Table desc,
     */
    private void insertIntoSysTable(Table table) throws SQLException {
        insertIntoTablesList(table);
        insertIntoTableDesc(table);
    }

    /**
     * Функция формирцует запрос на вставку информации о таблицу в Table desc
     */
    private void insertIntoTableDesc(Table table) throws SQLException {
        Table tableDesc = getTable(EmptyDatabase.TableDesc.NAME);

        /*
         *  Проходим по всем колонкам таблицы, формируем объект contentValues
         * */
        for (TableColumn column : table.getColumns()) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(tableDesc.getTableCol(EmptyDatabase.TableDesc.TABLE_ID), column.getTableParent().getName());
            contentValues.put(tableDesc.getTableCol(EmptyDatabase.TableDesc.COL_NAME), column.getName());
            contentValues.put(tableDesc.getTableCol(EmptyDatabase.TableDesc.TYPE_ID), column.getType());
            contentValues.put(tableDesc.getTableCol(EmptyDatabase.TableDesc.PK_CONSTR), (column.isPrimaryKey()) ? (1) : (0));
            contentValues.put(tableDesc.getTableCol(EmptyDatabase.TableDesc.UNIQ_CONSTR), (column.isUnique()) ? (1) : (0));
            contentValues.put(tableDesc.getTableCol(EmptyDatabase.TableDesc.NOTNULL_CONSTR), (column.isNotNull()) ? (1) : (0));
            contentValues.put(tableDesc.getTableCol(EmptyDatabase.TableDesc.FK_TABLE_ID), (column.getFKTableCol() != null) ? (column.getFKTableCol().getTableParent().getName()) : (null));
            contentValues.put(tableDesc.getTableCol(EmptyDatabase.TableDesc.FK_COLUMN_ID), (column.getFKTableCol() != null) ? (column.getFKTableCol().getName()) : (null));

            dbManager.insert(tableDesc, contentValues, dbFile);
        }
    }

    /**
     * Функция формирует запрос на вставку информации о таблице в Tables list
     */
    private void insertIntoTablesList(Table table) throws SQLException {
        Table tablesList = getTable(EmptyDatabase.TablesList.NAME);
        ContentValues contentValues = new ContentValues();

        contentValues.put(
                tablesList.getTableCol(EmptyDatabase.TablesList.TABLE_NAME), table.getName()
        );

        contentValues.put(
                tablesList.getTableCol(EmptyDatabase.TablesList.TABLE_TYPE_ID), table.getType()
        );

        dbManager.insert(tablesList, contentValues, dbFile);
    }

    /**
     * Функция последовательно проходит по всем записям в таблицах tables_list и table_desc
     * и собирает все созданные таблицы в единый набор
     */
    public void readStructure() throws SQLException, ClassNotFoundException {

        //Считываем список таблиц в БД
        List<HashMap<String, Object>> tablesList = dbManager.getDataTable(
                dbFile, getTable(EmptyDatabase.TablesList.NAME)
        );

        //Считываем описание таблиц в БД
        List<HashMap<String, Object>> tableDesc = dbManager.getDataTable(
                dbFile, getTable(EmptyDatabase.TableDesc.NAME)
        );

        /*
         * Процесс формирования колонок заключается в следующем.
         * Циклом проходим по считанному списку таблиц.
         *   Если таблица с текущим наименованием существует ,
         *   иначе создаём новый объект таблицы.
         *   Фильтруем содержимое считанной "Table description" по текущей таблице.
         *       Циклом проходим по колонкам текущей таблицы.
         *           Если колонка с таким наименованием существует,
         *           то находим её и устанавливаем считанные значения,
         *           иначе создаем объект колонки и устанавливаем считанные значения
         * */
        for (HashMap<String, Object> row : tablesList) {

            //Определяем текущую таблицу
            String name = (String) row.get(EmptyDatabase.TablesList.TABLE_NAME);
            String type = (String) row.get(EmptyDatabase.TableTypes.TABLE_TYPE);
            Table table = getTable(name);
            if (table == null) {
                table = new Table();
                table.setName(name);
                table.setType(type);
                getTables().add(table);
            }
            //Проверяем тип таблицы
            /*----------------------------------------------------------*/
            //Определяем список столбцов, соответсвующих текущей таблице
            List<HashMap<String, Object>> columns = tableDesc.stream()
                    .filter(tab -> ((String) tab.get(EmptyDatabase.TablesList.TABLE_NAME)).equals(name))
                    .collect(Collectors.toList());
            //Проходим по колонкам
            for (HashMap<String, Object> tab : columns) {
                String columnName = (String) tab.get(EmptyDatabase.TableDesc.COL_NAME);
                Boolean isPrimaryKey = (((Integer) tab.get(EmptyDatabase.TableDesc.PK_CONSTR)) == 1);
                Boolean isUniq = (((Integer) tab.get(EmptyDatabase.TableDesc.UNIQ_CONSTR)) == 1);
                Boolean isNotNull = (((Integer) tab.get(EmptyDatabase.TableDesc.NOTNULL_CONSTR)) == 1);
                String fkTable = (String) tab.get(EmptyDatabase.TableDesc.FK_TABLE_ID);
                String fkColumn = (String) tab.get(EmptyDatabase.TableDesc.FK_COLUMN_ID);
                TableColumn tableCol = table.getTableCol(columnName);
                //Перед проверкой на существование считать всю информацию с строки
                if (tableCol == null) {
                    tableCol = new TableColumn();
                    table.addColumn(tableCol);
                }
                tableCol.setName(columnName);
                tableCol.setPrimaryKey(isPrimaryKey);
                tableCol.setUnique(isUniq);
                tableCol.setNotNull(isNotNull);
                tableCol.setTableParent(table);
            }

        }

    }

    /**
     * Функция проходит по всем таблицам
     */
    public void readTableContent() throws SQLException, ClassNotFoundException {

        List<Table> tablesCollect = tables.stream()
                .filter(table -> !table.getType().equals(Table.SYSTEM_TABLE_NA))
                .collect(Collectors.toList());
        for (Table table : tablesCollect) {
            readTable(table);
        }

    }

    private void readTable(Table table) throws SQLException, ClassNotFoundException {
        table.getContentValues().clear();
        //Считываем все записи из таблицы БД
        List<HashMap<String, Object>> dataTable = dbManager.getDataTable(
                dbFile, table
        );
        List<ContentValues> data = table.getContentValues();
        //Проходим по считанным данным
        for (HashMap<String, Object> row : dataTable) {
            LinkedList<String> colNames = new LinkedList<>(row.keySet());
            ContentValues contentValues = new ContentValues();
            //Проходим по всем столбцам
            for (String colName : colNames) {
                contentValues.put(
                        table.getTableCol(colName), row.get(colName)
                );
            }
            data.add(contentValues);
        }
    }

    /**
     * Функция удаляет записи из пользовательской БД
     */
    public void removeRows(
            Table table,
            List<ContentValues> removedRows
    ) throws SQLException {

        for (ContentValues row : removedRows) {
            WhereValues whereValues = new WhereValues();
            TableColumn tableColId = table.getTableCol(Table.ID);
            whereValues.put(tableColId, row.get(tableColId));
            dbManager.removedRow(table, whereValues, dbFile);
        }

    }

    /**
     * Функция удаляет таблицу из БД
     */
    public void removeTable(Table table) throws SQLException, ClassNotFoundException {
        dbManager.removeTables(table, dbFile);

        //Формируем запрос на удаление из table_desc
        WhereValues whereTableDesc = new WhereValues();
        Table tableDesc = getTable(EmptyDatabase.TableDesc.NAME);
        whereTableDesc.put(
                tableDesc.getTableCol(EmptyDatabase.TableDesc.TABLE_ID),
                table.getName()
        );
        dbManager.removedRow(tableDesc, whereTableDesc, dbFile);

        //Формируем запрос на удаление из tables_list
        Table tablesList = getTable(EmptyDatabase.TablesList.NAME);
        WhereValues whereTablesList = new WhereValues();
        whereTablesList.put(
                tablesList.getTableCol(EmptyDatabase.TablesList.TABLE_NAME),
                table.getName()
        );
        dbManager.removedRow(tablesList, whereTablesList, dbFile);

        tables.remove(table);
    }

    public void addTable(Table table) {
        tables.add(table);
    }

}
