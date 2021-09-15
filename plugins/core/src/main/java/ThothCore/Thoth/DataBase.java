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

    private File dbFile;

    public DataBase(EmptyDatabase template,
                    File dbFile) {
        super();
        this.dbFile = dbFile;
        copyTemplate(template);
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
     * Функция последовательно проходит по всем записям в таблицах tables_list и table_desc
     * и собирает все созданные таблицы в единый набор
     */
    public void readStructure() throws SQLException, ClassNotFoundException {

        DataBaseManager dbManager = DataBaseManager.getDbManager();

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
     * */
    public void readTableContent() throws SQLException, ClassNotFoundException {

        DataBaseManager dbManager = DataBaseManager.getDbManager();

        List<Table> tablesCollect = tables.stream()
                .filter(table -> !table.getType().equals(Table.SYSTEM_TABLE_NA))
                .collect(Collectors.toList());
        for (Table table : tablesCollect){

            //Считываем все записи из таблицы БД
            List<HashMap<String, Object>> dataTable = dbManager.getDataTable(
                    dbFile, table
            );
            List<ContentValues> data = table.getContentValues();
            //Проходим по считанным данным
            for (HashMap<String, Object> row : dataTable){
                LinkedList<String> colNames = new LinkedList<>(row.keySet());
                ContentValues contentValues = new ContentValues();
                //Проходим по всем столбцам
                for(String colName : colNames){
                    contentValues.put(
                            table.getTableCol(colName), row.get(colName)
                    );
                }
                data.add(contentValues);
            }
        }

    }

    public void addTable(Table table) {
        tables.add(table);
    }

}
