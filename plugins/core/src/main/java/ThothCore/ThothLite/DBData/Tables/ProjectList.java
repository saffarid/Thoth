package ThothCore.ThothLite.DBData.Tables;

import Database.Column.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Projectable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storing;
import ThothCore.ThothLite.DBLiteStructure.FullStructure.StructureDescription;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static ThothCore.ThothLite.DBLiteStructure.FullStructure.StructureDescription.ProjectsList.*;

public class ProjectList
        extends Data<Projectable>
{
    public ProjectList() {
        super();
        setName(TABLE_NAME);
    }

    @Override
    public List<HashMap<String, Object>> convertToMap(List<? extends Identifiable> list){
        List<HashMap<String, Object>> res = new LinkedList<>();
        for (Identifiable identifiable : list){
            Projectable projectable = (Projectable) identifiable;

            for(Storing storing : projectable.getComposition()){
                HashMap<String, Object> map = new HashMap<>();
                Storagable storagable = storing.getStoragable();

                map.put(ID, projectable.getId());
                map.put(NAME, projectable.getName());
                map.put(StructureDescription.Storage.PRODUCT_ID, storagable.getId());
                map.put(StructureDescription.Storage.COUNT, storing.getCount());
                map.put(StructureDescription.Storage.COUNT_TYPE_ID, storing.getCountType().getId());

                res.add(map);
            }
        }
        return res;
    }

    @Override
    public void readTable(List<HashMap<String, Object>> data) {

    }

    @Override
    public void readTable(ResultSet resultSet) {

    }

    @Override
    public void readTableWithTableColumn(List<HashMap<TableColumn, Object>> data) {

    }
}
