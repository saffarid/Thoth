package ThothGUI.ThothLite.Components.DBElementsView.IdentifiableCard;

import ThothCore.ThothLite.DBData.DBDataElement.Implements.Currency;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.*;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothCore.ThothLite.Exceptions.NotContainsException;
import ThothCore.ThothLite.ThothLite;
import controls.*;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import layout.basepane.VBox;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

public class PurchasableCard extends IdentifiableCard {

    private TextField trackNumber;
    private ComboBox store;
    private DatePicker datePicker;
    private CompositeListView compositeListView;

    private enum PropertiesPurchasableId {
        TRACK_NUMBER("track_number"),
        STORE("store"),
        DELIVERY_DATE("delivery_date"),
        IS_DELIVERED("is_delivered"),
        STORAGABLE("storagable"),
        COUNT("count"),
        COUNT_TYPE("count_type"),
        PRICE("price"),
        CURRENCY("currency")
        ;
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
    }

    @Override
    public void apply() {
        if(identifiableIsNew){
            super.apply();
        }else{

            Purchasable purchasable = (Purchasable) this.identifiable;
            if( purchasable.isDelivered() ){

                try {
                    ThothLite instance = ThothLite.getInstance();

                    List<Purchasable> data = new LinkedList<>();
                    data.add(purchasable);

                    instance.updateInTable(AvaliableTables.PURCHASABLE, data);
                    instance.insertToTable(AvaliableTables.STORING, purchasable.getComposition());

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (NotContainsException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    @Override
    protected Node createContent() {

        HBox hBox = new HBox();

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_LEFT);
        trackNumber = getTextField(PropertiesPurchasableId.TRACK_NUMBER);
        store = getComboBox(PropertiesPurchasableId.TRACK_NUMBER);
        datePicker = getDatePicker();
        vBox.getChildren().addAll(
                new Twin(getLabel(PropertiesPurchasableId.TRACK_NUMBER), trackNumber)
                , new Twin(getLabel(PropertiesPurchasableId.STORE), store)
                , new Twin(getLabel(PropertiesPurchasableId.DELIVERY_DATE), datePicker)
        );
        vBox.setMinWidth(250);
//        vBox.setPadding(new Insets(2));

        Twin twinDelivered = new Twin(getLabel(PropertiesPurchasableId.IS_DELIVERED), getToggle());
        twinDelivered.setMinWidth(250);
        twinDelivered.setPrefWidth(250);

        hBox.setPadding(new Insets(2));

        hBox.getChildren().addAll(
                vBox,
                twinDelivered
        );

        hBox.setAlignment(Pos.TOP_LEFT);

        VBox res = new VBox();
        compositeListView = new CompositeListView(((Purchasable) identifiable).getComposition(), identifiableIsNew);
        res.getChildren().addAll(
                hBox,
                compositeListView
        );

        apply.disableProperty().bind(
                trackNumber.textProperty().isEqualTo("")
                        .or(store.valueProperty().isNull())
                        .or(datePicker.valueProperty().isNull())
        );

        return res;
    }

    protected ComboBox getComboBox(PropertiesPurchasableId id) {
        ComboBox res = new ComboBox<>();
        res.setId(id.id);

        try {
            res.setItems( FXCollections.observableList( ThothLite.getInstance().getDataFromTable(AvaliableTables.PARTNERS) ) );
        } catch (NotContainsException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        res.setCellFactory(listView -> new PartnerCell());
        res.setButtonCell(new PartnerCell());

        res.setMinWidth(120);
        res.setPrefWidth(120);
        res.setMaxWidth(120);

        if(!identifiableIsNew){
            res.setDisable(true);
        }

        return res;
    }

    private DatePicker getDatePicker(){
        DatePicker datePicker = new DatePicker(((Purchasable) identifiable).finishDate());

        datePicker.setMinWidth(120);
        datePicker.setPrefWidth(120);
        datePicker.setMaxWidth(120);

        if(!identifiableIsNew){
            datePicker.setDisable(true);
        }

        return datePicker;
    }

    private Label getLabel(PropertiesPurchasableId id) {
        Label res = new Label(id.id);
        res.setMinWidth(120);
        res.setPrefWidth(120);
        res.setMaxWidth(120);
        return res;
    }

    private TextField getTextField(PropertiesPurchasableId id) {
        TextField res = new TextField();
        res.setId(id.id);

        switch (id) {
            case TRACK_NUMBER: {
                res.setText(((Purchasable) identifiable).getId());
                break;
            }
        }

        if(!identifiableIsNew){
            res.setDisable(true);
        }

        res.setMinWidth(120);
        res.setPrefWidth(120);
        res.setMaxWidth(120);

        return res;
    }

    private Toggle getToggle(){
        Toggle res = new Toggle(((Purchasable)identifiable).isDelivered());

        return res;
    }

    @Override
    protected Identifiable identifiableInstance() {
        return new Purchasable() {

            private String orderNumber;
            private Partnership store;
            private LocalDate deliveryDate;
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
                for(Storing storing : purchasedProducts){
                    if(storing.getStoragable().getId().equals(id))
                        return storing;
                }
                return null;
            }

            @Override
            public Storing getStoringByStoringId(String id) {
                for(Storing storing : purchasedProducts){
                    if(storing.getId().equals(id))
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
                return "$classname{" +
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
    protected void updateIdentifiable() {
        ((Purchasable)identifiable).setId(trackNumber.getText());
        ((Purchasable)identifiable).setPartner((Partnership) store.getValue());
        ((Purchasable)identifiable).setFinishDate(datePicker.getValue());
    }

    private class PartnerCell
            extends ListCell<Partnership>{

        @Override
        protected void updateItem(Partnership partnership, boolean b) {
            if(partnership != null) {
                super.updateItem(partnership, b);
                setText(partnership.getName());
            }
        }
    }

}
