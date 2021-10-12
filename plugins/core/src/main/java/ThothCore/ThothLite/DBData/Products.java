package ThothCore.ThothLite.DBData;

import Database.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.Currency;
import ThothCore.ThothLite.DBData.DBDataElement.ListElement;
import ThothCore.ThothLite.DBData.DBDataElement.Product;
import ThothCore.ThothLite.DBLiteStructure.StructureDescription;
import ThothCore.ThothLite.TableReadable;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import static ThothCore.ThothLite.DBLiteStructure.StructureDescription.Products.*;

public class Products
        extends Data<Product>
        implements TableReadable {

    public Products() {
        super();
        name = TABLE_NAME;
    }

    @Override
    public void readTable(List<HashMap<String, Object>> data) {
        for(HashMap<String, Object> row : data){
            DBData dbData = DBData.getInstance();
            datas.add(
                    new Product(
                            String.valueOf(row.get(ID)),
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

