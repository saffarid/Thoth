package Database;

import java.util.List;

/**
 * Шаблон структуры системной БД
 * */
public abstract class DataBaseInfo {

    /**
     * Наименование БД
     * */
    String dbName;

    /**
     * Список таблиц в БД
     * */
    List<Table> tables;

}
