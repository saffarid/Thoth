package thoth_core.thoth_lite.db_data.tables;

import database.Column.TableColumn;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Orderable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Identifiable;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_core.thoth_lite.db_lite_structure.full_structure.StructureDescription;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static thoth_core.thoth_lite.db_lite_structure.full_structure.StructureDescription.Incomes.*;

public class Incomes
        extends Data<Orderable> {

    public Incomes() {
        super();
        setName(TABLE_NAME);
    }

    @Override
    public List<HashMap<String, Object>> convertToMap(List<? extends Identifiable> list) {
        List<HashMap<String, Object>> res = new LinkedList<>();
        for(Identifiable identifiable : list){
            HashMap<String, Object> map = new HashMap<>();
            Orderable orderable = (Orderable) identifiable;
            map.put(ORDER_ID, orderable.getId());
            res.add(map);
        }
        return res;
    }

    @Override
    public void readTable(List<HashMap<String, Object>> data) {
        datas.clear();
        for(HashMap<String, Object> row : data){
            try {
                Orderable orderable = (Orderable) getFromTableById(StructureDescription.Orders.TABLE_NAME, String.valueOf(row.get(ORDER_ID)));
                addData(orderable);
            } catch (NotContainsException e) {
                e.printStackTrace();
            }
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
