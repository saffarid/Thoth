package Database;

import DataBase.TableColumn;
import Model.Config;

import java.io.File;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Класс представляет обертку работы с базой данных
 *
 * @version 1.1
 */

public class DataBaseWrapper {

    private final static String TEMPLATE_LOG = "";
    private final static String PRE_URL_H2 = "jdbc:h2:";
    private final static String PRE_URL_POSTGRE = "jdbc:postgresql:";
    private final static String PRE_URL_MYSQL = "jdbc:mysql:";
    private final static String PRE_URL_ORACLE = "jdbc:oracle:thin:";
    private final static String PRE_URL_SQLITE = "jdbc:sqlite:";
    private final static String CLASS_NAME_H2 = "org.h2.Driver";
    private final static String CLASS_NAME_POSTGRE = "org.postgresql.Driver";
    private final static String CLASS_NAME_MYSQL = "com.mysql.jdbc.Driver";
    private final static String CLASS_NAME_ORACLE = "oracle.jdbc.driver.OracleDriver";
    private final static String CLASS_NAME_SQLITE = "org.sqlite.JDBC";

    private static Map<String, Connection> pullConn;
    private static Map<String, Integer> countConn;
    private static Logger LOG;

    static {
        LOG = Logger.getLogger(DataBaseWrapper.class.getName());
        pullConn = new HashMap<>();
        countConn = new HashMap<>();
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

        String template = "alter table `%1s` add %2s";
        execute(conn, String.format(template, tableName, tableColumn.toAdd()));
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
    public static void createTable(String tableName,
                                   List<TableColumn> columns,
                                   Connection conn) throws SQLException {
        String com = "create table if not exists `%1s` (%2s)";
        StringBuilder res = new StringBuilder("");
        columns.stream().forEach(column -> {
            res.append(column.toCreate());
        });
        res.deleteCharAt(res.lastIndexOf(","));
        execute(conn, String.format(com, tableName, res.toString()));
    }

    /**
     * Функция удаляет записи из таблицы.
     *
     * @param tableName имя таблицы
     * @param where     массив, хранящий значение по которому ищем строки для удаления.
     */
    public static void delete(Connection conn,
                              String tableName,
                              WhereValues where) throws SQLException {
        String templateComand = "delete from `%1s`";
        String comand = String.format(templateComand, tableName);
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
        Statement statement = conn.createStatement();
        statement.execute(comand);
    }

    /**
     * Функция осуществляет вставку данных в таблицу.
     *
     * @param tableName имя таблицы в которую добавляют данные
     */
    public static void insert(String tableName,
                              ContentValues content,
                              Connection conn) throws SQLException {
        String template = "insert into `%1s` %2s";
        execute(conn, String.format(template, tableName, content.toStringCreateTable()));
    }

    /**
     * Функция открывает соединение с базой данных.
     *
     * @param dbName имя базы данных.
     */
    public static Connection openConnetion(String dbName) throws ClassNotFoundException, SQLException {

        StringBuilder url = new StringBuilder(PRE_URL_SQLITE);
//            StringBuilder url = new StringBuilder(PRE_URL_MYSQL);
        File dir = new File(Config.getConfig().getDatabase().getUrlStorage());
        if (!dir.exists()) {
            dir.mkdir();
        }

        url.append(dir.getAbsolutePath());
        url.append(File.separator);
        url.append(dbName);
        Connection connection = null;


        Class.forName(CLASS_NAME_SQLITE);
//                Class.forName(CLASS_NAME_MYSQL);
        connection = DriverManager.getConnection(url.toString());
//                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/store", "root", "PnO030994");

        return connection;
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
     * */
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
     * @param tableName имя таблицы.
     * @param columns   массив содержащий список колонок для запроса.
     * @param where     строка, хранящая имя столбца в по которому определяем какую строку удаляем.
     * @return объект ResultSet
     */
    public static ResultSet select(String tableName,
                                   String[] columns,
                                   WhereValues where,
                                   Connection conn) throws SQLException {
        ResultSet result = null;

        String templateComand = "select %1s from `%2s`";
        String column = Arrays.toString(columns);
        column = column.substring(1, column.length() - 1);
        String comand = String.format(templateComand, column, tableName);

        if (where != null) {
            String templateWhere = "%1s where %2s";
            comand = String.format(templateWhere, comand, where.toString());
        }
        Statement statement = conn.createStatement();
        result = statement.executeQuery(comand);

        return result;
    }

    /**
     * Функция осуществляет обновление строки в таблице.
     *
     * @param tableName     имя таблицы в которую добавляют данные
     * @param contentValues класс-обёртка данных, для сохранения в базе данных. Ключ представляет наименование столбца, значение - значение столбца.
     * @param where         строка, хранящая имя столбца в по которому определяем в какой строке обновляем информацию
     */
    public static void update(String tableName,
                              ContentValues contentValues,
                              WhereValues where,
                              Connection conn) throws SQLException {
        //Заготовка SQL-команды
        String templateComand = "update `%1s` set %2s";
        String comand = String.format(templateComand, tableName, contentValues.toStringUpdate());
        if (where != null) {
            String templateWhere = "%1s where %2s";
            comand = String.format(templateWhere, comand, where.toString());
        }
        execute(conn, comand);
    }
}
