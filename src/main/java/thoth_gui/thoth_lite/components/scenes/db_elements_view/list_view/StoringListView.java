package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_view;

import javafx.beans.value.ObservableValue;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Storing;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortBy;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortPane;

import java.util.List;

public class StoringListView extends IdentifiablesListView<Storing> {

    public StoringListView(List<Storing> datas) {
        super(datas, AvaliableTables.STORING);
    }


    @Override
    protected SortPane getSortPane() {
        return null;
    }

    @Override
    protected void sort(ObservableValue<? extends SortBy> observableValue, SortBy sortBy, SortBy sortBy1) {

    }
}
