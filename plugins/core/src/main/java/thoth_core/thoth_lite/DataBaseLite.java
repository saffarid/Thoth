package thoth_core.thoth_lite;

import database.Column.Autoincrement;
import database.Column.PrimaryKey;
import database.Column.TableColumn;
import database.ContentValues;
import database.DataBaseManager;
import database.Table;
import database.WhereValues;
import thoth_core.thoth_lite.db_data.DBData;
import thoth_core.thoth_lite.db_lite_structure.full_structure.DBLiteStructure;
import thoth_core.thoth_lite.db_lite_structure.full_structure.StructureDescription;
import thoth_core.thoth_lite.db_lite_structure.full_structure.StructureDescription.TableTypes;
import thoth_core.thoth_lite.exceptions.NotContainsException;

import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class DataBaseLite {

    private static Logger LOG;

    private final String URL_DB = "db/storage.tho";
    private final File dbFile;

    private DBLiteStructure structure;
    private DataBaseManager dbManager;

    static {
        LOG = Logger.getLogger(DBLiteStructure.class.getName());
    }

    public DataBaseLite()
            throws SQLException, ClassNotFoundException {
        dbManager = DataBaseManager.getDbManager();
        this.structure = new DBLiteStructure();
        dbFile = new File(URL_DB);
        if (!this.dbFile.exists()) {
            firstInit();
        } else {
            readDataBase();
        }
    }

    /**
     * Первичная инициализация БД
     */
    private void firstInit()
            throws SQLException, ClassNotFoundException {
        LOG.log(Level.INFO, "Создаю БД");
        dbManager.createDatabase(this.dbFile);

        LOG.log(Level.INFO, "Добавляю таблицы в БД");
        for (Table table : structure.getTables()) {
            dbManager.createTable(table, this.dbFile);

            if(!table.getContentValues().isEmpty()){
                for(ContentValues values : table.getContentValues())
                dbManager.insert(
                        table,
                        values,
                        dbFile
                );
            }
        }
        LOG.log(Level.INFO, "Создание структуры успешно пройдено");
    }

    /**
     * Инициализация начала транзакции
     */
    public void beginTransaction()
            throws SQLException {
        dbManager.beginTransaction(dbFile);
    }

    public void commitTransaction()
            throws SQLException {
        dbManager.commitTransaction(dbFile);
    }

    private ContentValues convertToContentValues(String tableName, HashMap<String, Object> data) {
        ContentValues contentValues = new ContentValues();

        for (String columnName : data.keySet()) {

            TableColumn columnByName = structure.getTable(tableName).getColumnByName(columnName);
            //Исключает колонки с автоинкрементируемым индексом
            if (!(columnByName instanceof Autoincrement)) {
                contentValues.put(columnByName, data.get(columnName));
            }
        }

        return contentValues;
    }

    private WhereValues convertToWhereValues(String tableName, HashMap<String, Object> data) {
        WhereValues whereValues = new WhereValues();

        PrimaryKey primaryKeyColumn = structure.getTable(tableName).getPrimaryKeyColumn();
        whereValues.put(
                primaryKeyColumn, data.get(primaryKeyColumn.getName())
        );
        return whereValues;
    }

    public List<Table> getTables() {
        return structure.getTables();
    }

    public List<Table> getTablesByType(TableTypes type) {
        return structure.getTables().stream()
                .filter(table -> table.getType().equals(type))
                .collect(Collectors.toList());
    }

    public void insert(String tableName, List<HashMap<String, Object>> datas)
            throws SQLException {
        for (HashMap<String, Object> data : datas) {
            dbManager.insert(
                    structure.getTable(tableName),
                    convertToContentValues(tableName, data),
                    dbFile
            );
        }
    }

    /**
     * Чтение содержимого таблиц БД
     */
    public void readDataBase()
            throws SQLException, ClassNotFoundException {
        List<Table> collect = structure.getTables()
                .stream()
                .filter(table -> !table.getType().equals(TableTypes.SYSTEM_TABLE.getType()))
                .collect(Collectors.toList());
        for (Table table : collect) {
            readTable(
                    table,
                    dbManager.getDataTable(dbFile, table, false)
            );
        }
    }

    /**
     * Чтение содержимого таблицы
     */
    public void readTable(String table)
            throws SQLException, ClassNotFoundException {
        Table readingTable = structure.getTable(table);

        CompletableFuture.supplyAsync(() -> {
            List<HashMap<String, Object>> dataTable = new LinkedList<>();
            try {
                dataTable = dbManager.getDataTable(dbFile, readingTable, false);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return dataTable;
        }).thenAccept(hashMaps -> {
            readTable(readingTable, hashMaps);
        });
    }

    /**
     * Чтение содержимого таблицы
     */
    private void readTable(Table table, List<HashMap<String, Object>> data) {
        try {
            TableTypes tableType = TableTypes.valueOf(table.getType());
            DBData.getInstance()
                    .getTableReadable(table.getName())
                    .readTable(tableType, data);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NotContainsException e) {
            e.printStackTrace();
        }
    }

    /**
     * Функция удаляет записи из таблицы.
     *
     * @param tableName имя таблицы.
     * @param datas     удаляемые данные.
     */
    public void remove(String tableName, List<HashMap<String, Object>> datas)
            throws SQLException {
        for (HashMap<String, Object> data : datas) {
            dbManager.removedRow(
                    structure.getTable(tableName)
                    , convertToWhereValues(tableName, data)
                    , dbFile
            );
        }
    }

    public void update(String tableName, List<HashMap<String, Object>> datas)
            throws SQLException {
        for (HashMap<String, Object> data : datas) {
            dbManager.update(
                    structure.getTable(tableName)
                    , convertToContentValues(tableName, data)
                    , convertToWhereValues(tableName, data)
                    , dbFile
            );
        }

    }

}
