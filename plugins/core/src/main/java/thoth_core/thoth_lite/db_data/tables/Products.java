package thoth_core.thoth_lite.db_data.tables;

import database.Column.TableColumn;
import thoth_core.thoth_lite.db_data.db_data_element.implement.Product;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Listed;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Identifiable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Storagable;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_core.thoth_lite.db_lite_structure.full_structure.StructureDescription;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Products
        extends Data<Storagable>
{

    public Products() {
        super();
        setName(StructureDescription.Products.TABLE_NAME);
    }

    @Override
    public List<HashMap<String, Object>> convertToMap(List<? extends Identifiable> identifiable) {
        List<HashMap<String, Object>> res = new LinkedList<>();

        for (Identifiable data : identifiable) {
            Storagable storagable = (Storagable) data;

            HashMap<String, Object> map = new HashMap<>();
            map.put(StructureDescription.Products.ARTICLE, storagable.getId());
            map.put(StructureDescription.Products.NAME, storagable.getName());
            map.put(StructureDescription.Products.PRODUCT_TYPE_ID, storagable.getType().getValue());
            map.put(StructureDescription.Products.COUNT, storagable.getCount());
            map.put(StructureDescription.Products.COUNT_TYPE_ID, storagable.getCountType().getValue());
            map.put(StructureDescription.Products.ADRESS, storagable.getAdress().getValue());

            res.add(map);
        }

        return res;
    }

    @Override
    public void readTable(List<HashMap<String, Object>> data) {
        datas.clear();
        for(HashMap<String, Object> row : data){
            try {
                addData(
                        new Product(
                                (String) row.get(StructureDescription.Products.ARTICLE),
                                (String) row.get(StructureDescription.Products.NAME),
                                (Listed) getFromTableById(StructureDescription.ProductTypes.TABLE_NAME, String.valueOf(row.get(StructureDescription.Products.PRODUCT_TYPE_ID))),
                                Double.parseDouble( String.valueOf(row.get(StructureDescription.Products.COUNT) ) ),
                                (Listed) getFromTableById(StructureDescription.CountTypes.TABLE_NAME, String.valueOf(row.get(StructureDescription.Products.COUNT_TYPE_ID))),
                                (Listed) getFromTableById(StructureDescription.Storage.TABLE_NAME, String.valueOf(row.get(StructureDescription.Products.ADRESS))),
                                (String) row.get(StructureDescription.Products.NOTE)
                        )
                );
            } catch (NotContainsException e) {
                e.printStackTrace();
            }
        }
        publisher.submit(datas);
    }

    @Override
    public void readTable(ResultSet resultSet) {

    }

    @Override
    public void readTableWithTableColumn(List<HashMap<TableColumn, Object>> data) {

    }
}

