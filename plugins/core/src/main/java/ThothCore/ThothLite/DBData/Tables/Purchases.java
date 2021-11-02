package ThothCore.ThothLite.DBData.Tables;

import Database.Column.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.*;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Nameable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Purchasable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storing;
import ThothCore.ThothLite.Exceptions.NotContainsException;
import ThothCore.ThothLite.StructureDescription;

import java.sql.ResultSet;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static ThothCore.ThothLite.StructureDescription.Purchases.*;

public class Purchases
        extends Data<Purchasable> {

    public Purchases() {
        super();
        setName(TABLE_NAME);
    }

    @Override
    public List<HashMap<String, Object>> convertToMap(List<? extends Identifiable> list) {
        List<HashMap<String, Object>> res = new LinkedList<>();

        for (Identifiable identifiable : list) {
            Purchasable purchasable = (Purchasable) identifiable;

            for (Storing storing : purchasable.getComposition()) {
                HashMap<String, Object> map = new HashMap<>();

                map.put(ORDER_ID, purchasable.getId());
                map.put(STORE_ID, purchasable.getPartner().getName());
                map.put(PRODUCT_ID, storing.getStoragable().getId());
                map.put(COUNT, storing.getCount());
                map.put(COUNT_TYPE_ID, storing.getCountType().getValue());
                map.put(DELIVERY_DATE, purchasable.finishDate().format(DateTimeFormatter.ISO_DATE));
                map.put(IS_DELIVERED, false);

                res.add(map);
            }
        }

        return res;
    }

    @Override
    public void readTable(List<HashMap<String, Object>> data) throws ParseException {
        for (HashMap<String, Object> row : data) {

            try {
                Purchasable purchase = getById((String) row.get(ORDER_ID));

                if (purchase == null) {

                    purchase = new Purchase(
                            String.valueOf(row.get(ORDER_ID)),
                            (Partner) getFromTableById(StructureDescription.Partners.TABLE_NAME, String.valueOf(row.get(STORE_ID))),
                            (String) row.get(DELIVERY_DATE),
                            (int) row.get(IS_DELIVERED) == Purchase.DELIVERED
                    );

                    addData(
                            purchase
                    );
                }

                Storing storing = purchase.getStoringByStoragableId(String.valueOf(row.get(PRODUCT_ID)));

                if (storing == null) {
                    storing = new StorageCell(
                            (Storagable) getFromTableById(StructureDescription.Products.TABLE_NAME, String.valueOf(row.get(PRODUCT_ID))),
                            (Double) row.get(COUNT),
                            (Listed) getFromTableById(StructureDescription.CountTypes.TABLE_NAME, String.valueOf(row.get(COUNT_TYPE_ID)))
                    );
                    purchase.addStoring(storing);
                }
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