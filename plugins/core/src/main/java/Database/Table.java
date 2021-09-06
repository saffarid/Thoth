package Database;

import ThothCore.EmptyDatabase.EmptyDatabase;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Шаблон описания создаваемой/считываемой таблицы
 */
public abstract class Table {

    public static final String ID = "id";
    protected static final String ID_TYPE = "integer";
    private final String PK_CONSTR = "constraint `%1s_pk` primary key(%2s) autoincrement";
    private final String UNIQ_CONSTR = "constraint `%1s_%2s_uniq` unique(%3s)";
    private final String FK_CONSTR = "constraint `%1s_%2s_fk` foreign key (%3s) references `%4s` (%5s)";

    /**
     * Тип пользовательская таблица
     */
    public static final String TABLE = "table";
    /**
     * Системная таблица, только для чтения
     */
    public static final String SYSTEM_TABLE_RO = "system_table_ro";
    /**
     * Системная таблица, чтение/запись
     */
    public static final String SYSTEM_TABLE_RW = "system_table_rw";
    /**
     * Системная таблица, не доступна для пользователя
     */
    public static final String SYSTEM_TABLE_NA = "system_table_na";

    private TableColumn idColumn;

    /**
     * Наименование таблицы
     */
    protected String name;

    /**
     * Тип таблицы
     */
    protected String type;

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
     * Ключом является колонка текущей таблицы, значение - внешняя таблица
     */
    protected Map<TableColumn, TableColumn> constrFK;

    public Table() {
        columns = new LinkedList<>();
        constrPKColumns = new LinkedList<>();
        constrNNColumns = new LinkedList<>();
        constrUColumns = new LinkedList<>();
        constrFK = new HashMap<>();
        idColumn = new TableColumn(
                ID,
                ID_TYPE,
                true);
        addColumn(idColumn);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<TableColumn> getColumns() {
        return columns;
    }

    public void addColumn(TableColumn column) {
        if (!columns.contains(column)) {
            columns.add(column);
            column.setTableParent(this);
        }
        if (column.isPrimaryKey()) {
            if (!constrPKColumns.contains(column)) constrPKColumns.add(column);
//            if (!constrNNColumns.contains(column)) constrNNColumns.add(column);
        }
        if (column.isNotNull() && !constrNNColumns.contains(column)) constrNNColumns.add(column);
        if (column.isUnique() && !constrUColumns.contains(column)) constrUColumns.add(column);
        if (column.getFKTable() != null) constrFK.put(column, column.getFKTable());
    }

    public void removeColumn(TableColumn column) {
        if (columns.contains(column)) columns.remove(column);

        if (column.isPrimaryKey()) {
            if (constrPKColumns.contains(column)) constrPKColumns.remove(column);
            if (constrNNColumns.contains(column)) constrNNColumns.remove(column);
        }
        if (column.isNotNull() && constrNNColumns.contains(column)) constrNNColumns.remove(column);
        if (column.isUnique() && constrUColumns.contains(column)) constrUColumns.remove(column);
    }

    public String getConstrPK() {
        //Получаем список столбцов в коллекции первичного ключа
        StringBuilder columns = new StringBuilder(constrPKColumns.stream().map(column -> column.getName()).collect(Collectors.toList()).toString());
        String res = String.format(PK_CONSTR, name, columns.deleteCharAt(columns.indexOf("[")).deleteCharAt(columns.indexOf("]")).toString());
        return res.trim();
    }

    public String getConstrUniq() {
        List<String> columns = constrUColumns.stream().map(column -> column.getName()).collect(Collectors.toList());
        StringBuilder res = new StringBuilder("");

        for (String name : columns) {
            res.append(String.format(UNIQ_CONSTR, this.name, name.trim(), "`" + name.trim() + "`"));
            if (columns.indexOf(name) != columns.size() - 1) {
                res.append(", ");
            }
        }
        return res.toString();
    }

    public String getConstrFK() {

        StringBuilder res = new StringBuilder("");
        if (constrFK != null && !constrFK.keySet().isEmpty()) {
            ArrayList<TableColumn> tableColumns = new ArrayList(constrFK.keySet());
            for (TableColumn column : tableColumns) {
                Table table = constrFK.get(column).getTableParent();
                List<String> collect = table.getColumns()
                        .stream()
                        .filter(column1 -> column1.isPrimaryKey())
                        .map(column1 -> column1.getName())
                        .collect(Collectors.toList());
                StringBuilder tableColumnsPK = new StringBuilder(
                        collect
                                .toString()
                );
                res.append(String.format(
                        FK_CONSTR,
                        name,
                        table.getName(),
                        "`" + column.getName() + "`",
                        table.getName(),
                        "`" + tableColumnsPK.deleteCharAt(tableColumnsPK.indexOf("[")).deleteCharAt(tableColumnsPK.indexOf("]")).toString() + "`"
                        )
                );
                if (tableColumns.indexOf(column) != tableColumns.size() - 1) {
                    res.append(", ");
                }
            }
        }
        return res.toString();
    }

    public boolean hasConstPK() {
        return !constrPKColumns.isEmpty();
    }

    public boolean hasConstrUniq() {
        return !constrUColumns.isEmpty();
    }

    public boolean hasConstrFK() {
        return !constrFK.isEmpty();
    }

    public TableColumn getIdColumn(){
        return idColumn;
    }
}
