package thoth_core.thoth_lite.db_data.tables;

import database.Column.TableColumn;
import thoth_core.thoth_lite.db_data.db_data_element.implement.Currency;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Finance;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Identifiable;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static thoth_core.thoth_lite.db_lite_structure.full_structure.StructureDescription.Currency.*;

public class Currencies
        extends Data<Finance> {

    public Currencies() {
        super();
        setName(TABLE_NAME);
    }

    @Override
    public List<HashMap<String, Object>> convertToMap(List<? extends Identifiable> list) {
        List<HashMap<String, Object>> res = new LinkedList<>();

        for (Identifiable identifiable : list) {
            HashMap<String, Object> map = new HashMap<>();
            Finance currency = (Finance) identifiable;
            map.put(ID, currency.getId());
            map.put(CURRENCY, currency.getCurrency());
            map.put(COURSE, currency.getCourse());
            res.add(map);
        }

        return res;
    }

    @Override
    public void readTable(List<HashMap<String, Object>> data) {
        datas.clear();
        for (HashMap<String, Object> row : data) {
            datas.add(new Currency(
                    String.valueOf(row.get(ID)),
                    (String) row.get(CURRENCY),
                    (Double) row.get(COURSE)
            ));
        }
        publisher.submit(datas);
    }

    @Override
    public void readTable(ResultSet resultSet) {

    }

    @Override
    public void readTableWithTableColumn(List<HashMap<TableColumn, Object>> data) {

    }
}
