package ThothCore.ThothLite.DBData.Tables;

import Database.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.Orderable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import static ThothCore.ThothLite.StructureDescription.Incomes.*;

public class Incomes
        extends Data<Orderable> {

    public Incomes() {
        super();
        setName(TABLE_NAME);
    }

    @Override
    public HashMap<String, Object> convertToMap(Identifiable identifiable) {
        return null;
    }

    @Override
    public void readTable(List<HashMap<String, Object>> data) {
        for(HashMap<String, Object> row : data){

        }
    }

    @Override
    public void readTable(ResultSet resultSet) {

    }

    @Override
    public void readTableWithTableColumn(List<HashMap<TableColumn, Object>> data) {

    }
}
