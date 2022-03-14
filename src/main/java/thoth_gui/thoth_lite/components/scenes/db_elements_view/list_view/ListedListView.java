package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_view;

import javafx.beans.value.ObservableValue;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Identifiable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Typable;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortBy;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortPane;
import thoth_gui.thoth_lite.components.scenes.db_elements_view.list_cell.IdentifiableListCell;
import thoth_gui.thoth_lite.components.scenes.db_elements_view.list_cell.RemoveItemFromList;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.skin.VirtualFlow;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ListedListView
        extends IdentifiablesListView<Typable>
        implements RemoveItem {

    private enum SORT_BY implements SortBy {
        SORT_BY_UP("sort_by_up"),
        SORT_BY_DOWN("sort_by_down");
        private String sortBy;

        SORT_BY(String sortBy) {
            this.sortBy = sortBy;
        }

        @Override
        public String getSortName() {
            return sortBy;
        }
    }

    private final ScheduleTask scheduleTask = new ScheduleTask(this);
    private final ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(1);

    protected ListedListView(
            List<Typable> datas
            , AvaliableTables table
    ) {
        super(datas, table);
        identifiableElementList.getItems().addListener((ListChangeListener<? super Typable>) change -> {
            poolExecutor.schedule(scheduleTask, 250, TimeUnit.MILLISECONDS);
        });
    }

    @Override
    protected SortPane getSortPane() {
        sortPane = SortPane.getInstance()
                .setSortItems(SORT_BY.values())
                .setCell()
                .setSortMethod(this::sort)
                .setValue(SORT_BY.SORT_BY_UP)
        ;
        return sortPane;
    }

    @Override
    protected void sort(ObservableValue<? extends SortBy> observableValue, SortBy sortBy, SortBy sortBy1) {
        ObservableList<Typable> items = identifiableElementList.getItems();
        SORT_BY t1 = (SORT_BY) sortBy1;
        switch (t1) {
            case SORT_BY_UP: {
                items.sort((o1, o2) -> o1.getValue().compareTo(o2.getValue()));
                break;
            }
            case SORT_BY_DOWN: {
                items.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
                break;
            }
        }
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
        Typable typableInstance = new Typable() {

            private String id = "new";
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
        ObservableList<Typable> items = identifiableElementList.getItems();
        items.add(typableInstance);
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
                    IdentifiableListCell<Typable> cell2 = (IdentifiableListCell<Typable>) cell1.getCell(i);
                    RemoveItemFromList viewCell = (RemoveItemFromList) cell2.getView();

                    if (viewCell.hasRemoveItem()) continue;

                    viewCell.setRemoveItem(listedListView::removeItem);
                }
            }
        }

    }

}
