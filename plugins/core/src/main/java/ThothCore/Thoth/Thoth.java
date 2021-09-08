package ThothCore.Thoth;

import Database.DataBaseManager;
import ThothCore.EmptyDatabase.EmptyDatabase;

import java.io.File;
import java.sql.SQLException;

public class Thoth {

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

    private void readDataBase() throws SQLException, ClassNotFoundException {

        //Считываем список таблиц в БД
        dbManager.getDataTable(
                dbFile, db.getTable(EmptyDatabase.TablesList.NAME)
        );

    }
}
