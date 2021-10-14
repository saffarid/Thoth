package ThothCore.ThothLite.DBData;

import Database.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.ListElement;
import ThothCore.ThothLite.DBData.DBDataElement.Product;
import ThothCore.ThothLite.DBData.DBDataElement.StorageCell;
import ThothCore.ThothLite.DBLiteStructure.StructureDescription;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import static ThothCore.ThothLite.DBLiteStructure.StructureDescription.Storage.*;

public class Storage
        extends Data<StorageCell>
        implements TableReadable {

    public Storage() {
        super();
        name = TABLE_NAME;
    }

    @Override
    public void readTable(List<HashMap<String, Object>> data) {
        for (HashMap<String, Object> row : data) {
            DBData dbData = DBData.getInstance();
            datas.add(
                    new StorageCell(
                            (Product) dbData.getTable(StructureDescription.Products.TABLE_NAME)
                                    .getById( String.valueOf(row.get(PRODUCT_ID)) ),
                            (Double) row.get(COUNT),
                            (ListElement) dbData.getTable(StructureDescription.CountTypes.TABLE_NAME)
                                    .getById( String.valueOf(row.get(COUNT_TYPE_ID)) )
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
