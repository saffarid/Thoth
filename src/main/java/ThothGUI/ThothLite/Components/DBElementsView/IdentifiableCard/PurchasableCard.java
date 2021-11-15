package ThothGUI.ThothLite.Components.DBElementsView.IdentifiableCard;

import ThothCore.ThothLite.DBData.DBDataElement.Implements.Currency;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.*;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothCore.ThothLite.Exceptions.NotContainsException;
import ThothCore.ThothLite.ThothLite;
import controls.Label;
import controls.TextField;
import controls.Toggle;
import controls.Twin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import layout.basepane.VBox;

import java.sql.SQLException;
import java.util.List;


public class PurchasableCard extends IdentifiableCard {

    private enum PropertiesPurchasableId {
        TRACK_NUMBER("track_number"),
        STORE("store"),
        DELIVERY_DATE("delivery_date"),
        IS_DELIVERED("is_delivered"),
        STORAGABLE("storagable"),
        COUNT("count"),
        COUNT_TYPE("count_type");
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
    protected Node createContent() {

        VBox vBox = new VBox();


        vBox.getChildren().addAll(
                new Twin(getLabel(PropertiesPurchasableId.TRACK_NUMBER), getTextField(PropertiesPurchasableId.TRACK_NUMBER))
                , new Twin(getLabel(PropertiesPurchasableId.STORE), getComboBox(PropertiesPurchasableId.TRACK_NUMBER))
                , new Twin(getLabel(PropertiesPurchasableId.DELIVERY_DATE), new DatePicker(((Purchasable) identifiable).finishDate()))
                , new Twin(getLabel(PropertiesPurchasableId.IS_DELIVERED), new Toggle(false))
                , new CompositeListView(((Purchasable) identifiable).getComposition())
        );

        return vBox;
    }

    protected ComboBox getComboBox(PropertiesPurchasableId id) {
        ComboBox res = new ComboBox<>();
        res.setId(id.id);
        switch (id) {

        }


        res.valueProperty().addListener((observableValue, o, t1) -> {
            switch (id) {

            }
        });

        return res;
    }

    private Label getLabel(PropertiesPurchasableId id) {
        Label res = new Label(id.id);
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

        res.textProperty().addListener((observableValue, s, t1) -> {
            if (t1 != null) {

                switch (id) {

                }

            }
        });

        res.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1 == false) {

                switch (id) {

                }

            }
        });

        return res;
    }

}
