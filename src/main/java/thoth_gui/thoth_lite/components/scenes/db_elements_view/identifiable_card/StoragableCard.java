package thoth_gui.thoth_lite.components.scenes.db_elements_view.identifiable_card;

import controls.ComboBox;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

import thoth_core.thoth_lite.ThothLite;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Identifiable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Typable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Storagable;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;

import layout.basepane.HBox;
import layout.basepane.VBox;
import thoth_gui.thoth_lite.components.controls.*;
import thoth_gui.thoth_lite.components.controls.combo_boxes.TypableComboBox;
import thoth_gui.thoth_lite.components.converters.StringDoubleConverter;
import thoth_gui.thoth_lite.tools.Properties;
import thoth_gui.thoth_lite.tools.TextCase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StoragableCard
        extends IdentifiableCard {

    private final String newStoragable = "new_storagable";
    private final String storagable = "storagable";

    private controls.TextField article;
    private controls.TextField name;
    private ComboBox<Typable>  type;
    private ComboBox<Typable>  adress;
    private controls.TextField count;
    private DoubleProperty     countProperty;
    private ComboBox<Typable>  countType;
    private controls.TextArea  note;

    private enum ControlsId {
        ARTICLE(Properties.getString("article", TextCase.NORMAL)),
        NAME(Properties.getString("name", TextCase.NORMAL)),
        PRODUCT_TYPE(Properties.getString("product-type", TextCase.NORMAL)),
        COUNT(Properties.getString("count", TextCase.NORMAL)),
        COUNT_TYPE(Properties.getString("count_type", TextCase.NORMAL)),
        ADRESS(Properties.getString("storing", TextCase.NORMAL)),
        NOTE(Properties.getString("note", TextCase.NORMAL));
        private String id;

        ControlsId(String id) {
            this.id = id;
        }
    }

    protected StoragableCard(
            Identifiable identifiable
            , AvaliableTables table
    ) {
        super(identifiable, table);
        this.id = "storagable-card";
        tools = new SimpleObjectProperty<>(createToolsNode());
    }

    @Override
    public void close() {
        Platform.runLater(() -> {
            type.close();
            adress.close();
            countType.close();
        });
    }

    @Override
    protected Node createContentNode() {
        super.createContentNode();

        VBox vBox = new VBox();

        article = getTextField(ControlsId.ARTICLE);;
        name = getTextField(ControlsId.NAME);
        type = TypableComboBox.getInstance(AvaliableTables.PRODUCT_TYPES, ((Storagable) identifiable.getValue()).getType());
        adress = TypableComboBox.getInstance(AvaliableTables.STORING, ((Storagable) identifiable.getValue()).getAdress());
        count = getTextField(ControlsId.COUNT);
        countProperty = new SimpleDoubleProperty();
        countType = TypableComboBox.getInstance(AvaliableTables.COUNT_TYPES, ((Storagable) identifiable.getValue()).getCountType());
        note = TextArea.getInstance();

        Bindings.bindBidirectional(this.count.textProperty(), countProperty, new StringDoubleConverter());

        note.setText(((Storagable) identifiable.getValue()).getNote());

        apply.disableProperty().bind(
                article.textProperty().isEqualTo("")
                        .or(name.textProperty().isEqualTo(""))
                        .or(type.valueProperty().isNull())
                        .or(this.count.textProperty().isEqualTo(""))
                        .or(countProperty.lessThan(StringDoubleConverter.countMin))
                        .or(countType.valueProperty().isNull())
                        .or(adress.valueProperty().isNull())
        );

        HBox count = new HBox();
        count.setSpacing(5);
        count.getChildren().addAll(
                this.count
                , countType
        );

        vBox.getChildren().addAll(
                Row.getInstance(
                        Label.getInstanse(ControlsId.ARTICLE.id),
                        article
                )
                , Row.getInstance(
                        Label.getInstanse(ControlsId.NAME.id),
                        name
                )
                , Row.getInstance(
                        Label.getInstanse(ControlsId.PRODUCT_TYPE.id),
                        type
                )
                , Row.getInstance(
                        Label.getInstanse(ControlsId.COUNT.id),
                        count
                )
                , Row.getInstance(
                        Label.getInstanse(ControlsId.ADRESS.id)
                                .setInfoGraph(LabeledInfoGraphic.getInstance("Расположение продукта")),
                        adress
                )
                , Row.getInstance(
                        Label.getInstanse(ControlsId.NOTE.id),
                        note
                )
        );

        contentNode.setCenter(vBox);

        return contentNode;
    }

    @Override
    protected Node createToolsNode() {
        return ((ToolsPane) super.createToolsNode())
                .setTitleText(identifiableIsNew ? newStoragable : storagable);
    }

    private controls.TextField getTextField(ControlsId id) {
        controls.TextField res = TextField.getInstance();
        res.setId(id.id);

        switch (id) {
            case ARTICLE: {
                res.setText(identifiable.getValue().getId());
                break;
            }
            case NAME: {
                res.setText(((Storagable) identifiable.getValue()).getName());
                break;
            }
            case COUNT: {
                res.setText(String.valueOf(((Storagable) identifiable.getValue()).getCount()));
                break;
            }
        }

        res.textProperty().addListener((observableValue, s, t1) -> {
            switch (id) {
                case COUNT: {
                    if (t1.equals("")) return;

                    Pattern pattern = Pattern.compile(StringDoubleConverter.countRegEx);
                    Matcher matcher = pattern.matcher(t1);
                    if (!matcher.matches()) {
                        res.setText(s);
                    }
                }
            }
        });

        res.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1) return;
            switch (id) {
                case COUNT: {
                    if (res.getText().equals("")) {
                        res.setText(String.valueOf(0.0));
                    }
                }
            }
        });
        return res;
    }

    @Override
    protected Identifiable identifiableInstance() {
        return new Storagable() {

            private String id = "";
            private String name = "";
            private Double count = 0.;
            private Typable countType = null;
            private Typable adress = null;
            private Typable type = null;
            private String note = "";

            @Override
            public Typable getAdress() {
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
            public Typable getCountType() {
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
            public Typable getType() {
                return type;
            }

            @Override
            public void setAdress(Typable adress) {
                this.adress = adress;
            }

            @Override
            public void setCount(Double count) {
                this.count = count;
            }

            @Override
            public void setCountType(Typable countType) {
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
            public void setType(Typable type) {
                this.type = type;
            }

        };
    }

    @Override
    public void open() {
        ThothLite.getInstance().subscribeOnTable(AvaliableTables.PRODUCT_TYPES, type);
        ThothLite.getInstance().subscribeOnTable(AvaliableTables.STORING, adress);
        ThothLite.getInstance().subscribeOnTable(AvaliableTables.COUNT_TYPES, countType);
    }

    @Override
    protected void updateIdentifiable() {

        ((Storagable) identifiable.getValue()).setId(article.getText());
        ((Storagable) identifiable.getValue()).setName(name.getText());
        ((Storagable) identifiable.getValue()).setType((Typable) type.getValue());
        ((Storagable) identifiable.getValue()).setCount(Double.parseDouble(count.getText()));
        ((Storagable) identifiable.getValue()).setCountType((Typable) countType.getValue());
        ((Storagable) identifiable.getValue()).setAdress((Typable) adress.getValue());
        ((Storagable) identifiable.getValue()).setNote(note.getText());

    }
}
