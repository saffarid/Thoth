package Database;

import ThothCore.EmptyDatabase.EmptyDatabase;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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

    /**
     *  Копирование структуры шаблона в текущую БД
     * */
    public void copyTemplate(EmptyDatabase template){
        for(Table table : template.getTables()){
            tables.add(new Table().copy(table));
        }
    }
    /**
     * @param name наименование таблицы.
     * @return Объект класса Table по переданному наименованию таблицы, если объект не найдет возвращается null.
     * @see Table
     * */
    public Table getTable(String name){
        Optional<Table> first = tables
                .stream()
                .filter(table -> table.getName().equals(name))
                .findFirst();
        if(first.isPresent()) return first.get();
        else return null;
    }

    public File getDbUser() {
        return dbUser;
    }

    public List<Table> getTables() {
        return tables;
    }
}
