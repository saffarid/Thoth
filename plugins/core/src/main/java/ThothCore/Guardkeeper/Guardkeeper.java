package ThothCore.Guardkeeper;

import Database.ContentValues;
import Database.DataBaseManager;
import Database.TableColumn;
import Database.WhereValues;
import ThothCore.Guardkeeper.DataBaseDescription.*;
import ThothCore.Guardkeeper.DataBaseException.DatabaseExistsException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс считывает и создает новые пользовательские БД.
 * Считывание происходит из встроенной SQLite БД, которая не связана с пользовательскими БД.
 */
public class Guardkeeper {

    /**
     * Описание БД
     */
    private DataBaseInfo.DatabasesPath table;

    private File dbGuardkeeper;

    /**
     * Менеджер для работы с БД
     */
    private DataBaseManager dbManager;

    /**
     * Список пользовательских баз.
     */
    private List<File> databases;

    public Guardkeeper() throws SQLException, ClassNotFoundException {
        dbManager = DataBaseManager.getDbManager();
        dbGuardkeeper = DataBaseInfo.dbName;
        dbManager.getConnection(dbGuardkeeper);

        table = new DataBaseInfo.DatabasesPath();
        dbManager.createTable(table, this.dbGuardkeeper);

        readDataBases();
    }

    /**
     * Функция создает новую пользовательскую БД
     */
    public void createNewDatabase(File db, File template)
            throws SQLException,
                   ClassNotFoundException,
                   DatabaseExistsException {
        new CreatorUserDatabase(this.dbGuardkeeper).create(db, template, table);
        readDataBases();
    }

    public List<File> getDatabases() {
        return databases;
    }

    /**
     * Функция создает и возвращает объект SandBox
     */
    public void openSandBox(File db) throws NoSuchFileException {
        if(!db.exists()){
            throw new NoSuchFileException(db.getAbsolutePath());
        }

    }

    /**
     * Функция создает и возвращает объект Viewer
     */
    public void openViewer(File db) throws NoSuchFileException {
        if(!db.exists()){
            throw new NoSuchFileException(db.getAbsolutePath());
        }
    }

    /**
     * Функция считывает список пользовательских таблиц и преобразует их в списов объектов File.
     */
    private void readDataBases() throws SQLException {

        List<TableColumn> columns = table.getColumns()
                .stream()
                .filter(column -> !column.getName().equals("id"))
                .collect(Collectors.toList());
        List<LinkedHashMap<TableColumn, Object>> select = dbManager.select(table, columns, null, this.dbGuardkeeper);

        databases = select.stream().map(row -> {
            LinkedList<TableColumn> row1 = new LinkedList<>(row.keySet());
            return new File((String)row.get(row1.get(1)), (String)row.get(row1.get(0)));
        }).collect(Collectors.toList());
    }

    /**
     * Функция перемещает/переименовывает файл пользовательской БД
     */
    public void renameDatabase(File oldDb, File newDb) throws IOException, SQLException {
        if (!oldDb.equals(newDb)) {
            WhereValues whereValues = new WhereValues();
            whereValues.put(table.getColumns()
                            .stream()
                            .filter(column -> column.getName().equals(DataBaseInfo.DatabasesPath.COL_PATH))
                            .collect(Collectors.toList()).get(0),
                    oldDb.getParent());
            whereValues.put(table.getColumns()
                            .stream()
                            .filter(column -> column.getName().equals(DataBaseInfo.DatabasesPath.COL_NAME))
                            .collect(Collectors.toList()).get(0),
                    oldDb.getName());

            ContentValues contentValues = new ContentValues();
            contentValues.put(table.getColumns()
                            .stream()
                            .filter(column -> column.getName().equals(DataBaseInfo.DatabasesPath.COL_PATH))
                            .collect(Collectors.toList()).get(0),
                    newDb.getParent());
            contentValues.put(table.getColumns()
                            .stream()
                            .filter(column -> column.getName().equals(DataBaseInfo.DatabasesPath.COL_NAME))
                            .collect(Collectors.toList()).get(0),
                    newDb.getName());

            if (!newDb.getParentFile().exists()) newDb.getParentFile().mkdir();
            Files.move(oldDb.toPath(), newDb.toPath(), StandardCopyOption.REPLACE_EXISTING);
            dbManager.update(table, contentValues, whereValues, this.dbGuardkeeper);

            readDataBases();
        }
    }

    /**
     * Функция удаляет пользовательскую БД
     */
    public void removeDatabase(File db) throws SQLException, IOException, NoSuchFileException {

        WhereValues whereValues = new WhereValues();
        whereValues.put(table.getColumns()
                        .stream()
                        .filter(column -> column.getName().equals(DataBaseInfo.DatabasesPath.COL_PATH))
                        .collect(Collectors.toList()).get(0),
                db.getParent());
        whereValues.put(table.getColumns()
                        .stream()
                        .filter(column -> column.getName().equals(DataBaseInfo.DatabasesPath.COL_NAME))
                        .collect(Collectors.toList()).get(0),
                db.getName());

        Files.delete(db.toPath());
        dbManager.removedRow(table, whereValues, this.dbGuardkeeper);

        readDataBases();
    }


}
