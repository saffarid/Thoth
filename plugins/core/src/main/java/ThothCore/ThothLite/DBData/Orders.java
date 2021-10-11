package ThothCore.ThothLite.DBData;

import Database.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.ListElement;
import ThothCore.ThothLite.DBData.DBDataElement.Order;
import ThothCore.ThothLite.DBData.DBDataElement.Partner;
import ThothCore.ThothLite.DBData.DBDataElement.Project;
import ThothCore.ThothLite.DBLiteStructure.StructureDescription;
import ThothCore.ThothLite.TableReadable;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import static ThothCore.ThothLite.DBLiteStructure.StructureDescription.Orders.*;

public class Orders
        extends Data<Order>
        implements TableReadable {

    public Orders() {
        super();
        name = TABLE_NAME;
    }

    @Override
    public void readTable(List<HashMap<String, Object>> data) throws ParseException {
        for(HashMap<String, Object> row : data){

            datas.add(
                    new Order(
                            (String) row.get(ID),
                            (Partner) DBData.getInstance().getTable(StructureDescription.Partners.TABLE_NAME)
                            .getById( (String) row.get(CUSTOMER_ID) ),
                            (Project) DBData.getInstance().getTable(StructureDescription.ProjectsList.TABLE_NAME)
                            .getById( (String) row.get(PROJECT_ID) ),
                            (int) row.get(IS_MONTHLY) == 1,
                            DateFormat.getDateInstance().parse((String) row.get(DATE_START)),
                            DateFormat.getDateInstance().parse((String) row.get(DATE_FINISH)),
                            (ListElement) DBData.getInstance().getTable(StructureDescription.OrderStatus.TABLE_NAME)
                            .getById( (String) row.get(STATUS_ID)),
                            (int) row.get(AUTOFINISH) == 1
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
