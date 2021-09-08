package ThothCore.Thoth;

import Database.DataBaseManager;
import ThothCore.EmptyDatabase.EmptyDatabase;

import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Thoth {

    private static Logger LOG;

    private final String logTemplate = "%1s: %2s";

    /**
     * Файл базы данных
     * */
    protected File dbFile;

    /**
     * Локальная копия БД
     * */
    private DataBase db;

    /**
     * Функциональная возможность
     * */
    private String funcAvaliable;

    private DataBaseManager dbManager;

    static {
        LOG = Logger.getLogger(Thoth.class.getName());
    }

    public Thoth(
            File dbUser) throws SQLException, ClassNotFoundException {
        init(dbUser, new EmptyDatabase());
    }

    public Thoth(
            File dbUser,
            EmptyDatabase template) throws SQLException, ClassNotFoundException {
        init(dbUser, template);
    }

    private void init(
            File dbUser,
            EmptyDatabase template) throws SQLException, ClassNotFoundException {
        dbFile = dbUser;
        dbManager = DataBaseManager.getDbManager();
        db = new DataBase(template);
        readDataBase();
    }

    private String getLogMes(String mes){
        return String.format(logTemplate, getClass().getSimpleName(), mes);
    }

    private void readDataBase() throws SQLException, ClassNotFoundException {

        LOG.log(Level.INFO, getLogMes(
                new StringBuilder("Начинаю чтение пользовательской БД ")
                        .append(dbFile.getAbsolutePath())
                        .toString())
        );

        //Считываем список таблиц в БД
        List<HashMap<String, Object>> dataTable1 = dbManager.getDataTable(
                dbFile, db.getTable(EmptyDatabase.TablesList.NAME)
        );

        //Считываем описание таблиц в БД
        List<HashMap<String, Object>> dataTable = dbManager.getDataTable(
                dbFile, db.getTable(EmptyDatabase.TableDesc.NAME)
        );

        LOG.log(Level.INFO, getLogMes(
                "Формирование таблиц/колонок"
        ));

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
//        for (HashMap<String, Object> row :)

        LOG.log(Level.INFO, getLogMes(
                new StringBuilder("Чтение пользовательской БД ")
                .append(dbFile.getAbsolutePath())
                .append(" завершено.")
                .toString()
        ));
    }
}
