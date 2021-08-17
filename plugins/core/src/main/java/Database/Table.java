package Database;

import java.util.List;
import java.util.Map;

public abstract class Table {

    /**
     * Наименование таблицы
     * */
    protected String name;

    /**
     * Список столбцов
     * */
    protected List<TableColumn> columns;

    /**
     * Список столбцов для ограничения PRIMARY KEY
     * */
    protected List<String> constrPKColumns;

    /**
     * Список столбцов для ограничения UNIQUE
     * */
    protected List<String> constrUColumns;

    /**
     * Список столбцов для ограничения NOT NULL
     * */
    protected List<String> constrNNColumns;

    /**
     * Список для ограничения FOREIGN KEY
     * */
    protected Map<String, List<String>> constrFK;

}
