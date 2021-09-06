package Database;

public class TableColumn {

    final String TEMPLATE_FOR_CREATE = "`%1s` %2s%3s";

    /**
     * Наименование столбца
     * */
    protected String name;
    /**
     * Тип данных столбца.
     * Переменная должна хранить значение равное ключу в файлах типов колонок.
     * */

    protected String type;
    /**
     * Уникальность значений в каждой записи данного столбца
     * */

    protected boolean isUnique;
    /**
     * Если записи столбца представляют собой первичный ключ
     * */

    protected boolean isPrimaryKey;
    /**
     * Возможность записи пустых значений
     * */
    protected boolean isNotNull;

    /**
     * Колонка внешней таблицы, на которую ссылается внешний ключ
     * */
    protected TableColumn FKTable;

    /**
     * Таблица, в которую добавляется колонка
     * */
    private Table tableParent;

    public TableColumn(String name, String type, boolean isPrimaryKey) {
        this.name = name;
        this.type = type;
        this.isUnique = false;
        this.isPrimaryKey = isPrimaryKey;
        this.isNotNull = false;
    }

    public TableColumn(String name, String type, boolean isUnique, boolean isPrimaryKey, boolean isNotNull, TableColumn table) {
        this.name = name;
        this.type = type;
        this.isUnique = isUnique;
        this.isPrimaryKey = isPrimaryKey;
        this.isNotNull = isNotNull;
        this.FKTable = table;
    }

    public TableColumn(String name, String type, boolean isUnique, boolean isPrimaryKey, boolean isNotNull) {
        this.name = name;
        this.type = type;
        this.isUnique = isUnique;
        this.isPrimaryKey = isPrimaryKey;
        this.isNotNull = isNotNull;
        this.FKTable = null;
    }

    public TableColumn() {
        name = "";
        type = "";
        isUnique = false;
        isPrimaryKey = false;
        isNotNull = false;
        FKTable = null;
    }

    /**
     * Функция возвращает наименование колонки в формате Наименование_таблицы.Наименование_колонки
     * */
    public String getFullName(){
        return tableParent.getName() + "." + getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isUnique() {
        return isUnique;
    }

    public void setUnique(boolean unique) {
        isUnique = unique;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public boolean isNotNull() {
        return isNotNull;
    }

    public void setNotNull(boolean notNull) {
        isNotNull = notNull;
    }

    public TableColumn getFKTable() {
        return FKTable;
    }

    public void setFKTable(TableColumn FKTable) {
        this.FKTable = FKTable;
    }

    public void setTableParent(Table table){
        tableParent = table;
    }

    public Table getTableParent(){
        return tableParent;
    }

    /**
     * Функция возвращает строку для использования в запросе CREATE TABLE.
     * */
    public String toCreate(){
        String res;
        if (isPrimaryKey){
            res = String.format(TEMPLATE_FOR_CREATE,
                    name,
                    (type),
                    (isPrimaryKey) ? (" primary key autoincrement") : ("")
            );
        }else {
            res = String.format(TEMPLATE_FOR_CREATE,
                    name,
                    (type),
                    (isNotNull) ? (" not null") : ("")
            );
        }
        return res;
    }

    public boolean equals(Database.TableColumn obj) {
        return name.equals(obj.getName());
    }

}
