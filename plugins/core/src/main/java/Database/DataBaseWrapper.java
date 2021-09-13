package Database;

import java.io.File;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/**
 * Класс представляет обертку работы с базой данных
 *
 * @version 1.1
 */

public class DataBaseWrapper {

    private final static String PRE_URL_SQLITE = "jdbc:sqlite:";
    private final static String CLASS_NAME_SQLITE = "org.sqlite.JDBC";
    private final static boolean isExecute = true;
    private static Logger LOG;

    static {
        LOG = Logger.getLogger(DataBaseWrapper.class.getName());
    }

    /**
     * Функция добавляет новый столбец в таблицу
     *
     * @param tableName   имя таблицы
     * @param tableColumn Объект описание столбца
     * @param conn        Объект подключения
     */
    public static void addColumn(String tableName,
                                 TableColumn tableColumn,
                                 Connection conn) throws SQLException {

//        String template = "alter table `%1s` add %2s";
//        execute(conn, String.format(template, tableName, tableColumn.toAdd()));
    }

    /**
     * Функция начинает транзакцию
     */
    public static void beginTransaction(Connection conn,
                                        String transactionName) throws SQLException {
        String comand = String.format("begin transaction%1s", ((transactionName != null) ? (" " + transactionName) : ("")));
        execute(conn, comand);
    }

    /**
     * Функция удаляет все записи из таблицы.
     *
     * @param tableName имя таблицы
     * @param conn      Соединение с БД
     */
    public static void clearTable(Connection conn,
                                  String tableName) throws SQLException {
        String comand = "delete from `%1s`";
        execute(conn, String.format(comand, tableName));
    }

