package ThothCore.ThothLite.DBData;

import Database.TableColumn;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.Currency;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import static ThothCore.ThothLite.DBLiteStructure.StructureDescription.Currency.*;

public class Currencies
        extends Data<Currency>
{

    public Currencies() {
        super();
        setName(TABLE_NAME);
    }

    @Override
    public void readTable(List<HashMap<String, Object>> data) {
        for(HashMap<String, Object> row : data){
            datas.add(new Currency(
                    String.valueOf(row.get(ID)),
                    (String) row.get(CURRENCY),
                    (Double) row.get(COURSE)
            ));
        }
    }

    @Override
    public void readTable(ResultSet resultSet) {

    }

    @Override
    public void readTableWithTableColumn(List<HashMap<TableColumn, Object>> data) {

    }
}
