package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_view;

import thoth_core.thoth_lite.db_data.db_data_element.properties.Orderable;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;

import java.util.List;

public class OrderableListView extends IdentifiablesListView<Orderable> {

    public OrderableListView(List<Orderable> datas) {
        super(datas, AvaliableTables.ORDERABLE);
    }

}
