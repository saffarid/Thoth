package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_view;

import javafx.beans.value.ObservableValue;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Storagable;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import javafx.collections.ObservableList;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortBy;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortCell;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortPane;

import java.util.List;

public class StoragableListView extends IdentifiablesListView<Storagable> {

    private enum SORT_BY implements SortBy {
        ID_UP("sort_by_id_up"),
        ID_DOWN("sort_by_id_down"),
        NAME_UP("sort_by_name_up"),
        NAME_DOWN("sort_by_name_down"),
        TYPE_UP("sort_by_type_up"),
        TYPE_DOWN("sort_by_type_down");
        private String id;
        SORT_BY(String id) {
            this.id = id;
        }
        @Override
        public String getSortName() {
            return id;
        }
    }

    public StoragableListView(List<Storagable> datas) {
        super(datas, AvaliableTables.STORAGABLE);
    }

    @Override
    protected SortPane getSortPane() {
        sortPane = SortPane.getInstance()
                .setSortItems(SORT_BY.values())
                .setCell()
                .setSortMethod(this::sort)
                .setValue(SORT_BY.ID_UP)
        ;
        return sortPane;
    }

    @Override
    protected void sort(ObservableValue<? extends SortBy> observableValue, SortBy sortBy, SortBy sortBy1) {
        ObservableList<Storagable> items = identifiableElementList.getItems();
        SORT_BY sortBy2 = (SORT_BY) sortBy1;
        switch (sortBy2) {
            case ID_UP: {
                items.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
                break;
            }
            case ID_DOWN: {
                items.sort((o1, o2) -> o2.getId().compareTo(o1.getId()));
                break;
            }
            case NAME_UP: {
                items.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
                break;
            }
            case NAME_DOWN: {
                items.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
                break;
            }
            case TYPE_UP: {
                items.sort((o1, o2) -> o1.getType().getValue().compareTo(o2.getType().getValue()));
                break;
            }
            case TYPE_DOWN: {
                items.sort((o1, o2) -> o2.getType().getValue().compareTo(o1.getType().getValue()));
                break;
            }
        }
    }

}
