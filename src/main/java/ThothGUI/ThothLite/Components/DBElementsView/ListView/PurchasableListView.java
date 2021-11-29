package ThothGUI.ThothLite.Components.DBElementsView.ListView;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Purchasable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothGUI.ThothLite.Components.DBElementsView.ListView.IdentifiablesListView;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import layout.basepane.HBox;

import java.util.List;

public class PurchasableListView extends IdentifiablesListView<Purchasable> {

    private enum SORT_BY {
        ID_UP("sort_by_id_up"),
        ID_DOWN("sort_by_id_down"),
        DATE_UP("sort_by_date_up"),
        DATE_DOWN("sort_by_date_down"),
        DELIVERED("sort_by_delivered");
        private String id;
        SORT_BY(String id) {
            this.id = id;
        }
        public String toString(){
            return this.id;
        }
    }

    public PurchasableListView(List<Purchasable> datas) {
        super(datas, AvaliableTables.PURCHASABLE);
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
            ObservableList<Purchasable> items = identifiableElementList.getItems();
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
