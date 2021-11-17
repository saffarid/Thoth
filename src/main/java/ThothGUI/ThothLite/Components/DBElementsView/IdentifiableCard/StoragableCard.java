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
        COUNT("count"),
        COUNT_TYPE("count_type"),
        ADRESS("adress")
        ;
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
                , new Twin(getLabel(PropetiesStoragableId.COUNT.id), getTextField(PropetiesStoragableId.COUNT))
                , new Twin(getLabel(PropetiesStoragableId.COUNT_TYPE.id), getComboBox(PropetiesStoragableId.COUNT_TYPE))
                , new Twin(getLabel(PropetiesStoragableId.ADRESS.id), getComboBox(PropetiesStoragableId.ADRESS))
        );

        return vBox;
    }

    protected ComboBox getComboBox(PropetiesStoragableId id) {
        ComboBox res = new ComboBox<>();
        res.setId(id.id);

        res.setCellFactory(listedListView -> new ComboBoxListedCell());
        res.setButtonCell(new ComboBoxListedCell());

        Listed value = null;

        try {

            switch (id) {
                case PRODUCT_TYPE: {
                    res.setItems(FXCollections.observableList( (List<Listed>) ThothLite.getInstance().getDataFromTable(AvaliableTables.PRODUCT_TYPES) ) );

                    value = ((Storagable) identifiable).getType();
                    if (value == null) {
                        res.setValue(res.getItems().get(0));
                        ((Storagable) identifiable).setType((Listed) res.getValue());
                    } else {
                        res.setValue(value);
                    }
                    break;
                }
                case COUNT_TYPE:{
                    res.setItems(FXCollections.observableList( (List<Listed>) ThothLite.getInstance().getDataFromTable(AvaliableTables.COUNT_TYPES) ));

                    value = ((Storagable)identifiable).getCountType();
                    if(value == null){
                        res.setValue(res.getItems().get(0));
                        ((Storagable) identifiable).setCountType((Listed) res.getValue());
                    }else{
                        res.setValue(value);
                    }
                    break;
                }
                case ADRESS:{
                    res.setItems( FXCollections.observableList( (List<Listed>) ThothLite.getInstance().getDataFromTable(AvaliableTables.STORING) ) );

                    value = ((Storagable)identifiable).getCountType();
                    if(value == null){
                        res.setValue(res.getItems().get(0));
                        ((Storagable) identifiable).setAdress((Listed) res.getValue());
                    }else{
                        res.setValue(value);
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
                case PRODUCT_TYPE:{
                    ((Storagable)identifiable).setType((Listed) res.getValue());
                    break;
                }
                case COUNT_TYPE:{
                    ((Storagable)identifiable).setCountType((Listed) res.getValue());
                    break;
                }
                case ADRESS:{
                    ((Storagable)identifiable).setAdress((Listed) res.getValue());
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
            case COUNT:{
                res.setText( String.valueOf( ((Storagable)identifiable).getCount() ) );
                break;
            }
        }

        res.textProperty().addListener((observableValue, s, t1) -> {
            if(t1 != null){
                switch (id){
                    case COUNT:{
                        try {
                            Double.parseDouble(res.getText());
                        }catch (NumberFormatException e){
                            res.setText(s);
                        }
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
                    case COUNT:{
                        ((Storagable)identifiable).setCount( Double.parseDouble(res.getText()) );
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
            private Double count = 0.;
            private Listed countType = null;
            private Listed adress = null;
            private Listed type = null;

            @Override
            public Listed getAdress() {
                return adress;
            }

            @Override
            public Double getCount() {
                return count;
            }

            @Override
            public Listed getCountType() {
                return countType;
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
            public Listed getType() {
                return type;
            }

            @Override
            public void setAdress(Listed adress) {
                this.adress = adress;
            }

            @Override
            public void setCount(Double count) {
                this.count = count;
            }

            @Override
            public void setCountType(Listed countType) {
                this.countType = countType;
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
            public void setType(Listed type) {
                this.type = type;
            }

        };
    }
}
