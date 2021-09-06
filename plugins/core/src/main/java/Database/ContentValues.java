package Database;

import ThothCore.Guardkeeper.DataBaseDescription.DataBaseInfo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ContentValues extends HashMap<TableColumn, Object> {

    private final String TEMPLATE_COMAND_INSERT = "(%2s) values (%3s)";
    private final String TEMPLATE_COMAND_UPDATE = "`%1s`=%2s,";

    /**
     * @return Строка список столбцов
     */
    public String getColumnListInsert() {
        String template = "`%1s` ,";
        StringBuilder res = new StringBuilder("");
        keySet().stream().forEach(column -> {
            res.append(String.format(template, column.getName()));
        });
        res.deleteCharAt(res.lastIndexOf(","));
        return res.toString();
    }

    /**
     * @return Строка значений
     */
    public String getColumnValueInsert() {
        String strTemplate = "\'%1s\' ,";
//        String template = "%1s,";
        String rowTemplate = "%1s";
        StringBuilder res = new StringBuilder("");
        keySet().stream().forEach(col -> {
            if (col.getType().equals(DataBaseInfo.DatabasesPath.TEXT)){
                res.append(String.format(strTemplate, get(col)));
            }
        });
        res.deleteCharAt(res.lastIndexOf(","));
        return String.format(rowTemplate, res.toString());
    }

    /**
     * Преобразование объекта в строку при создании таблицы в БД
     */
    public String toStringInsert() {
        StringBuilder colName = new StringBuilder("");
        StringBuilder colValue = new StringBuilder("");

        String strColNameTemplate = "`%1s`";
        String strColValTemplate = "\'%1s\'";

        List<TableColumn> columns = new LinkedList<>(keySet());

        for(TableColumn column: columns){
            //Проверяем TableColumn на наличие внешнего ключа

            TableColumn fkTable = column.getFKTable();      //Если внешний ключ установлен, объект не будет равен null
            if(fkTable != null){
                /*
                * Необходимо определить ID строки внешней таблицы, в contentValues содержится значения для пользователя
                * */

                /*
                * Формируем подзапрос select ID from `внешняя таблица` where columnName = columnValue.
                * ID - fkTable.getName, внешняя таблица - fkTable.getTable, fkTable.getName - column, columnValue - get(column)
                */
                String subRequestTemplate = "select `%1s` from `%2s` where `%3s` = %4s";
                String subRequestStrTemplate = "select `%1s` from `%2s` where `%3s` = \'%4s\'";
                //Определяем колонку первичный ключ внешней таблицы
                TableColumn fkTableColCont = fkTable
                        .getTableParent()
                        .getColumns()
                        .stream()
                        .filter(col -> !col.getName().equals(Table.ID))
                        .findFirst()
                        .get();
                //Формируем строку подзапроса
                if(get(column).getClass().getSimpleName().equals(String.class.getSimpleName())){
                    subRequestTemplate = "select `%1s` from `%2s` where `%3s` = \'%4s\'";
                }

                String subRequest = String.format(
                        subRequestTemplate,
                        fkTable.getTableParent().getIdColumn().getName(),   //1
                        fkTable.getTableParent().getName(),     //2
                        fkTableColCont.getName(),      //Неверно определяется столбец
                        get(column)     //4
                );

                System.out.println(subRequest);
                colValue.append("(" + subRequest + ")");
            }else{
                Object value = get(column);
                if(value.getClass().getSimpleName().equals(String.class.getSimpleName())){
                    colValue.append(
                            String.format(strColValTemplate, value)
                    );
                }else{
                    colValue.append(value);
                }
            }
            colName.append(
                    String.format(strColNameTemplate, column.getName())
            );
            if(columns.indexOf(column) != (columns.size() - 1)){
                colName.append(", ");
                colValue.append(", ");
            }
        }

//        return String.format(TEMPLATE_COMAND_INSERT, getColumnListInsert(), getColumnValueInsert());
        return String.format(TEMPLATE_COMAND_INSERT, colName.toString(), colValue.toString());
    }


    /**
     * Преобразование объекта в строку при обновлении записи в БД
     */
    public String toStringUpdate() {
        StringBuilder res = new StringBuilder("");
        keySet().stream().forEach(tableColumn -> {
            Object value = get(tableColumn);
            if(value.getClass().getName().equals(String.class.getName())){
                res.append(String.format(TEMPLATE_COMAND_UPDATE, tableColumn.getName(), "\'" + value.toString() + "\'"));
            }else{
                res.append(String.format(TEMPLATE_COMAND_UPDATE, tableColumn.getName(), value.toString()));
            }
        });
        res.deleteCharAt(res.lastIndexOf(","));
        return res.toString();
    }
}
