package thoth_core.thoth_lite.db_data.tables;

import database.Column.TableColumn;
import thoth_core.thoth_lite.db_data.db_data_element.implement.ListElement;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Listed;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Identifiable;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static thoth_core.thoth_lite.db_lite_structure.full_structure.StructureDescription.IncomeTypes.ID;
import static thoth_core.thoth_lite.db_lite_structure.full_structure.StructureDescription.Storage.*;

public class Storage
        extends Data<Listed> {

    public Storage() {
        super();
        setName(TABLE_NAME);
    }

    @Override
    public List<HashMap<String, Object>> convertToMap(List<? extends Identifiable> list) {
        List<HashMap<String, Object>> res = new LinkedList<>();

        for (Identifiable identifiable : list) {
            Listed storing = (Listed) identifiable;
            HashMap<String, Object> map = new HashMap<>();

            map.put(ID, storing.getId());
            map.put(ADRESS, storing.getValue());

            res.add(map);
        }

        return res;
    }

    @Override
    public void readTable(List<HashMap<String, Object>> data) {
        datas.clear();
        for (HashMap<String, Object> row : data) {
            addData(
                    new ListElement(
                            String.valueOf(row.get(ID)),
                            (String) row.get(ADRESS)
                    )
            );
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
