package ThothCore.Guardkeeper;

import Database.DataBaseManager;
import ThothCore.Guardkeeper.DataBaseException.DatabaseExistsException;

import java.io.File;
import java.sql.SQLException;

/**
 * Класс-подрядчик для класса Guardkeeper.
 * Класс отвечает за создание новой БД или её редактирование (переименование, перемещение).
 * Перед созданием пользовательской БД, класс проверяет существование планируемой БД
 * */
public class CreatorUserDatabase {

    /**
     * Наименование БД
     * */
    private String name;

    /**
     * Расположение БД
     * */
    private File path;

    /**
     * Шаблон БД
     * */
    private File templateSQL;

    /**
     * Функция создает пользовательскую БД
     * */
    public void create(File db, File templateSQL) throws SQLException, ClassNotFoundException, DatabaseExistsException {

        if(!db.exists()) {
            DataBaseManager.getDbManager().createDatabase(db);
            /*
             * Добавить обработку шаблона БД
             * */
        }else{
            throw new DatabaseExistsException(db);
        }
    }

}
