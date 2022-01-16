package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_view;

import javafx.beans.value.ObservableValue;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Finance;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Identifiable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Typable;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortBy;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortCell;
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
import java.util.logging.Level;

public class FinanceListView
        extends IdentifiablesListView<Finance>
        implements RemoveItem {

    private enum SORT_BY implements SortBy{
        SORT_BY_CURRENCY_UP("sort_by_currency_up"),
        SORT_BY_CURRENCY_DOWN("sort_by_currency_down"),
        SORT_BY_COURSE_UP("sort_by_course_up"),
        SORT_BY_COURSE_DOWN("sort_by_course_down");
        private String sortBy;
        SORT_BY(String sortBy) {
            this.sortBy = sortBy;
        }

        @Override
        public String getSortName() {
            return sortBy;
        }
    }

    private final ScheduleTask scheduleTask;

    protected FinanceListView(List<Finance> datas) {
        super(datas, AvaliableTables.CURRENCIES);

        scheduleTask = new ScheduleTask(this);
        ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(1);
        poolExecutor.schedule(scheduleTask, 300, TimeUnit.MILLISECONDS);

        identifiableElementList.getItems().addListener((ListChangeListener<? super Finance>) change -> {
            poolExecutor.schedule(scheduleTask, 250, TimeUnit.MILLISECONDS);
        });
    }

    @Override
    protected SortPane getSortPane() {
        sortPane = SortPane.getInstance()
                .setSortItems(SORT_BY.values())
                .setCell()
                .setSortMethod(this::sort)
                .setValue(SORT_BY.SORT_BY_CURRENCY_UP)
        ;
        return sortPane;
    }

    @Override
    protected void sort(ObservableValue<? extends SortBy> observableValue, SortBy sortBy, SortBy sortBy1) {
        ObservableList<Finance> items = identifiableElementList.getItems();
        SORT_BY t1 = (SORT_BY) sortBy1;
        switch (t1){
            case SORT_BY_CURRENCY_UP:{
                items.sort((o1, o2) -> o1.getCurrency().compareTo(o2.getCurrency()));
                break;
            }
            case SORT_BY_CURRENCY_DOWN:{
                items.sort((o1, o2) -> o2.getCurrency().compareTo(o1.getCurrency()));
                break;
            }
            case SORT_BY_COURSE_UP:{
                items.sort((o1, o2) -> o1.getCourse().compareTo(o2.getCourse()));
                break;
            }
            case SORT_BY_COURSE_DOWN:{
                items.sort((o1, o2) -> o2.getCourse().compareTo(o1.getCourse()));
                break;
            }
        }
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
                    IdentifiableListCell<Typable> cell2 = (IdentifiableListCell<Typable>) cell1.getCell(i);
                    RemoveItemFromList view1 = (RemoveItemFromList) cell2.getView();
                    if (!view1.hasRemoveItem()) {
                        view1.setRemoveItem(financeListView::removeItem);
                    }
                }
            }
        }
    }

}
