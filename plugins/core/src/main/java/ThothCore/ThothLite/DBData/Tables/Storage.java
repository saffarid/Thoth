package ThothCore.ThothLite.DBData.Tables;

import Database.Column.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.ListElement;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.StorageCell;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storing;
import ThothCore.ThothLite.Exceptions.NotContainsException;
import ThothCore.ThothLite.DBLiteStructure.FullStructure.StructureDescription;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static ThothCore.ThothLite.DBLiteStructure.FullStructure.StructureDescription.IncomeTypes.ID;
import static ThothCore.ThothLite.DBLiteStructure.FullStructure.StructureDescription.Storage.*;

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
        for (HashMap<String, Object> row : data) {
            addData(
                    new ListElement(
                            String.valueOf(row.get(ID)),
                            (String) row.get(ADRESS)
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
