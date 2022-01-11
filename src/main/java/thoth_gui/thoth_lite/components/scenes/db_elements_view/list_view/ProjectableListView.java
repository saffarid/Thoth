package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_view;

import thoth_core.thoth_lite.db_data.db_data_element.properties.Projectable;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;

import java.util.List;

public class ProjectableListView extends IdentifiablesListView<Projectable> {

    public ProjectableListView(List<Projectable> datas) {
        super(datas, AvaliableTables.PROJECTABLE);
    }

}
