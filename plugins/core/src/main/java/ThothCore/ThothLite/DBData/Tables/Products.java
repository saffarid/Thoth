package ThothCore.ThothLite.DBData.Tables;

import Database.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.Currency;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.Product;
import ThothCore.ThothLite.DBData.DBDataElement.Listed;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Storagable;
import ThothCore.ThothLite.StructureDescription;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import static ThothCore.ThothLite.StructureDescription.Products.*;

public class Products
        extends Data<Storagable>
{

    public Products() {
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
            addData(
                    new Product(
                            (String) row.get(ARTICLE),
                            (String) row.get(NAME),
                            (Listed) getFromTableById(StructureDescription.ProductTypes.TABLE_NAME, String.valueOf(row.get(PRODUCT_TYPE_ID))),
                            (Double) row.get(PRICE),
                            (Currency) getFromTableById(StructureDescription.Currency.TABLE_NAME, String.valueOf(row.get(CURRENCY_ID))),
                            (String) row.get(NOTE)
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

