package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_view;

import javafx.beans.value.ObservableValue;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Purchasable;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import javafx.collections.ObservableList;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortBy;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortPane;
import thoth_gui.thoth_lite.components.scenes.Scenes;

import java.util.List;

public class PurchasableListView extends IdentifiablesListView<Purchasable> {

    private enum SORT_BY implements SortBy{
        ID_UP("sort_by_id_up"),
        ID_DOWN("sort_by_id_down"),
        DATE_UP("sort_by_date_up"),
        DATE_DOWN("sort_by_date_down"),
        DELIVERED("sort_by_delivered");
        private String id;
        SORT_BY(String id) {
            this.id = id;
        }

        @Override
        public String getSortName() {
            return id;
        }
    }

    public PurchasableListView(List<Purchasable> datas) {
        super(datas, AvaliableTables.PURCHASABLE);
        this.id = Scenes.PURCHASABLE.name();
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
        ObservableList<Purchasable> items = identifiableElementList.getItems();
        SORT_BY t1 = (SORT_BY) sortBy1;
        switch (t1){
            case ID_UP:{
                items.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
                break;
            }
            case ID_DOWN:{
                items.sort((o1, o2) -> o2.getId().compareTo(o1.getId()));
                break;
            }
            case DATE_UP:{
                items.sort((o1, o2) -> o1.finishDate().compareTo(o2.finishDate()));
                break;
            }
            case DATE_DOWN:{
                items.sort((o1, o2) -> o2.finishDate().compareTo(o1.finishDate()));
                break;
            }
            case DELIVERED:{
                items.sort((o1, o2) -> Boolean.valueOf(o1.isDelivered()).compareTo( Boolean.valueOf(o2.isDelivered()) ));
                break;
            }
        }
    }

}
