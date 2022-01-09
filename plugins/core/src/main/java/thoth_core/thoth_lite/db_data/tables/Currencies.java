package thoth_core.thoth_lite.db_data.tables;

import database.Column.TableColumn;
import thoth_core.thoth_lite.db_data.db_data_element.implement.Currency;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Finance;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Identifiable;
import thoth_core.thoth_lite.db_lite_structure.full_structure.StructureDescription;

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
    public HashMap< String, List< HashMap<String, Object> > > convertToMap(List<? extends Identifiable> list) {

        HashMap< String, List< HashMap<String, Object> > > res = new HashMap<>();

        List<HashMap<String, Object>> datas = new LinkedList<>();

        for (Identifiable identifiable : list) {
            HashMap<String, Object> map = new HashMap<>();
            Finance currency = (Finance) identifiable;
            map.put(ID, currency.getId());
            map.put(CURRENCY, currency.getCurrency());
            map.put(COURSE, currency.getCourse());
            datas.add(map);
        }

        res.put(TABLE_NAME, datas);

        return res;
    }

    @Override
    public void readTable(StructureDescription.TableTypes tableType, List<HashMap<String, Object>> data) {
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
    public void readTable(StructureDescription.TableTypes tableType, ResultSet resultSet) {

    }

    @Override
    public void readTableWithTableColumn(StructureDescription.TableTypes tableType, List<HashMap<TableColumn, Object>> data) {

    }
}