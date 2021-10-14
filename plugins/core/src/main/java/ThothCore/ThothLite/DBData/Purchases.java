package ThothCore.ThothLite.DBData;

import Database.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.ListElement;
import ThothCore.ThothLite.DBData.DBDataElement.Partner;
import ThothCore.ThothLite.DBData.DBDataElement.Product;
import ThothCore.ThothLite.DBData.DBDataElement.Purchase;
import ThothCore.ThothLite.DBLiteStructure.StructureDescription;

import java.sql.ResultSet;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static ThothCore.ThothLite.DBLiteStructure.StructureDescription.Purchases.*;

public class Purchases
        extends Data<Purchase>
        implements TableReadable {

    public Purchases() {
        super();
        name = TABLE_NAME;
    }

    @Override
    public void readTable(List<HashMap<String, Object>> data) throws ParseException {
        for (HashMap<String, Object> row : data) {

            Purchase purchase = getById((String) row.get(ORDER_ID));

            if (purchase == null) {
                purchase = new Purchase(
                        String.valueOf(row.get(ORDER_ID)),
                        (Partner) DBData.getInstance().getTable(StructureDescription.Partners.TABLE_NAME)
                                .getById( String.valueOf( row.get(STORE_ID)) ),
                        new Date((Long) row.get(DELIVERY_DATE)),
                        (int) row.get(IS_DELIVERED) == Purchase.DELIVERED
                );

                datas.add(
                        purchase
                );
            }

            purchase.addProduct(
                    (Product) DBData.getInstance().getTable(StructureDescription.Products.TABLE_NAME)
                            .getById( String.valueOf(row.get(PRODUCT_ID)) ),
                    (Double) row.get(COUNT),
                    (ListElement) DBData.getInstance().getTable(StructureDescription.CountTypes.TABLE_NAME)
                            .getById( String.valueOf(row.get(COUNT_TYPE_ID)) )
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
