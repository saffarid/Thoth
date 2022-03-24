package thoth_gui.thoth_lite.components.scenes.db_elements_view.identifiable_card;


import controls.Toggle;
import controls.Twin;
import javafx.beans.property.SimpleObjectProperty;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.Priority;

import javafx.scene.paint.Color;
import layout.basepane.BorderPane;
import layout.basepane.GridPane;

import thoth_core.thoth_lite.db_data.db_data_element.properties.Identifiable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Partnership;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Purchasable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Storing;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_core.thoth_lite.ThothLite;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;

import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;

import thoth_gui.thoth_lite.components.controls.Label;
import thoth_gui.thoth_lite.components.controls.TextField;
import thoth_gui.thoth_lite.components.controls.ToolsPane;
import thoth_gui.thoth_lite.components.controls.combo_boxes.ComboBox;
import thoth_gui.thoth_lite.tools.Properties;
import thoth_gui.thoth_lite.tools.TextCase;
import tools.BorderWrapper;


import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class PurchasableCard
        extends IdentifiableCard {

    private final String newPurchase = Properties.getString("new_purchase", TextCase.NORMAL);
    private final String purchase = Properties.getString("purchase", TextCase.NORMAL);

    private controls.TextField trackNumber;
    private controls.ComboBox store;
    private DatePicker datePicker;
    private CompositeListView compositeListView;
    private Toggle toggleDelivered;

    private enum PropertiesPurchasableId {
        TRACK_NUMBER(Properties.getString("track_number", TextCase.NORMAL)),
        STORE(Properties.getString("store", TextCase.NORMAL)),
        DELIVERY_DATE(Properties.getString("delivery_date", TextCase.NORMAL)),
        IS_DELIVERED(Properties.getString("is_delivered", TextCase.NORMAL)),
        STORAGABLE(Properties.getString("storagable", TextCase.NORMAL)),
        COUNT(Properties.getString("count", TextCase.NORMAL)),
        COUNT_TYPE(Properties.getString("count_type", TextCase.NORMAL)),
        PRICE(Properties.getString("price", TextCase.NORMAL)),
        CURRENCY(Properties.getString("currency", TextCase.NORMAL));
        private String id;

        PropertiesPurchasableId(String id) {
            this.id = id;
        }
    }

    protected PurchasableCard(
            Identifiable identifiable
            , AvaliableTables table
    ) {
        super(identifiable, table);
        this.id = "purchasable-card";
        tools = new SimpleObjectProperty<>(createToolsNode());
    }

    @Override
    public void apply() {
        if (identifiableIsNew) {
            super.apply();
        } else {
            if (toggleDelivered.isIsTrue()) {
                ((Purchasable) identifiable.getValue()).delivered();
            }
            ThothLite.getInstance().acceptPurchase((Purchasable) identifiable.getValue());
            closeable.close();
        }
    }

    @Override
    public void close() {
        store.close();
        compositeListView.close();
    }

    @Override
    protected Node createToolsNode() {
        apply.setDisable(((Purchasable) identifiable.getValue()).isDelivered());
        return ((ToolsPane) super.createToolsNode())
                .setTitleText(identifiableIsNew ? newPurchase : purchase);
    }

    @Override
    protected Node createContentNode() {
        super.createContentNode();

        toggleDelivered = new Toggle(((Purchasable) identifiable.getValue()).isDelivered());
        toggleDelivered.setDisable(((Purchasable) identifiable.getValue()).isDelivered());

        GridPane content = new GridPane();

        content.setPadding(new Insets(2));
        content.setHgap(5);
        content.setVgap(5);

        content
                .addRow(Priority.NEVER)
                .addRow(Priority.NEVER)
                .addRow(Priority.NEVER)
                .addRow(Priority.ALWAYS)
                .addColumn(Priority.SOMETIMES)
                .addColumn(Priority.ALWAYS)
        ;

        trackNumber = getTextField(PropertiesPurchasableId.TRACK_NUMBER);
        store = getComboBox(PropertiesPurchasableId.TRACK_NUMBER);
        datePicker = getDatePicker();

        content.add(getTwin(Label.getInstanse(PropertiesPurchasableId.TRACK_NUMBER.id), trackNumber), 0, 0);
        content.add(getTwin(Label.getInstanse(PropertiesPurchasableId.STORE.id), store), 0, 1);
        content.add(getTwin(Label.getInstanse(PropertiesPurchasableId.DELIVERY_DATE.id), datePicker), 0, 2);

        content.add(getTwin(Label.getInstanse(PropertiesPurchasableId.IS_DELIVERED.id), toggleDelivered), 1, 0);

        compositeListView = new CompositeListView(((Purchasable) identifiable.getValue()).getComposition(), identifiableIsNew);
        content.add(compositeListView, 0, 3, 2, 1);

        contentNode.setCenter(content);

        return contentNode;
    }

    private Twin getTwin(
            Node left,
            Node right
    ) {
        return new Twin(left, right)
                .setMinLeftWidth(150)
                .setMaxLeftWidth(150)
                .setPriorityLeft(Priority.NEVER)
                .setMinRightWidth(150)
                .setMaxRightWidth(150)
                .setPriorityRight(Priority.NEVER)
                .setFillWidthRight(true)
                ;
    }

    protected controls.ComboBox getComboBox(PropertiesPurchasableId id) {
        controls.ComboBox res = ComboBox.getInstance();
        res.setId(id.id);

        res.setItems(FXCollections.observableList(ThothLite.getInstance().getDataFromTable(AvaliableTables.PARTNERS)));

        res.setCellFactory(listView -> new PartnerCell());
        res.setButtonCell(new PartnerCell());

        if (!identifiableIsNew) {
            res.setDisable(true);
        }

        return res;
    }

    private DatePicker getDatePicker() {
        DatePicker datePicker = thoth_gui.thoth_lite.components.controls.DatePicker.getInstance(((Purchasable) identifiable.getValue()).finishDate());

        if (!identifiableIsNew) {
            datePicker.setDisable(true);
        }

        return datePicker;
    }

    private controls.TextField getTextField(PropertiesPurchasableId id) {
        controls.TextField res = TextField.getInstance();
        res.setId(id.id);

        switch (id) {
            case TRACK_NUMBER: {
                res.setText(((Purchasable) identifiable.getValue()).getId());
                break;
            }
        }

        if (!identifiableIsNew) {
            res.setDisable(true);
        }

        return res;
    }

    @Override
    protected Identifiable identifiableInstance() {
        return new Purchasable() {

            private String orderNumber;
            private Partnership store;
            private LocalDate deliveryDate = LocalDate.now();
            private boolean isDelivered = false;
            private List<Storing> purchasedProducts = new LinkedList<>();

            @Override
            public String getId() {
                return orderNumber;
            }

            @Override
            public void finish() {
                isDelivered = true;
            }

            @Override
            public LocalDate finishDate() {
                return deliveryDate;
            }

            @Override
            public String message() {
                return null;
            }

            @Override
            public boolean isFinish() {
                return isDelivered;
            }

            @Override
            public void setFinishDate(LocalDate finishDate) {
                this.deliveryDate = finishDate;
            }

            @Override
            public void setId(String id) {
                this.orderNumber = id;
            }

            @Override
            public List<Storing> getComposition() {
                return purchasedProducts;
            }

            @Override
            public void addStoring(Storing storing) {
                purchasedProducts.add(storing);
            }

            @Override
            public boolean containsStoring(Storing storing) {
                return purchasedProducts.contains(storing);
            }

            @Override
            public Storing getStoringByStoragableId(String id) {
                for (Storing storing : purchasedProducts) {
                    if (storing.getStoragable().getId().equals(id))
                        return storing;
                }
                return null;
            }

            @Override
            public Storing getStoringByStoringId(String id) {
                for (Storing storing : purchasedProducts) {
                    if (storing.getId().equals(id))
                        return storing;
                }
                return null;
            }

            @Override
            public boolean removeStoring(Storing storing) {
                return purchasedProducts.remove(storing);
            }

            @Override
            public Partnership getPartner() {
                return store;
            }

            @Override
            public void setPartner(Partnership partner) {
                this.store = partner;
            }

            @Override
            public boolean isDelivered() {
                return isDelivered;
            }

            @Override
            public void delivered() {
                isDelivered = true;
            }

            @Override
            public String toString() {
                return "Purchase{" +
                        "orderNumber='" + orderNumber + '\'' +
                        ", store=" + store +
                        ", deliveryDate=" + deliveryDate +
                        ", isDelivered=" + isDelivered +
                        ", purchasedProducts=" + purchasedProducts +
                        '}';
            }
        };
    }

    @Override
    public void open() {
        ThothLite.getInstance().subscribeOnTable(AvaliableTables.PARTNERS, store);
        compositeListView.open();
    }

    @Override
    protected void updateIdentifiable() {
        ((Purchasable) identifiable.getValue()).setId(trackNumber.getText());
        ((Purchasable) identifiable.getValue()).setPartner((Partnership) store.getValue());
        ((Purchasable) identifiable.getValue()).setFinishDate(datePicker.getValue());
    }

    private class PartnerCell
            extends ListCell<Partnership> {

        @Override
        protected void updateItem(Partnership partnership, boolean b) {
            if (partnership != null) {
                super.updateItem(partnership, b);
                setText(partnership.getName());
            }
        }
    }

}
