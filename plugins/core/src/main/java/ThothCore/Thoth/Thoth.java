package ThothCore.Thoth;

import Database.ContentValues;
import Database.DataBaseManager;
import Database.Table;
import ThothCore.EmptyDatabase.EmptyDatabase;

import java.io.File;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Thoth {

    private static Logger LOG;

    private final String logTemplate = "%1s: %2s";

    /**
     * Локальная копия БД
     * */
    private DataBase db;

    /**
     * Менеджер работы с БД
     * */
    private DataBaseManager dbManager;

    /**
     * Файл базы данных
     * */
    protected File dbFile;

    /**
     * Функциональная возможность
     * */
    private String funcAvaliable;

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
        db = new DataBase(template, dbFile);
        readDataBase();
    }

    /**
     * Функция создает таблицу в пользовательской БД
     * */
    public void createTable(Table table) throws SQLException, ClassNotFoundException {
        db.createTable(table);
    }

    /**
     * Функция создает триггер в пользовательской БД
     * */
    public void createTrigger(Table table){

    }

    /**
     * Функция редактирует таблицу в пользовательской БД
     * */
    public void editTable(Table table){

    }

    public List<ContentValues> getDataTypes(){
        return db.getTable(EmptyDatabase.DataTypes.NAME).getContentValues();
    }

    public List<Object> getTableTypes(){

        List<Object> collect = db.getTable(EmptyDatabase.TableTypes.NAME)
                .getContentValues()
                .stream()
                .map(contentValues1 -> contentValues1.get(
                        db.getTable(EmptyDatabase.TableTypes.NAME).getTableCol(EmptyDatabase.TableTypes.TABLE_TYPE))
                )
                .collect(Collectors.toList());
        return collect;
    }

    /**
     * Функция добавляет запись в таблицу пользовательской БД
     * */
    public void insertData(
            Table table,
            List<ContentValues> contentValues){

        for(ContentValues values : contentValues){
            try {
                //Проверка адекватности данных
                db.insertData(table, values);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private String getLogMes(String mes){
        return String.format(logTemplate, getClass().getSimpleName(), mes);
    }

    /**
     * Функция формирует список таблиц для выбора с какой таблицей пользователь может взаимодействовать.
     * Список формируется на основе текущего режима работы Thoth, а так же уровня пользовательского доступа.
     * @return список доступных таблиц.
     * */
    public List<Table> getTables(){
        //Реализация проверки доступа в зависимости от открытого модуля и формирование списка таблиц на основе этого
        //Функция должна возвращать только одну таблицу - tables list
        List<Table> res = db.getTables()
                .stream()
                .filter(table -> !table.getType().equals(Table.SYSTEM_TABLE_NA))
                .collect(Collectors.toList());

        return res;
    }

    private void readDataBase() throws SQLException, ClassNotFoundException {

        LOG.log(Level.INFO, getLogMes(
                new StringBuilder("Начинаю чтение пользовательской БД ")
                        .append(dbFile.getAbsolutePath())
                        .toString())
        );

        LOG.log(Level.INFO, getLogMes(
                "Формирование таблиц/колонок"
        ));

        db.readStructure();
        db.readTableContent();

        LOG.log(Level.INFO, getLogMes(
                new StringBuilder("Чтение пользовательской БД ")
                        .append(dbFile.getAbsolutePath())
                        .append(" завершено.")
                        .toString()
        ));
    }

    /**
     * Функция удаляет выбранную таблицу в пользовательской БД
     * */
    public void removeTable(LinkedList<Table> tables) throws SQLException, ClassNotFoundException {

        for(Table table : tables) {
            //Проверка адекватности
            db.removeTable(table);
        }

    }
}
