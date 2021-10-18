package ThothCore.ThothLite;

import Database.DataBaseManager;
import Database.Table;
import ThothCore.ThothLite.DBData.DBData;
import ThothCore.ThothLite.DBLiteStructure.DBLiteStructure;

import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseLite {

    private static Logger LOG;

    private final String URL_DB = "db/storage.tho";
    private final File dbFile;

    private DBLiteStructure structure;
    private DataBaseManager dbManager;

    static {
        LOG = Logger.getLogger(DBLiteStructure.class.getName());
    }

    public DataBaseLite() throws SQLException, ClassNotFoundException {
        dbManager = DataBaseManager.getDbManager();
        this.structure = new DBLiteStructure();
        dbFile = new File(URL_DB);
        if(!this.dbFile.exists()){
            firstInit();
        }else{
            readDataBase();
        }
    }

    /**
     * Первичная инициализация БД
     * */
    private void firstInit() throws SQLException, ClassNotFoundException {
        LOG.log(Level.INFO, "Создаю БД");
        dbManager.createDatabase(this.dbFile);

        LOG.log(Level.INFO, "Добавляю таблицы в БД");
        for(Table table : structure.getTables()){
            dbManager.createTable(table, this.dbFile);
        }
        LOG.log(Level.INFO, "Создание структуры успешно пройдено");
    }

    public List<Table> getTables(){
        return structure.getTables();
    }

    /**
     * Чтение содержимого таблиц БД
     * */
    public void readDataBase() throws SQLException, ClassNotFoundException {
        for(Table table : structure.getTables()){
            readTable(
                    table,
                    dbManager.getDataTable(dbFile, table, false)
            );
        }
    }

    /**
     * Чтение содержимого таблицы
     * */
    public void readTable(Table table, List<HashMap<String, Object>> data){
        try {
            DBData.getInstance().getTableReadable(table.getName()).readTable(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }



}
