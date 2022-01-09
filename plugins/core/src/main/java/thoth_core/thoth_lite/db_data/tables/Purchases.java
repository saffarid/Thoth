package thoth_core.thoth_lite.db_data.tables;

import database.Column.TableColumn;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Typable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Identifiable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Purchasable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Storagable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Storing;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_core.thoth_lite.db_lite_structure.full_structure.StructureDescription;
import thoth_core.thoth_lite.db_data.db_data_element.implement.Currency;
import thoth_core.thoth_lite.db_data.db_data_element.implement.Partner;
import thoth_core.thoth_lite.db_data.db_data_element.implement.Purchase;
import thoth_core.thoth_lite.db_data.db_data_element.implement.StorageCell;

import java.sql.ResultSet;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static thoth_core.thoth_lite.db_lite_structure.full_structure.StructureDescription.Purchases.*;

public class Purchases
        extends Data<Purchasable> {

    public Purchases() {
        super();
        setName(TABLE_NAME);
    }

    @Override
    public HashMap<String, List<HashMap<String, Object>>> convertToMap(List<? extends Identifiable> list) {
        HashMap<String, List<HashMap<String, Object>>> res = new HashMap<>();
        List<HashMap<String, Object>> datasDesc = new LinkedList<>();
        List<HashMap<String, Object>> datasComposition = new LinkedList<>();

        for( Identifiable identifiable : list){
            Purchasable purchasable = (Purchasable) identifiable;

            HashMap<String, Object> description = new HashMap<>();

            //Заполнение карты описания
            description.put(StructureDescription.PurchaseDesc.PURCHASE_ID, purchasable.getId());
            description.put(StructureDescription.PurchaseDesc.STORE_ID, purchasable.getPartner().getName());
            description.put(StructureDescription.PurchaseDesc.DELIVERY_DATE, purchasable.finishDate().format(DateTimeFormatter.ISO_DATE));
            description.put(StructureDescription.PurchaseDesc.IS_DELIVERED, purchasable.isDelivered()?1:0);
            datasDesc.add(description);
            //Заполнение карты состава
            for (Storing storing : purchasable.getComposition()){
                HashMap<String, Object> composition = new HashMap<>();
                composition.put(StructureDescription.PurchaseComposition.PURCHASE_ID, purchasable.getId());
                composition.put(StructureDescription.PurchaseComposition.PRODUCT_ID, storing.getStoragable().getId());
                composition.put(StructureDescription.PurchaseComposition.COUNT, storing.getCount());
                composition.put(StructureDescription.PurchaseComposition.COUNT_TYPE_ID, storing.getCountType().getValue());
                composition.put(StructureDescription.PurchaseComposition.PRICE, storing.getPrice());
                composition.put(StructureDescription.PurchaseComposition.CURRENCY_ID, storing.getCurrency().getCurrency());
                datasComposition.add(composition);
            }
        }
        res.put(StructureDescription.PurchaseDesc.TABLE_NAME, datasDesc);
        res.put(StructureDescription.PurchaseComposition.TABLE_NAME, datasComposition);
        return res;
    }

    @Override
    public void readTable(StructureDescription.TableTypes tableType, List<HashMap<String, Object>> data) throws ParseException {
        datas.clear();
        for (HashMap<String, Object> row : data) {

            Purchasable purchase = null;
            try {
                purchase = getById((String) row.get(ORDER_ID));
            } catch (NotContainsException e) {
                try {
                    purchase = new Purchase(
                            String.valueOf(row.get(ORDER_ID)),
                            (Partner) getFromTableById(StructureDescription.Partners.TABLE_NAME, String.valueOf(row.get(STORE_ID))),
                            LocalDate.parse( (String) row.get(DELIVERY_DATE) ),
                            (int) row.get(IS_DELIVERED) == Purchase.DELIVERED
                    );

                    addData(
                            purchase
                    );
                } catch (NotContainsException notContainsException) {
                    notContainsException.printStackTrace();
                }
            }

            try {
                Storing storing = purchase.getStoringByStoragableId(String.valueOf(row.get(PRODUCT_ID)));

                if (storing == null) {
                    storing = new StorageCell(
                            String.valueOf( row.get(ID) ), //Идентификатор
                            (Storagable) getFromTableById(                          //
                                    StructureDescription.Products.TABLE_NAME        //Продукт
                                    , String.valueOf(row.get(PRODUCT_ID))           //
                            ),
                            (Double) row.get(COUNT),                                //Кол-во
                            (Typable) getFromTableById(                              //
                                    StructureDescription.CountTypes.TABLE_NAME      //Единица измерения кол-ва
                                    , String.valueOf( row.get(COUNT_TYPE_ID) )      //
                            ),
                            (Double) row.get(PRICE),
                            (Currency) getFromTableById(
                                    StructureDescription.Currency.TABLE_NAME
                                    , String.valueOf( row.get(CURRENCY_ID) )
                            )
                    );
                    purchase.addStoring(storing);
                }
            } catch (NotContainsException e) {
                e.printStackTrace();
            }
        }

        publisher.submit(datas);
    }

    @Override
    public void readTable(StructureDescription.TableTypes tableType, ResultSet resultSet) {

    }

    @Override
    public void readTableWithTableColumn(StructureDescription.TableTypes tableType, List<HashMap<TableColumn, Object>> data) {

    }
}
