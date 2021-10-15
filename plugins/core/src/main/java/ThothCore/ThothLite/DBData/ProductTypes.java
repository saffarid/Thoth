package ThothCore.ThothLite.DBData;

import Database.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.ListElement;
import ThothCore.ThothLite.DBLiteStructure.StructureDescription;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import static ThothCore.ThothLite.DBLiteStructure.StructureDescription.ProductTypes.*;

public class ProductTypes
        extends Data<ListElement>
{

    public ProductTypes() {
        super();
        setName(TABLE_NAME);
    }

    @Override
    public void readTable(List<HashMap<String, Object>> data) {
        for(HashMap<String, Object> row : data){
            datas.add(
                    new ListElement(
                            String.valueOf(row.get(ID)),
                            (String) row.get(PRODUCT_TYPES)
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
