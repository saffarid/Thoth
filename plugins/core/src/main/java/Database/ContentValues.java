package Database;

import java.util.*;
import java.util.stream.Collectors;

public class ContentValues extends HashMap<TableColumn, Object> {

    private final String TEMPLATE_COMAND_INSERT = "(\n\t%2s\n) values (\n\t%3s)";
    private final String TEMPLATE_COMAND_UPDATE = "`%1s`=%2s,";

    /**
     * Преобразование объекта в строку при создании таблицы в БД
     */
    public String toStringInsert() {
        StringBuilder colName = new StringBuilder("");
        StringBuilder colValue = new StringBuilder("");

        String strColNameTemplate = "`%1s`";
        String strColValTemplate = "\'%1s\'";

        List<TableColumn> columns = new LinkedList<>(keySet());
        columns = columns.stream().filter(column -> get(column) != null).collect(Collectors.toList());
        for (TableColumn column : columns) {
            //Проверяем TableColumn на наличие внешнего ключа
            Object value = get(column);
            if (value != null) {
                TableColumn fkTable = column.getFKTableCol();      //Если внешний ключ установлен, объект не будет равен null
                if (fkTable != null) {
                    /*
                     * Необходимо определить ID строки внешней таблицы, в contentValues содержится значения для пользователя
                     * */
                    String subRequestTemplate = "select `%1s` from `%2s` where %3s";
                    String whereTempalte =
                            (value.getClass().getSimpleName().equals(String.class.getSimpleName()))
                                    ? ("`%1s` = \'%2s\'")
                                    : ("`%1s` = %2s");
                    StringBuilder whereBuilder = new StringBuilder("");
                    if (fkTable.getName().equals(Table.ID)) {
                        //Обработка случая, когда внешний ключ ссылается на идентификатор строки внешней таблицы
                        /*
                         * Формируем подзапрос select ID from `внешняя таблица` where columnName = columnValue.
                         * ID - fkTable.getName, внешняя таблица - fkTable.getTable, fkTable.getName - column, columnValue - get(column)
                         */

                        //Определяем все колонки кроме первичного ключа
                        List<TableColumn> collect = fkTable
                                .getTableParent()
                                .getColumns()
                                .stream()
                                .filter(col -> !col.getName().equals(Table.ID))
                                .collect(Collectors.toList());
                        //Формируем блок Where
                        for (TableColumn col : collect) {
                            whereBuilder.append(
                                    String.format(whereTempalte, col.getName(), value)
                            );
                            if (collect.indexOf(col) != collect.size() - 1) {
                                whereBuilder.append(" or ");
                            }
                        }

                        //Формируем строку подзапроса
                        String subRequest = String.format(
                                subRequestTemplate,
                                fkTable.getName(),   //1
                                fkTable.getTableParent().getName(),     //2
                                whereBuilder.toString()
                        );

                        colValue.append("(" + subRequest + ")");
                    } else {
                        //Обработка подзапроса если есть информация, какой столбец интересует внешний ключ
                        //fkTable - представляет столбец в котором ищем информацию для определения идентификатора
                        Table tableParent = fkTable.getTableParent();
                        String subRequest = String.format(
                                subRequestTemplate,
                                tableParent.getTableCol(tableParent, Table.ID).getName(),   //1
                                tableParent.getName(),     //2
                                String.format(whereTempalte, fkTable.getName(), value)
                        );
                        colValue.append("(" + subRequest + ")");
                    }

                } else {
                    //Колонка не содержит внешний ключ
                    if (value.getClass().getSimpleName().equals(String.class.getSimpleName())) {
                        colValue.append(
                                String.format(strColValTemplate, value)
                        );
                    } else {
                        colValue.append(value);
                    }
                }
                colName.append(
                        String.format(strColNameTemplate, column.getName())
                );
                if (columns.indexOf(column) != (columns.size() - 1)) {
                    colName.append(", \n\t");
                    colValue.append(", \n\t");
                }
            }
        }

        return String.format(TEMPLATE_COMAND_INSERT, colName.toString(), colValue.toString());
    }


    /**
     * Преобразование объекта в строку при обновлении записи в БД
     */
    public String toStringUpdate() {
        StringBuilder res = new StringBuilder("");
        keySet().stream().forEach(tableColumn -> {
            Object value = get(tableColumn);
            if (value.getClass().getName().equals(String.class.getName())) {
                res.append(String.format(TEMPLATE_COMAND_UPDATE, tableColumn.getName(), "\'" + value.toString() + "\'"));
            } else {
                res.append(String.format(TEMPLATE_COMAND_UPDATE, tableColumn.getName(), value.toString()));
            }
        });
        res.deleteCharAt(res.lastIndexOf(","));
        return res.toString();
    }
}
