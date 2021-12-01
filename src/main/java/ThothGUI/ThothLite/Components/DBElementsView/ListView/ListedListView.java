package ThothGUI.ThothLite.Components.DBElementsView.ListView;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothGUI.ThothLite.Components.DBElementsView.ListCell.IdentifiableListCell;
import ThothGUI.ThothLite.Components.DBElementsView.ListCell.ListedViewCell;
import ThothGUI.ThothLite.Components.DBElementsView.ListCell.RemoveItemFromList;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.skin.VirtualFlow;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class ListedListView
        extends IdentifiablesListView<Listed>
        implements RemoveItem {

    private final ScheduleTask scheduleTask;

    protected ListedListView(
            List<Listed> datas
            , AvaliableTables table
    ) {
        super(datas, table);

        scheduleTask = new ScheduleTask(this);
        ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(1);
        poolExecutor.schedule(scheduleTask, 250, TimeUnit.MILLISECONDS);

        identifiableElementList.getItems().addListener((ListChangeListener<? super Listed>) change -> {
            poolExecutor.schedule(scheduleTask, 250, TimeUnit.MILLISECONDS);
        });
    }

    @Override
    public void removeItem(Identifiable identifiable) {
        if (identifiableElementList.getItems().contains(identifiable)) {
            LOG.log(Level.INFO, "I have element");
            identifiableElementList.setCellFactory(null);
            identifiableElementList.getItems().remove(identifiable);
            identifiableElementList.setCellFactory(tListView -> new IdentifiableListCell(this.table));
        } else {
            LOG.log(Level.INFO, "No element");
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

}
