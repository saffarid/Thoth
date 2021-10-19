package ThothCore.ThothLite.DBData.Tables;

import Database.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.StorageCell;
import ThothCore.ThothLite.DBData.DBDataElement.Listed;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Storagable;
import ThothCore.ThothLite.DBData.DBDataElement.Storing;
import ThothCore.ThothLite.StructureDescription;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static ThothCore.ThothLite.StructureDescription.Storage.*;

public class Storage
        extends Data<Storing>
{

    public Storage() {
        super();
        setName(TABLE_NAME);
    }

    @Override
    public List<HashMap<String, Object>> convertToMap(List<? extends Identifiable> list){
        List<HashMap<String, Object>> res = new LinkedList<>();



        return res;
    }

    @Override
    public void readTable(List<HashMap<String, Object>> data) {
        for (HashMap<String, Object> row : data) {
            addData(
                    new StorageCell(
                            (String) row.get(ID),
                            (Storagable) getFromTableById(StructureDescription.Products.TABLE_NAME, String.valueOf(row.get(PRODUCT_ID))),
                            (Double) row.get(COUNT),
                            (Listed) getFromTableById(StructureDescription.CountTypes.TABLE_NAME, String.valueOf(row.get(COUNT_TYPE_ID)))
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
