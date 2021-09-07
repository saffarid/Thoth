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
    private EmptyDatabase db;

    /**
     * Функциональная возможность
     * */
    private String funcAvaliable;

    private DataBaseManager dbManager;

    public Thoth(
            File dbUser) throws SQLException, ClassNotFoundException {
        dbFile = dbUser;
        dbManager = DataBaseManager.getDbManager();
        db = new EmptyDatabase();
        readDataBase();
    }

    private void readDataBase() throws SQLException, ClassNotFoundException {

        //Считываем список таблиц в БД
        dbManager.getTableList(
                dbFile, db.getTable(EmptyDatabase.TablesList.NAME)
        );

    }
}
