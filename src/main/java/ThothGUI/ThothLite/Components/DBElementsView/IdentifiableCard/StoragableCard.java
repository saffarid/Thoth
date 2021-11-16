package ThothGUI.ThothLite.Components.DBElementsView.IdentifiableCard;

import ThothCore.ThothLite.DBData.DBDataElement.Implements.Currency;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothCore.ThothLite.Exceptions.NotContainsException;
import ThothCore.ThothLite.ThothLite;
import controls.ComboBox;
import controls.Label;
import controls.TextField;
import controls.Twin;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import layout.basepane.VBox;

import java.sql.SQLException;
import java.util.List;

public class StoragableCard extends IdentifiableCard {

    private enum PropetiesStoragableId {
        ARTICLE("article"),
        NAME("name"),
        PRODUCT_TYPE("product-type"),
        PRICE("price"),
        PRICE_TYPE("price-type");
        private String id;

        PropetiesStoragableId(String id) {
            this.id = id;
        }

    }

    protected StoragableCard(
            Identifiable identifiable,
            AvaliableTables table) {
        super(identifiable, table);
    }

    @Override
    protected Node createContent() {
        VBox vBox = new VBox();

        vBox.getChildren().addAll(
                new Twin(getLabel(PropetiesStoragableId.ARTICLE.id), getTextField(PropetiesStoragableId.ARTICLE))
                , new Twin(getLabel(PropetiesStoragableId.NAME.id), getTextField(PropetiesStoragableId.NAME))
                , new Twin(getLabel(PropetiesStoragableId.PRODUCT_TYPE.id), getComboBox(PropetiesStoragableId.PRODUCT_TYPE))
                , new Twin(getLabel(PropetiesStoragableId.PRICE.id), getTextField(PropetiesStoragableId.PRICE))
                , new Twin(getLabel(PropetiesStoragableId.PRICE_TYPE.id), getComboBox(PropetiesStoragableId.PRICE_TYPE))
        );

        return vBox;
    }

    protected ComboBox getComboBox(PropetiesStoragableId id) {
        ComboBox res = new ComboBox<>();
        res.setId(id.id);

        try {

            switch (id) {
                case PRICE_TYPE: {
                    res.setItems(FXCollections.observableList(ThothLite.getInstance().getDataFromTable(AvaliableTables.CURRENCIES)));
                    res.setCellFactory(listedListView -> new ComboBoxCurrencyCell());
                    res.setButtonCell(new ComboBoxCurrencyCell());
                    Currency currency = ((Storagable) identifiable).getCurrency();
                    if (currency == null) {
                        res.setValue(res.getItems().get(0));
                        ((Storagable) identifiable).setCurrency((Currency) res.getValue());
                    } else {
                        res.setValue(currency);
                    }
                    break;
                }
                case PRODUCT_TYPE: {
                    res.setItems(FXCollections.observableList((List<Listed>) ThothLite.getInstance().getDataFromTable(AvaliableTables.PRODUCT_TYPES)));
                    res.setCellFactory(listedListView -> new ComboBoxListedCell());
                    res.setButtonCell(new ComboBoxListedCell());
                    Listed type = ((Storagable) identifiable).getType();
                    if (type == null) {
                        res.setValue(res.getItems().get(0));
                        ((Storagable) identifiable).setType((Listed) res.getValue());
                    } else {
                        res.setValue(type);
                    }
                    break;
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NotContainsException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        res.valueProperty().addListener((observableValue, o, t1) -> {
            switch (id){
                case PRICE_TYPE:{
                    ((Storagable)identifiable).setCurrency((Currency) res.getValue());
                    break;
                }
                case PRODUCT_TYPE:{
                    ((Storagable)identifiable).setType((Listed) res.getValue());
                    break;
                }
            }
        });

        res.setMinWidth(120);
        res.setPrefWidth(120);
        res.setMaxWidth(120);

        return res;
    }

    private Label getLabel(String text) {
        Label res = new Label(text);
        return res;
    }

    private TextField getTextField(PropetiesStoragableId id) {
        TextField res = new TextField();
        res.setId(id.id);

        switch (id) {
            case ARTICLE: {
                res.setText(identifiable.getId());
                break;
            }
            case NAME: {
                res.setText(((Storagable) identifiable).getName());
                break;
            }
            case PRICE: {
                res.setText(String.valueOf(((Storagable) identifiable).getPrice()));
                break;
            }
        }

        res.textProperty().addListener((observableValue, s, t1) -> {
            if (t1 != null) {

                switch (id) {
                    case ARTICLE: {

                        break;
                    }
                    case NAME: {

                        break;
                    }
                    case PRICE: {

                        try{
                            Double.parseDouble( res.getText() );
                        } catch (NumberFormatException e){
                            if(!res.getText().equals("")) {
                                res.setText(s);
                            }
                        }

                        break;
                    }
                }

            }
        });

        res.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1 == false) {

                switch (id) {
                    case ARTICLE: {
                        identifiable.setId(res.getText());
                        break;
                    }
                    case NAME: {
                        ((Storagable) identifiable).setName(res.getText());
                        break;
                    }
                    case PRICE: {
                        ((Storagable) identifiable).setPrice( Double.parseDouble(res.getText()) );
                        break;
                    }
                }

            }
        });

        res.setMinWidth(120);
        res.setPrefWidth(120);
        res.setMaxWidth(120);

        return res;
    }

    @Override
    protected Identifiable identifiableInstance() {
        return new Storagable() {

            private String id = null;
            private String name = null;
            private Double price = 0.0;
            private Currency currency = null;
            private Listed type = null;

            @Override
            public String getId() {
                return id;
            }

            @Override
            public void setId(String id) {
                this.id = id;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public void setName(String name) {
                this.name = name;
            }

            @Override
            public Double getPrice() {
                return price;
            }

            @Override
            public void setPrice(Double price) {
                this.price = price;
            }

            @Override
            public Currency getCurrency() {
                return currency;
            }

            @Override
            public void setCurrency(Currency currency) {
                this.currency = currency;
            }

            @Override
            public Listed getType() {
                return type;
            }

            @Override
            public void setType(Listed type) {
                this.type = type;
            }

            @Override
            public String toString() {
                return "$classname{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", price=" + price +
                        ", currency=" + currency +
                        ", type=" + type +
                        '}';
            }
        };
    }
}
