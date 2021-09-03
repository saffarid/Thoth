package ThothCore.Guardkeeper;

import Database.ContentValues;
import Database.DataBaseManager;
import ThothCore.EmptyDatabase.EmptyDatabase;
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

    private File dbGuardkeeper;

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

    public CreatorUserDatabase(File db) {
        this.dbGuardkeeper = db;
    }

    /**
     * Функция создает пользовательскую БД
     */
    public void create(File db, File templateSQL, DataBaseInfo.DatabasesPath table) throws SQLException, ClassNotFoundException, DatabaseExistsException {

        if (!db.exists()) {
            //Создаем пользовательскую БД
            DataBaseManager dbManager = DataBaseManager.getDbManager();
            dbManager.createDatabase(db);

            /*
            * Заносим информацию о вновь созданной пользовательской БД в системную БД.
            * */
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

            dbManager.insert(table, contentValues, dbGuardkeeper);
            /*
             * Добавить обработку шаблона БД
             * */
            new EmptyDatabase().fill(db);
        } else {
            throw new DatabaseExistsException(db);
        }
    }

}
