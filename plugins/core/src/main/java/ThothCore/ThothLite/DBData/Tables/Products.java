package ThothCore.ThothLite.DBData.Tables;

import Database.Column.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.Currency;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.Product;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;
import ThothCore.ThothLite.Exceptions.NotContainsException;
import ThothCore.ThothLite.DBLiteStructure.FullStructure.StructureDescription;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static ThothCore.ThothLite.DBLiteStructure.FullStructure.StructureDescription.Products.*;

public class Products
        extends Data<Storagable>
{

    public Products() {
        super();
        setName(TABLE_NAME);
    }

    @Override
    public List<HashMap<String, Object>> convertToMap(List<? extends Identifiable> identifiable) {
        List<HashMap<String, Object>> res = new LinkedList<>();

        for (Identifiable data : identifiable) {
            Storagable storagable = (Storagable) data;

            HashMap<String, Object> map = new HashMap<>();
            map.put(ARTICLE, storagable.getId());
            map.put(NAME, storagable.getName());
            map.put(PRODUCT_TYPE_ID, storagable.getType().getValue());
            map.put(COUNT, storagable.getCount());
            map.put(COUNT_TYPE_ID, storagable.getCountType().getValue());
            map.put(ADRESS, storagable.getAdress().getValue());

            res.add(map);
        }

        return res;
    }

    @Override
    public void readTable(List<HashMap<String, Object>> data) {
        for(HashMap<String, Object> row : data){
            try {
                addData(
                        new Product(
                                (String) row.get(ARTICLE),
                                (String) row.get(NAME),
                                (Listed) getFromTableById(StructureDescription.ProductTypes.TABLE_NAME, String.valueOf(row.get(PRODUCT_TYPE_ID))),
                                Double.parseDouble( String.valueOf(row.get(COUNT) ) ),
                                (Listed) getFromTableById(StructureDescription.CountTypes.TABLE_NAME, String.valueOf(row.get(COUNT_TYPE_ID))),
                                (Listed) getFromTableById(StructureDescription.Storage.TABLE_NAME, String.valueOf(row.get(ADRESS)))
                        )
                );
            } catch (NotContainsException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void readTable(ResultSet resultSet) {

    }

    @Override
    public void readTableWithTableColumn(List<HashMap<TableColumn, Object>> data) {

    }
}

