package ThothGUI.ThothLite.Components.DBElementsView.IdentifiableCard;

import ThothCore.ThothLite.DBData.DBDataElement.Implements.Currency;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothCore.ThothLite.Exceptions.NotContainsException;
import ThothCore.ThothLite.ThothLite;
import ThothGUI.ThothLite.Components.DBElementsView.IdentifiableCard.IdentifiableCard;
import controls.ComboBox;
import controls.Label;
import controls.TextField;
import controls.Twin;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import layout.basepane.HBox;
import layout.basepane.VBox;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StoragableCard extends IdentifiableCard {

    private TextField article;
    private TextField name;
    private ComboBox type;
    private ComboBox adress;
    private TextField count;
    private ComboBox countType;
    private TextArea note;

    private enum PropetiesStoragableId {
        ARTICLE("article"),
        NAME("name"),
        PRODUCT_TYPE("product-type"),
        COUNT("count"),
        COUNT_TYPE("count_type"),
        ADRESS("adress"),
        NOTE("note");
        private String id;

        PropetiesStoragableId(String id) {
            this.id = id;
        }

    }

    protected StoragableCard(
            Identifiable identifiable
            , AvaliableTables table
    ) {
        super(identifiable, table);
    }

    @Override
    protected Node createContent() {
        VBox vBox = new VBox();

//        vBox.getChildren().addAll(
//                  new Twin(getLabel(PropetiesStoragableId.ARTICLE.id), getTextField(PropetiesStoragableId.ARTICLE))
//                , new Twin(getLabel(PropetiesStoragableId.NAME.id), getTextField(PropetiesStoragableId.NAME))
//                , new Twin(getLabel(PropetiesStoragableId.PRODUCT_TYPE.id), getComboBox(PropetiesStoragableId.PRODUCT_TYPE))
//                , new Twin(getLabel(PropetiesStoragableId.COUNT.id), getTextField(PropetiesStoragableId.COUNT))
//                , new Twin(getLabel(PropetiesStoragableId.COUNT_TYPE.id), getComboBox(PropetiesStoragableId.COUNT_TYPE))
//                , new Twin(getLabel(PropetiesStoragableId.ADRESS.id), getComboBox(PropetiesStoragableId.ADRESS))
//        );

        article = getTextField(PropetiesStoragableId.ARTICLE);
        name = getTextField(PropetiesStoragableId.NAME);
        type = getComboBox(PropetiesStoragableId.PRODUCT_TYPE);
        this.count = getTextField(PropetiesStoragableId.COUNT);
        countType = getComboBox(PropetiesStoragableId.COUNT_TYPE);
        adress = getComboBox(PropetiesStoragableId.ADRESS);
        note = new TextArea();

        note.setText(((Storagable) identifiable).getNote());

        apply.disableProperty().bind(
                article.textProperty().isEqualTo("")
                        .or(name.textProperty().isEqualTo(""))
                        .or(type.valueProperty().isNull())
                        .or(this.count.textProperty().isEqualTo(""))
                        .or(countType.valueProperty().isNull())
                        .or(adress.valueProperty().isNull())
        );

        HBox count = new HBox();
        count.setSpacing(5);
//        count.setPadding(new Insets(2));
        count.getChildren().addAll(
                this.count
                , countType
        );

        vBox.getChildren().addAll(
                createRow(getLabel(PropetiesStoragableId.ARTICLE.id), article)
                , createRow(getLabel(PropetiesStoragableId.NAME.id), name)
                , createRow(getLabel(PropetiesStoragableId.PRODUCT_TYPE.id), type)
                , createRow(getLabel(PropetiesStoragableId.COUNT.id), count)
                , createRow(getLabel(PropetiesStoragableId.ADRESS.id), adress)
                , createRow(getLabel(PropetiesStoragableId.NOTE.id), note)
        );

        return vBox;
    }

    private Node createRow(
            Node titleNode
            , Node enterNode
    ) {
        VBox res = new VBox();

        res.setAlignment(Pos.TOP_LEFT);
        res.setFillWidth(true);
        res.setPadding(new Insets(2));

        res.getChildren().addAll(
                titleNode
                , enterNode
        );

        return res;
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
                    res.setItems(FXCollections.observableList((List<Listed>) ThothLite.getInstance().getDataFromTable(AvaliableTables.PRODUCT_TYPES)));

                    value = ((Storagable) identifiable).getType();
                    if (value == null) {
                        res.setValue(res.getItems().get(0));
                    } else {
                        res.setValue(value);
                    }
                    break;
                }
                case COUNT_TYPE: {
                    res.setItems(FXCollections.observableList((List<Listed>) ThothLite.getInstance().getDataFromTable(AvaliableTables.COUNT_TYPES)));

                    value = ((Storagable) identifiable).getCountType();
                    if (value == null) {
                        res.setValue(res.getItems().get(0));
                    } else {
                        res.setValue(value);
                    }
                    break;
                }
                case ADRESS: {
                    res.setItems(FXCollections.observableList((List<Listed>) ThothLite.getInstance().getDataFromTable(AvaliableTables.STORING)));

                    value = ((Storagable) identifiable).getAdress();
                    if (value == null) {
                        res.setValue(res.getItems().get(0));
                    } else {
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

//        res.setMinWidth(120);
//        res.setPrefWidth(120);
//        res.setMaxWidth(120);

        return res;
    }

    private Label getLabel(String text) {
        Label res = new Label(text);
        res.setMinWidth(120);
        res.setPrefWidth(120);
        res.setMaxWidth(120);
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
            case COUNT: {
                res.setText(String.valueOf(((Storagable) identifiable).getCount()));
                break;
            }
        }

        res.textProperty().addListener((observableValue, s, t1) -> {
            switch (id) {
                case COUNT: {
                    if (!t1.equals("")) {
                        Pattern pattern = Pattern.compile("^[0-9]*[.]?[0-9]*$");
                        Matcher matcher = pattern.matcher(t1);

                        if (!matcher.matches()) {
                            res.setText(s);
                        }
                    }
                }
            }
        });

        res.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1 == false) {

                switch (id) {
                    case COUNT: {
                        if (res.getText().equals("")) {
                            res.setText(String.valueOf(0.0));
                        }
                    }
                }

            }
        });

//        res.setMinWidth(120);
//        res.setPrefWidth(120);
//        res.setMaxWidth(120);

        return res;
    }

    @Override
    protected Identifiable identifiableInstance() {
        return new Storagable() {

            private String id = "";
            private String name = "";
            private Double count = 0.;
            private Listed countType = null;
            private Listed adress = null;
            private Listed type = null;
            private String note = "";

            @Override
            public Listed getAdress() {
                return adress;
            }

            @Override
            public void setNote(String note) {
                this.note = note;
            }

            @Override
            public String getNote() {
                return note;
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

    @Override
    protected void updateIdentifiable() {

        ((Storagable) identifiable).setId(article.getText());
        ((Storagable) identifiable).setName(name.getText());
        ((Storagable) identifiable).setType((Listed) type.getValue());
        ((Storagable) identifiable).setCount(Double.parseDouble(count.getText()));
        ((Storagable) identifiable).setCountType((Listed) countType.getValue());
        ((Storagable) identifiable).setAdress((Listed) adress.getValue());
        ((Storagable) identifiable).setNote(note.getText());

    }
}
