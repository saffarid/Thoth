package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_view;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.skin.VirtualFlow;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Identifiable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Partnership;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Typable;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortBy;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortPane;
import thoth_gui.thoth_lite.components.scenes.db_elements_view.list_cell.IdentifiableListCell;
import thoth_gui.thoth_lite.components.scenes.db_elements_view.list_cell.RemoveItemFromList;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PartnerListView
    extends IdentifiablesListView<Partnership>
    implements RemoveItem{

    private enum SORT_BY implements SortBy{
        BY_NAME_UP("sort_by_name_up"),
        BY_NAME_DOWN("sort_by_name_down"),
        BY_WEB_UP("sort_by_web_up"),
        BY_WEB_DOWN("sort_by_web_down"),
        BY_PHONE_UP("sort_by_phone_up"),
        BY_PHONE_DOWN("sort_by_phone_down"),
        ;

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

    protected PartnerListView(List<Partnership> datas) {
        super(datas, AvaliableTables.PARTNERS);

        identifiableElementList.getItems().addListener((ListChangeListener<? super Partnership>) change -> {
            poolExecutor.schedule(scheduleTask, 250, TimeUnit.MILLISECONDS);
        });
    }

    @Override
    protected SortPane getSortPane() {
        sortPane = SortPane.getInstance()
                .setSortItems(SORT_BY.values())
                .setCell()
                .setSortMethod(this::sort)
                .setValue(SORT_BY.BY_NAME_UP)
        ;
        return sortPane;
    }

    @Override
    protected void sort(ObservableValue<? extends SortBy> observableValue, SortBy sortBy, SortBy sortBy1) {

        ObservableList<Partnership> list = identifiableElementList.getItems();
        SORT_BY t1 = (SORT_BY) sortBy1;
        switch (t1){
            case BY_NAME_UP:{
                list.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
                break;
            }
            case BY_NAME_DOWN:{
                list.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
                break;
            }
            case BY_PHONE_UP:{
                list.sort((o1, o2) -> o1.getWeb().compareTo(o2.getWeb()));
                break;
            }
            case BY_PHONE_DOWN:{
                list.sort((o1, o2) -> o2.getWeb().compareTo(o1.getWeb()));
                break;
            }
            case BY_WEB_UP:{
                list.sort((o1, o2) -> o1.getPhone().compareTo(o2.getPhone()));
                break;
            }
            case BY_WEB_DOWN:{
                list.sort((o1, o2) -> o2.getPhone().compareTo(o1.getPhone()));
                break;
            }
        }

    }

    @Override
    protected void openCreateNewIdentifiable(ActionEvent event) {
        Partnership newPartner = new Partnership() {

            private String id = "new";
            private String name;
            private String phone;
            private String web;
            private String comment;

            @Override
            public String getComment() {
                return comment;
            }

            @Override
            public String getId() {
                return id;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getPhone() {
                return phone;
            }

            @Override
            public String getWeb() {
                return web;
            }

            @Override
            public void setComment(String comment) {
                this.comment = comment;
            }

            @Override
            public void setId(String id) {
                this.id = id;
            }

            @Override
            public void setName(String name) {
                this.name = name;
            }

            @Override
            public void setPhone(String phone) {
                this.phone = phone;
            }

            @Override
            public void setWeb(String web) {
                this.web = web;
            }
        };
        ObservableList<Partnership> items = identifiableElementList.getItems();
        items.add(newPartner);
    }

    @Override
    public void removeItem(Identifiable identifiable) {
        if (identifiableElementList.getItems().contains(identifiable)) {

            identifiableElementList.setCellFactory(null);
            identifiableElementList.getItems().remove(identifiable);
            identifiableElementList.setCellFactory(tListView -> new IdentifiableListCell(this.table));
        }
    }

    private class ScheduleTask implements Runnable {

        private PartnerListView partnerListView;

        public ScheduleTask(PartnerListView partnerListView) {
            this.partnerListView = partnerListView;
        }

        @Override
        public void run() {
            for (Node cell : identifiableElementList.getChildrenUnmodifiable()) {
                VirtualFlow cell1 = (VirtualFlow) cell;
                for (int i = 0; i < cell1.getCellCount(); i++) {
                    IdentifiableListCell<Typable> cell2 = (IdentifiableListCell<Typable>) cell1.getCell(i);
                    RemoveItemFromList viewCell = (RemoveItemFromList) cell2.getView();

                    if (viewCell.hasRemoveItem()) continue;

                    viewCell.setRemoveItem(partnerListView::removeItem);
                }
            }
        }

    }
}
