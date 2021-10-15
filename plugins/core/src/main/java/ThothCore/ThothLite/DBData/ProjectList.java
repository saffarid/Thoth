package ThothCore.ThothLite.DBData;

import Database.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.Project;
import ThothCore.ThothLite.DBLiteStructure.StructureDescription;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import static ThothCore.ThothLite.DBLiteStructure.StructureDescription.ProjectsList.*;

public class ProjectList
        extends Data<Project>
{
    public ProjectList() {
        super();
        setName(TABLE_NAME);
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
