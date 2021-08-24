package ThothCore.Guardkeeper;

import Database.ContentValues;
import Database.DataBaseManager;
import ThothCore.Guardkeeper.DataBaseDescription.DataBaseInfo;
import ThothCore.Guardkeeper.DataBaseException.DatabaseExistsException;

import java.io.File;
import java.sql.SQLException;
import java.util.stream.Collectors;

/**
 * Класс-подрядчик для класса Guardkeeper.
 * Класс отвечает за создание новой БД или её редактирование (переименование, перемещение).
 * Перед созданием пользовательской БД, класс проверяет существование планируемой БД
 */
public class CreatorUserDatabase {

    /**
     * Наименование БД
     */
    private String name;

    /**
     * Расположение БД
     */
    private File path;

    /**
     * Шаблон БД
     */
    private File templateSQL;

    /**
     * Функция создает пользовательскую БД
     */
    public void create(File db, File templateSQL, DataBaseInfo.DatabasesPath table) throws SQLException, ClassNotFoundException, DatabaseExistsException {

        if (!db.exists()) {
            DataBaseManager.getDbManager().createDatabase(db);

            ContentValues contentValues = new ContentValues();
            contentValues.put(table.getColumns()
                    .stream()
                    .filter(column -> column.getName().equals(DataBaseInfo.DatabasesPath.COL_NAME))
                    .collect(Collectors.toList())
                    .get(0), db.getName());
            contentValues.put(table.getColumns()
                    .stream()
                    .filter(column -> column.getName().equals(DataBaseInfo.DatabasesPath.COL_PATH))
                    .collect(Collectors.toList())
                    .get(0), db.getParent());

            DataBaseManager.getDbManager().insert(table.getName(), contentValues);
            /*
             * Добавить обработку шаблона БД
             * */
        } else {
            throw new DatabaseExistsException(db);
        }
    }

}
