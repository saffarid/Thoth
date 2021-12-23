package ThothGUI.thoth_lite.components.db_elements_view.list_view;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothGUI.thoth_lite.components.db_elements_view.list_cell.IdentifiableListCell;
import ThothGUI.thoth_lite.components.db_elements_view.list_cell.RemoveItemFromList;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.skin.VirtualFlow;
import layout.basepane.HBox;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ListedListView
        extends IdentifiablesListView<Listed>
        implements RemoveItem {

    private enum SORT_BY{
        SORT_BY_UP("sort_by_up"),
        SORT_BY_DOWN("sort_by_down");
        private String sortBy;
        SORT_BY(String sortBy) {
            this.sortBy = sortBy;
        }
        public String getSortBy() {
            return sortBy;
        }
    }

    private final ScheduleTask scheduleTask;

    protected ListedListView(
            List<Listed> datas
            , AvaliableTables table
    ) {
        super(datas, table);

        scheduleTask = new ScheduleTask(this);
        ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(1);
        poolExecutor.schedule(scheduleTask, 300, TimeUnit.MILLISECONDS);

        identifiableElementList.getItems().addListener((ListChangeListener<? super Listed>) change -> {
            poolExecutor.schedule(scheduleTask, 250, TimeUnit.MILLISECONDS);
        });
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
            ObservableList<Listed> items = identifiableElementList.getItems();
            switch (t1){
                case SORT_BY_UP:{
                    items.sort((o1, o2) -> o1.getValue().compareTo(o2.getValue()));
                    break;
                }
                case SORT_BY_DOWN:{
                    items.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
                    break;
                }
            }
        });

        sortedBox.setValue(SORT_BY.SORT_BY_UP);

        return sortedPane;
    }

    @Override
    public void removeItem(Identifiable identifiable) {
        if (identifiableElementList.getItems().contains(identifiable)) {

            identifiableElementList.setCellFactory(null);
            identifiableElementList.getItems().remove(identifiable);
            identifiableElementList.setCellFactory(tListView -> new IdentifiableListCell(this.table));
        }
    }

    @Override
    protected void openCreateNewIdentifiable(ActionEvent event) {
        Listed listedInstance = new Listed() {

            private String id = "-1";
            private String value = "default";

            @Override
            public String getValue() {
                return value;
            }

            @Override
            public void setValue(String value) {
                this.value = value;
            }

            @Override
            public String getId() {
                return id;
            }

            @Override
            public void setId(String id) {
            }
        };
        ObservableList<Listed> items = identifiableElementList.getItems();
        items.add(listedInstance);
    }

    private class ScheduleTask implements Runnable {

        private ListedListView listedListView;

        public ScheduleTask(ListedListView listedListView) {
            this.listedListView = listedListView;
        }

        @Override
        public void run() {
            for (Node cell : identifiableElementList.getChildrenUnmodifiable()) {
                VirtualFlow cell1 = (VirtualFlow) cell;
                for (int i = 0; i < cell1.getCellCount(); i++) {
                    IdentifiableListCell<Listed> cell2 = (IdentifiableListCell<Listed>) cell1.getCell(i);
                    RemoveItemFromList viewCell = (RemoveItemFromList) cell2.getView();
                    if(!viewCell.hasRemoveItem()) {
                        viewCell.setRemoveItem(listedListView::removeItem);
                    }
                }
            }
        }

    }

    private class SortedCell
            extends ListCell<SORT_BY> {
        @Override
        protected void updateItem(SORT_BY sort_by, boolean b) {
            if (sort_by != null) {
                super.updateItem(sort_by, b);
                setText(sort_by.sortBy);
            }
        }
    }

}
