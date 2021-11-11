package ThothGUI.ThothLite.Components.DBElementsView.IdentifiableCard;

import ThothCore.ThothLite.DBData.DBDataElement.Implements.Currency;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Purchasable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothCore.ThothLite.Exceptions.NotContainsException;
import ThothCore.ThothLite.ThothLite;
import controls.Label;
import controls.TextField;
import controls.Twin;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import layout.basepane.VBox;

import java.sql.SQLException;
import java.util.List;

public class PurchasableCard extends IdentifiableCard {

    private enum PropertiesPurchasableId{
        TRACK_NUMBER("track_number"),
        STORE("store"),
        DELIVERY_DATE("delivery_date"),
        IS_DELIVERED("is_delivered"),
        STORAGABLE("storagable"),
        COUNT("count"),
        COUNT_TYPE("count_type")
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
    protected Node createContent() {

        VBox vBox = new VBox();

        vBox.getChildren().addAll(
                new Twin()
        );

        return vBox;
    }

    protected ComboBox getComboBox(PropertiesPurchasableId id) {
        ComboBox res = new ComboBox<>();
        res.setId(id.id);
            switch (id) {

            }


        res.valueProperty().addListener((observableValue, o, t1) -> {
            switch (id){

            }
        });

        return res;
    }

    private Label getLabel(String text) {
        Label res = new Label(text);
        return res;
    }

    private TextField getTextField(PropertiesPurchasableId id) {
        TextField res = new TextField();
        res.setId(id.id);

        switch (id) {

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
