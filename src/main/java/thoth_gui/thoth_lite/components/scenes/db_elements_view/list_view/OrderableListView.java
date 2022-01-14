package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_view;

import javafx.beans.value.ObservableValue;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Orderable;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortBy;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortPane;

import java.util.List;

public class OrderableListView extends IdentifiablesListView<Orderable> {

    public OrderableListView(List<Orderable> datas) {
        super(datas, AvaliableTables.ORDERABLE);
    }

    @Override
    protected SortPane getSortPane() {
        return null;
    }

    @Override
    protected void sort(ObservableValue<? extends SortBy> observableValue, SortBy sortBy, SortBy sortBy1) {

    }
}
