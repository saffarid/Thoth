package ThothCore.ThothLite.DBData;

import Database.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.ListElement;
import ThothCore.ThothLite.DBLiteStructure.StructureDescription;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

public class OrderStatus
        extends Data<ListElement>
        implements TableReadable {

    public OrderStatus() {
        super();
        name = StructureDescription.OrderStatus.TABLE_NAME;
    }

    @Override
    public void readTable(List<HashMap<String, Object>> data) {
        for(HashMap<String, Object> row : data){
            datas.add(
                    new ListElement(
                            String.valueOf(row.get(StructureDescription.OrderStatus.ID)),
                            (String) row.get(StructureDescription.OrderStatus.ORDER_STATUS)
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
