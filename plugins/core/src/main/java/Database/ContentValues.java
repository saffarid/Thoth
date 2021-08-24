package Database;

import ThothCore.Guardkeeper.DataBaseDescription.DataBaseInfo;

import java.util.HashMap;

public class ContentValues extends HashMap<TableColumn, Object> {

    private final String TEMPLATE_COMAND_INSERT = "(%2s) values %3s";
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
        String rowTemplate = "(%1s)";
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
    public String toStringCreateTable() {
        return String.format(TEMPLATE_COMAND_INSERT, getColumnListInsert(), getColumnValueInsert());
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
