package ThothCore.ThothLite.DBData;

import Database.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.ListElement;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.Partner;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.Product;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.Purchase;
import ThothCore.ThothLite.DBData.DBDataElement.Purchasable;
import ThothCore.ThothLite.DBLiteStructure.StructureDescription;

import java.sql.ResultSet;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static ThothCore.ThothLite.DBLiteStructure.StructureDescription.Purchases.*;

public class Purchases
        extends Data<Purchasable>
{

    public Purchases() {
        super();
        setName(TABLE_NAME);
    }

    @Override
    public void readTable(List<HashMap<String, Object>> data) throws ParseException {
        for (HashMap<String, Object> row : data) {

            Purchasable purchase = getById((String) row.get(ORDER_ID));

            if (purchase == null) {
                purchase = new Purchase(
                        String.valueOf(row.get(ORDER_ID)),
                        (Partner) DBData.getInstance().getTable(StructureDescription.Partners.TABLE_NAME)
                                .getById(String.valueOf(row.get(STORE_ID))),
                        new Date((Long) row.get(DELIVERY_DATE)),
                        (int) row.get(IS_DELIVERED) == Purchase.DELIVERED
                );

                datas.add(
                        purchase
                );
            }

            //Реализовать добавление продуктов в список

        }

    }

    @Override
    public void readTable(ResultSet resultSet) {

    }

    @Override
    public void readTableWithTableColumn(List<HashMap<TableColumn, Object>> data) {

    }
}
