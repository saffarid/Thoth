package ThothCore.ThothLite.DBData.Tables;

import Database.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.Order;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.Partner;
import ThothCore.ThothLite.DBData.DBDataElement.Listed;
import ThothCore.ThothLite.DBData.DBDataElement.Orderable;
import ThothCore.ThothLite.DBData.DBDataElement.Projectable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Finishable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.StructureDescription;

import java.sql.ResultSet;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import static ThothCore.ThothLite.StructureDescription.Orders.*;

public class Orders
        extends Data<Orderable>
{

    public Orders() {
        super();
        setName(TABLE_NAME);
    }

    @Override
    public HashMap<String, Object> convertToMap(Identifiable identifiable) {
        HashMap<String, Object> res = new HashMap<>();

        Orderable orderable = (Orderable) identifiable;

        res.put( CUSTOMER_ID, orderable.getPartnerId() );
        res.put( PROJECT_ID, orderable.getProjectable().getId() );
        res.put( DATE_START, orderable.startDate().format(DateTimeFormatter.ISO_DATE) );
        res.put( DATE_FINISH, orderable.finishDate().format(DateTimeFormatter.ISO_DATE) );

        return res;
    }

    @Override
    public void readTable(List<HashMap<String, Object>> data) throws ParseException {
        for (HashMap<String, Object> row : data) {
            addData(
                    new Order(
                            String.valueOf(row.get(ID)),
                            (Partner) getFromTableById(StructureDescription.Partners.TABLE_NAME, String.valueOf(row.get(CUSTOMER_ID))) ,
                            (Projectable) getFromTableById(StructureDescription.ProjectsList.TABLE_NAME, String.valueOf(row.get(PROJECT_ID))),
                            (int) row.get(IS_MONTHLY) == 1,
                            (String) row.get(DATE_START),
                            (String) row.get(DATE_FINISH),
                            (Listed) getFromTableById(StructureDescription.OrderStatus.TABLE_NAME, String.valueOf(row.get(STATUS_ID))),
                            (int) row.get(AUTOFINISH) == Finishable.AUTOFINISH
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
