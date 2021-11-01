package Database.Column.Implements;

import Database.Column.DataTypes;
import Database.Column.TableColumn;
import Database.Exceptions.NotSupportedOperation;
import Database.Table;

public abstract class Column
implements TableColumn {

    /**
     * Наименование столбца
     * */
    protected String name;

    /**
     * Тип данных столбца.
     * Переменная должна хранить значение равное ключу в файлах типов колонок.
     * */
    protected DataTypes type;

    /**
     * Таблица, в которую добавляется колонка
     * */
    private Table tableParent;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public TableColumn setName(String name) throws NotSupportedOperation {
        this.name = name;
        return this;
    }

    @Override
    public DataTypes getType() {
        return type;
    }

    @Override
    public TableColumn setType(DataTypes type) throws NotSupportedOperation{
        this.type = type;
        return this;
    }

    @Override
    public Table getTable() {
        return tableParent;
    }

    @Override
    public TableColumn setTable(Table table) {
        tableParent = table;
        return this;
    }
}
