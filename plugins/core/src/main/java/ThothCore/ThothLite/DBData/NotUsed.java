package ThothCore.ThothLite.DBData;

import Database.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.NotUse;
import ThothCore.ThothLite.DBData.DBDataElement.Product;
import ThothCore.ThothLite.DBLiteStructure.StructureDescription;
import ThothCore.ThothLite.TableReadable;

import java.sql.ResultSet;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import static ThothCore.ThothLite.DBLiteStructure.StructureDescription.NotUsed.*;

public class NotUsed
        extends Data<NotUse>
        implements TableReadable {

    public NotUsed() {
        super();
        name = TABLE_NAME;
    }

    @Override
    public void readTable(List<HashMap<String, Object>> data) throws ParseException {
        for(HashMap<String, Object> row : data){
            datas.add(
                    new NotUse(
                            (String) row.get(ID),
                            (Product) DBData.getInstance().getTable(StructureDescription.Products.TABLE_NAME)
                            .getById( (String) row.get(PRODUCT_ID) ),
                            (String) row.get(CAUSE)
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
