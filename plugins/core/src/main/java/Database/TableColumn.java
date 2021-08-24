package Database;

public abstract class TableColumn {

    final String TEMPLATE_FOR_CREATE = "`%1s` %2s %3s %4s %5s,";

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

    /**
     * Функция возвращает строку для использования в запросе CREATE TABLE.
     * */
    public String toCreate(){
        String res = String.format(TEMPLATE_FOR_CREATE,
                name,
                (type),
                (isPrimaryKey) ? ("primary key autoincrement") : (""),
                "",
                "");
        if(!isPrimaryKey) {
            res = String.format(TEMPLATE_FOR_CREATE,
                    name,
                    (type),
                    "",
                    (isUnique)?("unique"):(""),
                    (isNotNull)?("not null"):(""));
        }
        return res;
    }

    public boolean equals(Database.TableColumn obj) {
        return name.equals(obj.getName());
    }

}
