package ThothCore.ThothLite.DBData.Tables;

import Database.Column.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Orderable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.Exceptions.NotContainsException;
import ThothCore.ThothLite.DBLiteStructure.FullStructure.StructureDescription;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static ThothCore.ThothLite.DBLiteStructure.FullStructure.StructureDescription.Incomes.*;

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
    }

    @Override
    public void readTable(ResultSet resultSet) {

    }

    @Override
    public void readTableWithTableColumn(List<HashMap<TableColumn, Object>> data) {

    }
}
