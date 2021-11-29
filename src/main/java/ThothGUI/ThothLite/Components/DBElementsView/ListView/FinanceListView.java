package ThothGUI.ThothLite.Components.DBElementsView.ListView;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Finance;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothGUI.ThothLite.Components.DBElementsView.ListCell.FinanceViewCell;
import ThothGUI.ThothLite.Components.DBElementsView.ListCell.IdentifiableListCell;
import ThothGUI.ThothLite.Components.DBElementsView.ListCell.RemoveItemFromList;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.skin.VirtualFlow;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class FinanceListView
        extends IdentifiablesListView<Finance>
        implements RemoveItem {

    private final ScheduleTask scheduleTask;

    protected FinanceListView(List<Finance> datas) {
        super(datas, AvaliableTables.CURRENCIES);

        scheduleTask = new ScheduleTask(this);
        ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(1);
        poolExecutor.schedule(scheduleTask, 1, TimeUnit.SECONDS);

        identifiableElementList.getItems().addListener((ListChangeListener<? super Finance>) change -> {
            poolExecutor.schedule(scheduleTask, 1, TimeUnit.SECONDS);
        });
    }

    @Override
    protected void openCreateNewIdentifiable(ActionEvent event) {
        Finance financeInstance = new Finance() {
            private String id = "-1";
            private String currency = "new Currency";
            private Double course = 1d;

            @Override
            public String getId() {
                return id;
            }

            @Override
            public void setId(String id) {
            }

            @Override
            public String getCurrency() {
                return currency;
            }

            @Override
            public void setCurrency(String currency) {
                this.currency = currency;
            }

            @Override
            public Double getCourse() {
                return course;
            }

            @Override
            public void setCourse(Double course) {
                this.course = course;
            }
        };
        identifiableElementList.getItems().add(financeInstance);
    }

    @Override
    public void removeItem(Identifiable identifiable) {
        if (identifiableElementList.getItems().contains(identifiable)) {
            LOG.log(Level.INFO, "I have element");
            identifiableElementList.setCellFactory(null);
            identifiableElementList.getItems().remove(identifiable);
//            identifiableElementList.refresh();
            identifiableElementList.setCellFactory(tListView -> new IdentifiableListCell(this.table));
        } else {
            LOG.log(Level.INFO, "No element");
        }
    }

    private class ScheduleTask implements Runnable {

        private FinanceListView financeListView;

        public ScheduleTask(FinanceListView financeListView) {
            this.financeListView = financeListView;
        }

        @Override
        public void run() {
            for (Node cell : identifiableElementList.getChildrenUnmodifiable()) {
                VirtualFlow cell1 = (VirtualFlow) cell;
                for (int i = 0; i < cell1.getCellCount(); i++) {
                    IdentifiableListCell<Listed> cell2 = (IdentifiableListCell<Listed>) cell1.getCell(i);
                    RemoveItemFromList view1 = (RemoveItemFromList) cell2.getView();
                    if (!view1.hasRemoveItem()) {
                        view1.setRemoveItem(financeListView::removeItem);
                    }
                }
            }
        }
    }

}
