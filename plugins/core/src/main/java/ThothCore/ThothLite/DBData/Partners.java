package ThothCore.ThothLite.DBData;

import Database.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.Partner;
import ThothCore.ThothLite.DBLiteStructure.StructureDescription;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

public class Partners
        extends Data<Partner>
        implements TableReadable {

    public Partners() {
        super();
        name = StructureDescription.Partners.TABLE_NAME;
    }

    @Override
    public void readTable(List<HashMap<String, Object>> data) {
        for(HashMap<String, Object> row : data){
            datas.add(new Partner(
                    String.valueOf(row.get(StructureDescription.Partners.ID)),
                    (String) row.get(StructureDescription.Partners.NAME),
                    (String) row.get(StructureDescription.Partners.PHONE),
                    (String) row.get(StructureDescription.Partners.WEB)
            ));
        }
    }

    @Override
    public void readTable(ResultSet resultSet) {

    }

    @Override
    public void readTableWithTableColumn(List<HashMap<TableColumn, Object>> data) {

    }
}
