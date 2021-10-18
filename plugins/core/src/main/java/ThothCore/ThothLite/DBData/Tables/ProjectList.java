package ThothCore.ThothLite.DBData.Tables;

import Database.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.Projectable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import static ThothCore.ThothLite.StructureDescription.ProjectsList.*;

public class ProjectList
        extends Data<Projectable>
{
    public ProjectList() {
        super();
        setName(TABLE_NAME);
    }

    @Override
    public HashMap<String, Object> convertToMap(Identifiable identifiable) {
        return null;
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
