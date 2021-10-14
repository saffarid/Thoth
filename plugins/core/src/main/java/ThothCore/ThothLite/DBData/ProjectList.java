package ThothCore.ThothLite.DBData;

import Database.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.Project;
import ThothCore.ThothLite.DBLiteStructure.StructureDescription;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

public class ProjectList
        extends Data<Project>
        implements TableReadable {
    public ProjectList() {
        super();
        name = StructureDescription.ProjectsList.TABLE_NAME;
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
