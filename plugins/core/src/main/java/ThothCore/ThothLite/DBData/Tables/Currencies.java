package ThothCore.ThothLite.DBData.Tables;

import Database.Column.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.Currency;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static ThothCore.ThothLite.DBLiteStructure.FullStructure.StructureDescription.Currency.*;

public class Currencies
        extends Data<Currency> {

    public Currencies() {
        super();
        setName(TABLE_NAME);
    }

    @Override
    public List<HashMap<String, Object>> convertToMap(List<? extends Identifiable> list) {
        List<HashMap<String, Object>> res = new LinkedList<>();

        for (Identifiable identifiable : list) {
            HashMap<String, Object> map = new HashMap<>();
            Currency currency = (Currency) identifiable;
            map.put(CURRENCY, currency.getCurrency());
            map.put(COURSE, currency.getCourse());
            res.add(map);
        }

        return res;
    }

    @Override
    public void readTable(List<HashMap<String, Object>> data) {
        for (HashMap<String, Object> row : data) {
            datas.add(new Currency(
                    String.valueOf(row.get(ID)),
                    (String) row.get(CURRENCY),
                    (Double) row.get(COURSE)
            ));
        }
    }

    @Override
    public void readTable(ResultSet resultSet) {

    }

    @Override
    public void readTableWithTableColumn(List<HashMap<TableColumn, Object>> data) {

    }
}
