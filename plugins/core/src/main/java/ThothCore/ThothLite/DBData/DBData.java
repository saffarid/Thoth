package ThothCore.ThothLite.DBData;

import ThothCore.ThothLite.DBData.Tables.*;
import ThothCore.ThothLite.Exceptions.NotContainsException;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class DBData {

    private static DBData dbData;
    private List<Data> tables;

    private DBData() {
        tables = new LinkedList<>();

        tables.add(new CountTypes());
        tables.add(new Currencies());
        tables.add(new Incomes());
        tables.add(new IncomeTypes());
        tables.add(new Orders());
        tables.add(new OrderStatus());
        tables.add(new Partners());
        tables.add(new Products());
        tables.add(new ProductTypes());
        tables.add(new ProjectList());
        tables.add(new Purchases());
        tables.add(new Storage());
        tables.add(new NotUsed());
    }

    public static DBData getInstance(){
        if(dbData == null){
            dbData = new DBData();
        }
        return dbData;
    }

    public Data getTable(
            String name
    ) throws NotContainsException {
        Optional<Data> res = tables.stream()
                .filter(data -> data.getName().equals(name))
                .findFirst();
        if(!res.isPresent()) throw new NotContainsException();
        return res.get();
    }

    public TableReadable getTableReadable(
            String name
    ) throws NotContainsException {
        return getTable(name);
    }



}
