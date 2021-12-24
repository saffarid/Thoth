package thoth_gui.thoth_lite.components.db_elements_view.list_view;

import thoth_core.thoth_lite.db_data.db_data_element.properties.Storagable;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import layout.basepane.HBox;

import java.util.List;

public class StoragableListView extends IdentifiablesListView<Storagable> {

    private enum SORT_BY {
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
    }


    public StoragableListView(List<Storagable> datas) {
        super(datas, AvaliableTables.STORAGABLE);
    }

    @Override
    protected Node createSortedPane() {
        HBox sortedPane = (HBox) super.createSortedPane();

        ComboBox<SORT_BY> sortedBox = (ComboBox<SORT_BY>) sortedPane.getChildren()
                .stream()
                .filter(node -> node.getId() != null)
                .filter(node -> node.getId().equals(Ids.SORTED_BOX.toString()))
                .findFirst()
                .get();

        for(SORT_BY sort : SORT_BY.values()){
            sortedBox.getItems().add(sort);
        }

        sortedBox.setCellFactory(sort_byListView -> new SortedCell());
        sortedBox.setButtonCell(new SortedCell());

        sortedBox.valueProperty().addListener((observableValue, sort_by, t1) -> {
            ObservableList<Storagable> items = identifiableElementList.getItems();
            switch (t1){
                case ID_UP:{
                    items.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
                    break;
                }
                case ID_DOWN:{
                    items.sort((o1, o2) -> o2.getId().compareTo(o1.getId()));
                    break;
                }
                case NAME_UP:{
                    items.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
                    break;
                }
                case NAME_DOWN:{
                    items.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
                    break;
                }
                case TYPE_UP:{
                    items.sort((o1, o2) -> o1.getType().getValue().compareTo(o2.getType().getValue()));
                    break;
                }
                case TYPE_DOWN:{
                    items.sort((o1, o2) -> o2.getType().getValue().compareTo(o1.getType().getValue()));
                    break;
                }
            }
        });

        sortedBox.setValue(SORT_BY.ID_UP);

        return sortedPane;
    }

    private class SortedCell
            extends ListCell<SORT_BY> {
        @Override
        protected void updateItem(SORT_BY sort_by, boolean b) {
            if (sort_by != null) {
                super.updateItem(sort_by, b);
                setText(sort_by.id);
            }
        }
    }

}
