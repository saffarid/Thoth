package ThothCore.ThothLite.DBData.Tables;

import Database.Column.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.Order;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.Partner;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Orderable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Projectable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Finishable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.Exceptions.NotContainsException;
import ThothCore.ThothLite.DBLiteStructure.FullStructure.StructureDescription;

import java.sql.ResultSet;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static ThothCore.ThothLite.DBLiteStructure.FullStructure.StructureDescription.Orders.*;

public class Orders
        extends Data<Orderable> {

    public Orders() {
        super();
        setName(TABLE_NAME);
    }

    @Override
    public List<HashMap<String, Object>> convertToMap(List<? extends Identifiable> list) {
        List<HashMap<String, Object>> res = new LinkedList<>();
        for (Identifiable identifiable : list) {
            HashMap<String, Object> map = new HashMap<>();
            Orderable orderable = (Orderable) identifiable;
            map.put(CUSTOMER_ID, orderable.getPartner().getId());
            map.put(PROJECT_ID, orderable.getProjectable().getId());
            map.put(DATE_START, orderable.startDate().format(DateTimeFormatter.ISO_DATE));
            map.put(DATE_FINISH, orderable.finishDate().format(DateTimeFormatter.ISO_DATE));
            map.put(STATUS_ID, orderable.getStatus().getValue() );
            map.put(AUTOFINISH, 0);
            map.put(IS_MONTHLY, 0);
            res.add(map);
        }
        return res;
    }

    @Override
    public void readTable(List<HashMap<String, Object>> data) throws ParseException {
        for (HashMap<String, Object> row : data) {
//            try {
//                addData(
//                        new Order(
//                                String.valueOf(row.get(ID)),
//                                String.valueOf(row.get(S))
//                                (Partner) getFromTableById(StructureDescription.Partners.TABLE_NAME, String.valueOf(row.get(CUSTOMER_ID))),
//                                (Projectable) getFromTableById(StructureDescription.ProjectsList.TABLE_NAME, String.valueOf(row.get(PROJECT_ID))),
//                                (int) row.get(IS_MONTHLY) == 1,
//                                (String) row.get(DATE_START),
//                                (String) row.get(DATE_FINISH),
//                                (Listed) getFromTableById(StructureDescription.OrderStatus.TABLE_NAME, String.valueOf(row.get(STATUS_ID))),
//                                (int) row.get(AUTOFINISH) == Finishable.AUTOFINISH
//                        )
//                );
//            } catch (NotContainsException e) {
//                e.printStackTrace();
//            }
        }
    }

    @Override
    public void readTable(ResultSet resultSet) {

    }

    @Override
    public void readTableWithTableColumn(List<HashMap<TableColumn, Object>> data) {

    }
}
