package Database;

import ThothCore.EmptyDatabase.EmptyDatabase;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public abstract class DataBaseSQL {

    private File dbUser;

    /**
     * Список таблиц
     * */
    private List<Table> tables;

    public DataBaseSQL() {
        tables = new LinkedList<>();
    }

    public DataBaseSQL(EmptyDatabase template){
        tables = new LinkedList<>();
        copyTemplate(template);
    }

    public void copyTemplate(EmptyDatabase template){
        for(Table table : template.getTables()){
            tables.add(new Table().copy(table));
        }
    }

    public Table getTable(String name){
        return tables
                .stream()
                .filter(table -> table.getName().equals(name))
                .findFirst()
                .get();
    }


}
