package thoth_core.thoth_lite.db_data.tables;

import database.Column.TableColumn;
import thoth_core.thoth_lite.db_data.db_data_element.implement.Partner;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Partnership;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Identifiable;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static thoth_core.thoth_lite.db_lite_structure.full_structure.StructureDescription.Partners.*;


public class Partners
        extends Data<Partnership>
{

    public Partners() {
        super();
        setName(TABLE_NAME);
    }

    @Override
    public List<HashMap<String, Object>> convertToMap(List<? extends Identifiable> list){
        List<HashMap<String, Object>> res = new LinkedList<>();
        for(Identifiable identifiable : list){
            HashMap<String, Object> map = new HashMap<>();
            Partnership partner = (Partnership) identifiable;
            map.put(ID, partner.getId());
            map.put(NAME, partner.getName());
            map.put(PHONE, partner.getPhone());
            map.put(WEB, partner.getWeb());
            res.add(map);
        }
        return res;
    }

    @Override
    public void readTable(List<HashMap<String, Object>> data) {
        datas.clear();
        for(HashMap<String, Object> row : data){
            datas.add(new Partner(
                    String.valueOf(row.get(ID)),
                    (String) row.get(NAME),
                    (String) row.get(PHONE),
                    (String) row.get(WEB)
            ));
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
