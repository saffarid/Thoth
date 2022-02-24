package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_view;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Partnership;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Typable;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_gui.thoth_lite.components.controls.ToolsPane;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortBy;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortPane;

import java.util.List;

public class PartnerListView
    extends IdentifiablesListView<Partnership>{

    protected PartnerListView(List<Partnership> datas) {
        super(datas, AvaliableTables.PARTNERS);
    }


    @Override
    protected SortPane getSortPane() {
        sortPane = SortPane.getInstance()
//                .setSortItems(FinanceListView.SORT_BY.values())
                .setCell()
//                .setSortMethod(this::sort)
//                .setValue(FinanceListView.SORT_BY.SORT_BY_CURRENCY_UP)
        ;
        return sortPane;
    }

    @Override
    protected void sort(ObservableValue<? extends SortBy> observableValue, SortBy sortBy, SortBy sortBy1) {

    }

    @Override
    protected void openCreateNewIdentifiable(ActionEvent event) {
        Partnership newPartner = new Partnership() {

            private String id = "-1";
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
}
