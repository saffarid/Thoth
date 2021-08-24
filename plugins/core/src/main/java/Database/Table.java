package Database;

import java.util.List;
import java.util.Map;

/**
 * Шаблон описания создаваемой/считываемой таблицы
 */
public abstract class Table {

    /**
     * Наименование таблицы
     */
    protected String name;

    /**
     * Список столбцов
     */
    protected List<TableColumn> columns;

    /**
     * Список столбцов для ограничения PRIMARY KEY
     */
    protected List<TableColumn> constrPKColumns;

    /**
     * Список столбцов для ограничения UNIQUE
     */
    protected List<TableColumn> constrUColumns;

    /**
     * Список столбцов для ограничения NOT NULL
     */
    protected List<TableColumn> constrNNColumns;

    /**
     * Список для ограничения FOREIGN KEY
     */
    protected Map<Table, List<TableColumn>> constrFK;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TableColumn> getColumns() {
        return columns;
    }

    public void addColumn(TableColumn column) {
        if(!columns.contains(column)) columns.add(column);
        if (column.isPrimaryKey()) {
            if(!constrPKColumns.contains(column)) constrPKColumns.add(column);
            if(!constrNNColumns.contains(column))constrNNColumns.add(column);
        }
        if (column.isNotNull() && !constrNNColumns.contains(column)) constrNNColumns.add(column);
        if (column.isUnique() && !constrUColumns.contains(column)) constrUColumns.add(column);
    }

    public void removeColumn(TableColumn column){
        if(columns.contains(column))columns.remove(column);

        if (column.isPrimaryKey()) {
            if(constrPKColumns.contains(column)) constrPKColumns.remove(column);
            if(constrNNColumns.contains(column))constrNNColumns.remove(column);
        }
        if (column.isNotNull() && constrNNColumns.contains(column)) constrNNColumns.remove(column);
        if (column.isUnique() && constrUColumns.contains(column)) constrUColumns.remove(column);
    }

}
