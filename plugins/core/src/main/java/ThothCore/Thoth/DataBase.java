package ThothCore.Thoth;

import Database.DataBaseSQL;
import Database.Table;
import ThothCore.EmptyDatabase.EmptyDatabase;

public class DataBase extends DataBaseSQL {

    public DataBase(EmptyDatabase template) {
        super();
        copyTemplate(template);
    }

    /**
     *  Копирование структуры шаблона в текущую БД
     * */
    public void copyTemplate(EmptyDatabase template){
        tables.add(new Table().copy(template.getTable(EmptyDatabase.TablesList.NAME)));
        tables.add(new Table().copy(template.getTable(EmptyDatabase.TableDesc.NAME)));
        tables.add(new Table().copy(template.getTable(EmptyDatabase.TableTypes.NAME)));
        tables.add(new Table().copy(template.getTable(EmptyDatabase.DataTypes.NAME)));
    }

    public void addTable(Table table){
        tables.add(table);
    }

}
