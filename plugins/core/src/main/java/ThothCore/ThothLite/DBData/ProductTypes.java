package ThothCore.ThothLite.DBData;

import Database.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.ListElement;
import ThothCore.ThothLite.DBLiteStructure.StructureDescription;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

public class ProductTypes
        extends Data<ListElement>
        implements TableReadable {

    public ProductTypes() {
        super();
        name = StructureDescription.ProductTypes.TABLE_NAME;
    }

    @Override
    public void readTable(List<HashMap<String, Object>> data) {
        for(HashMap<String, Object> row : data){
            datas.add(
                    new ListElement(
                            String.valueOf(row.get(StructureDescription.ProductTypes.ID)),
                            (String) row.get(StructureDescription.ProductTypes.PRODUCT_TYPES)
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