    /**
     * Функция закрывает подключение к БД
     */
    public static void closeConnection(Connection conn,
                                       String dbName) {
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Функция закрепляет транзакцию
     */
    public static void commitTransaction(Connection conn,
                                         String transactionName) throws SQLException {
        String comand = String.format("commit%1s", ((transactionName != null) ? (" transaction " + transactionName) : ("")));
        execute(conn, comand);
    }

    /**
     * Функция создаёт таблицу в базе данных
     *
     * @param tableName имя таблицы
     * @param columns   объект-описание таблицы
     * @param conn      Название базы данных
     */
    public static void createTable(Table tableName,
                                   List<TableColumn> columns,
                                   Connection conn) throws SQLException {
        String com = "create table if not exists `%1s` (\n\t%2s\n\t)";
        StringBuilder res = new StringBuilder("");
        columns.stream().forEach(column -> {
            res.append(column.toCreate());
            if (columns.indexOf(column) != columns.size() - 1) {
                res.append(", \n\t");
            }
        });
        res.append(" ");
//        if(tableName.hasConstPK()) {
//            res.append(", ");
//            res.append(tableName.getConstrPK());
//        }
        if (tableName.hasConstrUniq()) {
            res.append(", \n\t");
            res.append(tableName.getConstrUniq());
        }
        if (tableName.hasConstrFK()) {
            res.append(", \n\t");
            res.append(tableName.getConstrFK());
        }
        execute(conn, String.format(com, tableName.getName(), res.toString()));
    }

    /**
     * Функция удаляет записи из таблицы.
     *
     * @param table таблица.
     * @param where массив, хранящий значение по которому ищем строки для удаления.
     */
    public static void delete(Connection conn,
                              Table table,
                              WhereValues where) throws SQLException {
        String templateComand = "delete from `%1s`";
        String comand = String.format(templateComand, table.getName());
        if (where != null) {
            String templateWhere = "%1s where %2s";
            comand = String.format(templateWhere, comand, where.toString());
        }
        execute(conn, comand);
    }

    /**
     * Функция удаляет столбец из таблицы.
     *
     * @param tableName   имя таблицы
     * @param tableColumn Объект описание колонки
     * @param conn        Соединение с БД
     */
    public static void deleteColumn(String tableName,
                                    TableColumn tableColumn,
                                    Connection conn) throws SQLException {
        String template = "alter table `%1s` drop `%2s`";
        execute(conn, String.format(template, tableName, tableColumn.getName()));
    }

    /**
     * Функция удаляет базу данных.
     *
     * @param dbName имя базы данных.
     */
    public static void deleteDataBase(Connection conn,
                                      String dbName) throws SQLException {
        String comand = "drop database " + dbName;
        execute(conn, comand);
    }

    /**
     * Функция удаляет таблицу из базы данных
     *
     * @param tableName имя таблицы
     * @param conn      Соединение с БД
     */
    public static void dropTable(String tableName, Connection conn) throws SQLException {
        String comand = "drop table `%1s`";
        execute(conn, String.format(comand, tableName));
    }

    /**
     * Функция выполняет команду
     */
    private static void execute(Connection conn,
                                String comand) throws SQLException {
        LOG.log(Level.INFO, comand);
        if (isExecute) {
            Statement statement = conn.createStatement();
            statement.execute(comand);
        }
    }

    /**
     * Функция открывает соединение с базой данных.
     *
     * @param db имя базы данных.
     */
    public static Connection openConnetion(File db) throws ClassNotFoundException, SQLException {

        StringBuilder url = new StringBuilder(PRE_URL_SQLITE);

        if (!db.getParentFile().exists()) db.getParentFile().mkdir();

        url.append(db.getAbsolutePath());
        Connection connection = null;

        Class.forName(CLASS_NAME_SQLITE);
        connection = DriverManager.getConnection(url.toString());

        return connection;
    }

    /**
     * Функция осуществляет вставку данных в таблицу.
     *
     * @param tableName имя таблицы в которую добавляют данные
     */
    public static void insert(Table tableName,
                              ContentValues content,
                              Connection conn) throws SQLException {
        String template = "insert into `%1s` %2s";
        execute(conn, String.format(template, tableName.getName(), content.toStringInsert()));
    }

    /**
     * Функция переименовывания таблицы в баще данных
     *
     * @param oldName старое наименование таблицы
     * @param newName новое название таблицы
     * @param conn    Соединение с БД
     */
    public static void renameTable(String oldName,
                                   String newName,
                                   Connection conn) throws SQLException {
        String comand = "alter table %1s rename to %2s";
        execute(conn, String.format(comand, oldName, newName));
    }

    /**
     * Функция переименовывает колонку таблицы
     */
    public static void renameColumn(String tableName,
                                    TableColumn oldTableColumn,
                                    TableColumn newTableColumn,
                                    Connection conn) throws SQLException {
        String template = "alter table `%1s` rename column '%2s' to %3s";
        execute(conn, String.format(template, tableName, oldTableColumn.getName().trim(), "'" + newTableColumn.getName().trim()) + "'");
    }

    /**
     * Функция откатывает транзакцию к предыдущей точке сохранения
     */
    public static void rollbackSavepoint(Connection conn,
                                         String savepointName) throws SQLException {
        String comand = String.format("rollback%1s", ((savepointName != null) ? (" to savepoint " + savepointName) : ("")));
        LOG.log(Level.INFO, comand);
        execute(conn, comand);
    }

    /**
     * Функция откатывает транзакцию к предыдущей транзакции
     */
    public static void rollbackTransaction(Connection conn,
                                           String transactionName) throws SQLException {
        String comand = String.format("rollback%1s", ((transactionName != null) ? (" transaction " + transactionName) : ("")));
        execute(conn, comand);
    }

    /**
     * Функция создает точку сохранения транзакции
     */
    public static void savepointTransaction(Connection conn,
                                            String savepointName) throws SQLException {
        String comand = String.format("savepoint%1s", ((savepointName == null) ? ("") : (" " + savepointName)));
        LOG.log(Level.INFO, comand);
        execute(conn, comand);
    }

    /**
     * Функция осуществляет чтение из базы данных
     *
     * @param table объект-описание таблицы.
     * @param where строка, хранящая имя столбца в по которому определяем какую строку удаляем.
     * @return объект ResultSet
     */
    public static ResultSet select(Table table,
                                   List<TableColumn> columns,
                                   WhereValues where,
                                   Connection conn) throws SQLException {
        ResultSet result = null;

        String templateComand = "select %1s \nfrom `%2s`";
        String templateFullName = "\"%1s\".`%2s`";
        String templateAs = "`%1s` as `%2s`";
        String templateName = "`%1s`";
        //Определяем столбцы для запроса в БД
        if (columns == null) columns = table.getColumns();

        StringBuilder column = new StringBuilder("");
        //Проходим по всем колонкам для проверки
        for (TableColumn col : columns) {
            TableColumn fkTableCol = col.getFKTableCol();
            if (fkTableCol != null) {
                StringBuilder subRequest = new StringBuilder("");
                /*
                 * Наименование таблицы формируется следующим образом.
                 * Если наименование текущей таблицы совпадает добавляем к наименованию "sub_"*/
                String selectTemplate = "select %1s from %2s where %3s";
                Table fkTableParent = new Table().copy(fkTableCol.getTableParent());
                String pseudoName;
                //Переменная для определения внешнего ключа на саму себя
                boolean foreighnSelf = fkTableParent.getName().equals(table.getName());
                if (foreighnSelf) {
                    pseudoName = "sub_".concat(fkTableParent.getName());
                }else{
                    pseudoName = fkTableParent.getName();
                }
                TableColumn fkTableColId = fkTableParent.getTableCol(Table.ID);
                //Ветка выполняется при наличии ссылки на внешнюю таблицу
                if (fkTableCol.getName().equals(Table.ID)) {
                    //Внешний ключ ссылается на колонку идентификатор внешней таблицы
                    /*Формируем подзапрос следующего вида
                     * select "выбираем все колонки внешней таблицы кроме ID" from fkTable where fk.id = fk_id
                     * Подзапросы должны формироваться для каждой колонки ОТДЕЛЬНО!!!*/
                    //Определяем колонки для вывода информации
                    List<TableColumn> collect = fkTableParent.getColumns()
                            .stream()
                            .filter(column1 -> !column1.getName().equals(Table.ID))
                            .collect(Collectors.toList());
                    //Определяем колонку идентификатор таблицы вывода

                    //Формируем подзапрос для каждого столбца
                    for (TableColumn tableColumnFkTable : collect) {
                        subRequest.append("(");
                        subRequest.append(
                                String.format(
                                        selectTemplate,
                                        tableColumnFkTable.getNameForSQL(),
                                        (!foreighnSelf)?(String.format(templateName, pseudoName)):(String.format(templateAs, fkTableParent.getName(), pseudoName)),
                                        String.format(templateFullName, pseudoName, fkTableCol.getName()) + " = " + col.getFullNameSQL()
                                )
                        );
                        subRequest.append(") as ");
                        subRequest.append(tableColumnFkTable.getName());
                        if (collect.indexOf(tableColumnFkTable) != collect.size() - 1) {
                            subRequest.append(", \n");
                        }
                    }
                } else {
                    //Внешний ключ ссылается на кастомную колонку внешней таблицы
                    /*Формируем подзапрос следующего вида
                     * select fkTableCol from fkTable where fk.id = fk_id*/
                    subRequest.append("(");
                    subRequest.append(String.format(
                            selectTemplate,
                            fkTableCol.getNameForSQL(),
                            (!foreighnSelf)?(String.format(templateName, pseudoName)):(String.format(templateAs, fkTableParent.getName(), pseudoName)),
                            String.format(templateFullName, pseudoName, fkTableColId.getName()) + " = " + col.getFullNameSQL()
                    ));
                    if (col.getName().startsWith("fk_")) {
                        subRequest.append(
                                ") as " + "fk_".concat(fkTableCol.getName())
                        );
                    } else {
                        subRequest.append(
                                ") as " + fkTableCol.getName()
                        );
                    }

                }
                column.append(subRequest);
            } else {
                //Колонка не содержит внешнего ключа
                column.append(col.getName());
            }
            if (columns.indexOf(col) != columns.size() - 1) {
                column.append(", \n");
            }
        }

        String comand = String.format(templateComand, column.toString(), table.getName());

        if (where != null) {
            String templateWhere = "%1s where %2s";
            comand = String.format(templateWhere, comand, where.toString());
        }
        LOG.log(Level.INFO, comand);
        Statement statement = conn.createStatement();
        result = statement.executeQuery(comand);

        return result;
    }

    /**
     * Функция осуществляет обновление строки в таблице.
     *
     * @param table         имя таблицы в которую добавляют данные
     * @param contentValues класс-обёртка данных, для сохранения в базе данных. Ключ представляет наименование столбца, значение - значение столбца.
     * @param where         строка, хранящая имя столбца в по которому определяем в какой строке обновляем информацию
     */
    public static void update(Table table,
                              ContentValues contentValues,
                              WhereValues where,
                              Connection conn) throws SQLException {
        //Заготовка SQL-команды
        String templateComand = "update `%1s` set %2s";
        String comand = String.format(templateComand, table.getName(), contentValues.toStringUpdate());
        if (where != null) {
            String templateWhere = "%1s where %2s";
            comand = String.format(templateWhere, comand, where.toString());
        }
        execute(conn, comand);
    }
}
