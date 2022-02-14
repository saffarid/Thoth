package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_view;

import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import layout.basepane.BorderPane;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Finance;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Identifiable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Typable;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_gui.thoth_lite.components.controls.Label;
import thoth_gui.thoth_lite.components.controls.ToolsPane;
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
import tools.BackgroundWrapper;

import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class FinanceListView
        extends IdentifiablesListView<Finance> {

    private enum SORT_BY implements SortBy {
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

    protected FinanceListView(List<Finance> datas) {
        super(datas, AvaliableTables.CURRENCIES);
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
    protected Node createToolsNode() {
        toolsNode = new ToolsPane(table.name())
                .addSortPane(
                        SortPane.getInstance()
                                .setSortItems(SORT_BY.values())
                                .setCell()
                                .setSortMethod(this::sort)
                                .setValue(SORT_BY.SORT_BY_CURRENCY_UP)
                );

        return toolsNode;
    }

    @Override
    protected void sort(ObservableValue<? extends SortBy> observableValue, SortBy sortBy, SortBy sortBy1) {
        ObservableList<Finance> items = identifiableElementList.getItems();
        SORT_BY t1 = (SORT_BY) sortBy1;
        switch (t1) {
            case SORT_BY_CURRENCY_UP: {
                items.sort((o1, o2) -> o1.getCurrency().getCurrencyCode().compareTo(o2.getCurrency().getCurrencyCode()));
                break;
            }
            case SORT_BY_CURRENCY_DOWN: {
                items.sort((o1, o2) -> o2.getCurrency().getCurrencyCode().compareTo(o1.getCurrency().getCurrencyCode()));
                break;
            }
            case SORT_BY_COURSE_UP: {
                items.sort((o1, o2) -> o1.getCourse().compareTo(o2.getCourse()));
                break;
            }
            case SORT_BY_COURSE_DOWN: {
                items.sort((o1, o2) -> o2.getCourse().compareTo(o1.getCourse()));
                break;
            }
        }
    }

    @Override
    protected void openCreateNewIdentifiable(ActionEvent event) {
    }

}
