package thoth_core.thoth_lite.db_data.tables;

import database.Column.TableColumn;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Projectable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Identifiable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Storagable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Storing;
import thoth_core.thoth_lite.db_lite_structure.full_structure.StructureDescription;

import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static thoth_core.thoth_lite.db_lite_structure.full_structure.StructureDescription.ProjectsDesc.*;

public class Projects
        extends Data<Projectable> {
    public Projects() {
        super();
        setName(TABLE_NAME);
    }

    @Override
    public HashMap<String, List<HashMap<String, Object>>> convertToMap(List<? extends Identifiable> list) {
        HashMap<String, List<HashMap<String, Object>>> res = new HashMap<>();
        List<HashMap<String, Object>> datasDesc = new LinkedList<>();
        List<HashMap<String, Object>> datasComposition = new LinkedList<>();

        for (Identifiable identifiable : list){
            Projectable projectable = (Projectable) identifiable;
            HashMap<String, Object> description = new HashMap<>();
            //Заполнение карты описания
            description.put(NAME, projectable.getName());
            description.put(DATE, projectable.startDate().format(DateTimeFormatter.ISO_DATE));
            datasDesc.add(description);
            //Заполнение карты состава
            for(Storing storing : projectable.getComposition()){
                HashMap<String, Object> composition = new HashMap<>();
                composition.put(StructureDescription.ProjectsComposition.PRODUCT_ID, storing.getStoragable().getId());
                composition.put(StructureDescription.ProjectsComposition.COUNT, storing.getCount());
                composition.put(StructureDescription.ProjectsComposition.COUNT_TYPE_ID, storing.getCountType().getValue());
                datasComposition.add(composition);
            }
        }

        return res;
    }

    @Override
    public void readTable(StructureDescription.TableTypes tableType, List<HashMap<String, Object>> data) {
        datas.clear();
        publisher.submit(datas);
    }

    @Override
    public void readTable(StructureDescription.TableTypes tableType, ResultSet resultSet) {

    }

    @Override
    public void readTableWithTableColumn(StructureDescription.TableTypes tableType, List<HashMap<TableColumn, Object>> data) {

    }
}
