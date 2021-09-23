package Database;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class WhereValues extends HashMap<TableColumn, Object> {
    //    StringBuilder result = new StringBuilder("");
//    keySet().stream().forEach(key -> {
//        result.append(String.format(TEMPLATE, key, get(key)));
//    });
//        result.deleteCharAt(result.lastIndexOf(","))
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");
        String templateFullName = "\"%1s\".`%2s`";
        LinkedList<TableColumn> tablesColumns = new LinkedList<>(this.keySet());

        for (TableColumn tableColumn : tablesColumns) {

            Object value = this.get(tableColumn);
            String whereTemplate = (value.getClass().getName().equals(String.class.getName())) ? ("%1s=\'%2s\'") : ("%1s=%2s");
            TableColumn fkTableCol = tableColumn.getFKTableCol();
            if (fkTableCol != null) {
                String subreqTemplate = "(select `%1s` from `%2s` where %3s)";
                if (fkTableCol.getName().equals(Table.ID)) {
                    //Внешний ключ ссылается на колонку идентификатор внешней таблицы
                    /*Формируем подзапрос следующего вида
                     * select id from fkTable where "все колонки кроме id" = value
                     */
                    //Определяем колонки для вывода информации

                    List<TableColumn> collect = fkTableCol.getTableParent().getColumns()
                            .stream()
                            .filter(column1 -> !column1.getName().equals(Table.ID))
                            .collect(Collectors.toList());

                    StringBuilder whereSubrequesResult = new StringBuilder("");

                    for(TableColumn column : collect){
                        whereSubrequesResult.append(
                            String.format(
                                    whereTemplate
                                    , String.format(templateFullName, column.getTableParent().getName(), column.getName())
                                    , value
                            )
                        );
                        if(collect.indexOf(column) != collect.size() - 1){
                            whereSubrequesResult.append(" or \n\t");
                        }
                    }

                    String subrequest = String.format(
                            subreqTemplate
                            , fkTableCol.getName()
                            , fkTableCol.getTableParent().getName()
                            , whereSubrequesResult.toString()
                    );

                    result.append(
                            String.format("`%1s` = %2s", tableColumn.getName(), subrequest)
                    );
                } else {
                    //Внешний ключ ссылается на кастомную колонку внешней таблицы
                    /*Формируем подзапрос следующего вида
                     * select fkTableCol from fkTable where fk.id = value*/
                    String subrequest =
                            String.format(
                                    subreqTemplate
                                    , fkTableCol.getTableParent().getTableCol(Table.ID).getName()
                                    , fkTableCol.getTableParent().getName()
                                    , String.format(
                                            whereTemplate
                                            , String.format(templateFullName, fkTableCol.getTableParent().getName(), fkTableCol.getName())
                                            , value
                                    )
                            );
                    result.append(
                            String.format("`%1s` = %2s", tableColumn.getName(), subrequest)
                    );
                }

            } else {
                //Колонка не содержит внешнего ключа
                if(value.getClass().getName().equals(String.class.getName())){
                    result.append(
                            String.format("`%1s` = \'%2s\'", tableColumn.getName(), value.toString())
                    );
                }else{
                    result.append(
                            String.format("`%1s` = %2s", tableColumn.getName(), value)
                    );
                }
            }

            if (tablesColumns.indexOf(tableColumn) != tablesColumns.size() - 1) {
                result.append(" and \n\t");
            }

        }

        return result.toString();
    }
}
