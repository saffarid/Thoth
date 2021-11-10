package ThothCore.ThothLite.DBData.Tables;

import Database.Column.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.ListElement;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static ThothCore.ThothLite.DBLiteStructure.FullStructure.StructureDescription.ProductTypes.*;

public class ProductTypes
        extends Data<Listed> {

    public ProductTypes() {
        super();
        setName(TABLE_NAME);
    }

    @Override
    public List<HashMap<String, Object>> convertToMap(List<? extends Identifiable> list) {
        List<HashMap<String, Object>> res = new LinkedList<>();
        for (Identifiable identifiable : list) {
            HashMap<String, Object> map = new HashMap<>();
            Listed listed = (Listed) identifiable;
            map.put(PRODUCT_TYPES, listed.getValue());
            res.add(map);
        }
        return res;
    }

    @Override
    public void readTable(List<HashMap<String, Object>> data) {
        for (HashMap<String, Object> row : data) {
            datas.add(
                    new ListElement(
                            String.valueOf(row.get(ID)),
                            (String) row.get(PRODUCT_TYPES)
                    )
            );
        }
    }

    @Override
    public void readTable(ResultSet resultSet) {

    }

    @Override
    public void readTableWithTableColumn(List<HashMap<TableColumn, Object>> data) {

    }
}
