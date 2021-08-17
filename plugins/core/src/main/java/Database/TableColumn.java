package Database;

public abstract class TableColumn {

    final String TEMPLATE_FOR_CREATE = "`%1s` %2s %3s %4s %5s,";

    /**
     * Наименование столбца
     * */
    String name;
    /**
     * Тип данных столбца.
     * Переменная должна хранить значение равное ключу в файлах типов колонок.
     * */

    String type;
    /**
     * Уникальность значений в каждой записи данного столбца
     * */

    boolean isUnique;
    /**
     * Если записи столбца представляют собой первичный ключ
     * */

    boolean isPrimaryKey;
    /**
     * Возможность записи пустых значений
     * */
    boolean isNotNull;

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


}
