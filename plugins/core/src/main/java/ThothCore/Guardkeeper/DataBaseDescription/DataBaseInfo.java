package ThothCore.Guardkeeper.DataBaseDescription;

import Database.Table;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class DataBaseInfo {

    /**
     * Файл базы данных
     * */
    public static File dbName = new File("db/guardkeeper");

    /**
     * Класс описание таблицы хранения адресов баз
     * */
    public static class DatabasesPath extends Table {

        public static final String COL_ID = "id";
        public static final String COL_NAME = "name";
        public static final String COL_PATH = "path";
        public static final String TEXT = "varchar(255)";

        public DatabasesPath() {
            columns = new LinkedList<>();
            constrPKColumns = new LinkedList<>();
            constrNNColumns = new LinkedList<>();
            constrUColumns = new LinkedList<>();
            name = "DatabasePath";
            addColumn(new TableColumn(COL_ID, "integer", true, true, true));
            addColumn(new TableColumn(COL_NAME, TEXT, false, false, true));
            addColumn(new TableColumn(COL_PATH, TEXT, false, false, true));
        }
    }



}
