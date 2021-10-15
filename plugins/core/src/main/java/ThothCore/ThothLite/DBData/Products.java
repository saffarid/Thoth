package ThothCore.ThothLite.DBData;

import Database.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.Currency;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.ListElement;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.Product;
import ThothCore.ThothLite.DBData.DBDataElement.Storagable;
import ThothCore.ThothLite.DBLiteStructure.StructureDescription;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import static ThothCore.ThothLite.DBLiteStructure.StructureDescription.Products.*;

public class Products
        extends Data<Storagable>
{

    public Products() {
        super();
        setName(TABLE_NAME);
    }

    @Override
    public void readTable(List<HashMap<String, Object>> data) {
        for(HashMap<String, Object> row : data){
            DBData dbData = DBData.getInstance();
            datas.add(
                    new Product(
                            (String) row.get(ARTICLE),
                            (String) row.get(NAME),
                            (ListElement) dbData.getTable(StructureDescription.ProductTypes.TABLE_NAME)
                                    .getById(String.valueOf(row.get(PRODUCT_TYPE_ID))),
                            (Double) row.get(PRICE),
                            (Currency) dbData.getTable(StructureDescription.Currency.TABLE_NAME)
                                    .getById(String.valueOf(row.get(CURRENCY_ID))),
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

