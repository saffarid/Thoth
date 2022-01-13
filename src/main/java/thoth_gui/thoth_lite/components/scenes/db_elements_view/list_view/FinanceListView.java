package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_view;

import thoth_core.thoth_lite.db_data.db_data_element.properties.Finance;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Identifiable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Typable;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_gui.thoth_lite.components.scenes.db_elements_view.list_cell.IdentifiableListCell;
import thoth_gui.thoth_lite.components.scenes.db_elements_view.list_cell.RemoveItemFromList;
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
import java.util.logging.Level;

public class FinanceListView
        extends IdentifiablesListView<Finance>
        implements RemoveItem {

    private enum SORT_BY{
        SORT_BY_CURRENCY_UP("sort_by_currency_up"),
        SORT_BY_CURRENCY_DOWN("sort_by_currency_down"),
        SORT_BY_COURSE_UP("sort_by_course_up"),
        SORT_BY_COURSE_DOWN("sort_by_course_down");
        private String sortBy;
        SORT_BY(String sortBy) {
            this.sortBy = sortBy;
        }
        public String getSortBy() {
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
    protected Node getSortPane() {
        HBox sortedPane = (HBox) super.getSortPane();

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
            ObservableList<Finance> items = identifiableElementList.getItems();
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
        });

        sortedBox.setValue(SORT_BY.SORT_BY_CURRENCY_UP);

        return sortedPane;
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
