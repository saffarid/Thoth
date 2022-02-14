package thoth_gui.thoth_lite.components.scenes.db_elements_view.identifiable_card;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

import thoth_core.thoth_lite.db_data.db_data_element.properties.Identifiable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Typable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Storagable;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;

import layout.basepane.HBox;
import layout.basepane.VBox;
import thoth_gui.thoth_lite.components.controls.Label;
import thoth_gui.thoth_lite.components.controls.TextArea;
import thoth_gui.thoth_lite.components.controls.TextField;
import thoth_gui.thoth_lite.components.controls.ToolsPane;
import thoth_gui.thoth_lite.components.controls.combo_boxes.TypableComboBox;
import thoth_gui.thoth_lite.components.converters.StringDoubleConverter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StoragableCard
       extends IdentifiableCard {

    private final String newStoragable = "new_storagable";
    private final String storagable = "storagable";

    private controls.TextField article;
    private controls.TextField name;
    private controls.ComboBox type;
    private controls.ComboBox adress;
    private controls.TextField count;
    private DoubleProperty countProperty;
    private controls.ComboBox countType;
    private controls.TextArea note;

    private enum ControlsId {
        ARTICLE("article"),
        NAME("name"),
        PRODUCT_TYPE("product-type"),
        COUNT("count"),
        COUNT_TYPE("count_type"),
        ADRESS("adress"),
        NOTE("note");
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
        tools = new SimpleObjectProperty<>( createToolsNode() );
    }

    @Override
    protected Node createContentNode() {
        super.createContentNode();

        VBox vBox = new VBox();

        countProperty = new SimpleDoubleProperty();

        this.article = getTextField(ControlsId.ARTICLE);
        this.name = getTextField(ControlsId.NAME);
        this.type = TypableComboBox.getInstance(AvaliableTables.PRODUCT_TYPES, ((Storagable) identifiable).getType());
        this.count = getTextField(ControlsId.COUNT);
        this.countType = TypableComboBox.getInstance(AvaliableTables.COUNT_TYPES, ((Storagable) identifiable).getCountType());
        this.adress = TypableComboBox.getInstance(AvaliableTables.STORING, ((Storagable) identifiable).getAdress());
        this.note = TextArea.getInstance();

        Bindings.bindBidirectional(this.count.textProperty(), countProperty, new StringDoubleConverter());

        note.setText(((Storagable) identifiable).getNote());

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
                createRow(Label.getInstanse(ControlsId.ARTICLE.id), article)
                , createRow(Label.getInstanse(ControlsId.NAME.id), name)
                , createRow(Label.getInstanse(ControlsId.PRODUCT_TYPE.id), type)
                , createRow(Label.getInstanse(ControlsId.COUNT.id), count)
                , createRow(Label.getInstanse(ControlsId.ADRESS.id), adress)
                , createRow(Label.getInstanse(ControlsId.NOTE.id), note)
        );

        contentNode.setCenter(vBox);

        return contentNode;
    }

    @Override
    protected Node createToolsNode() {
        return new ToolsPane( identifiableIsNew ? newStoragable : storagable );
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

    private controls.TextField getTextField(ControlsId id) {
        controls.TextField res = TextField.getInstance();
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
                        Pattern pattern = Pattern.compile(StringDoubleConverter.countRegEx);
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
    protected void updateIdentifiable() {

        ((Storagable) identifiable).setId(article.getText());
        ((Storagable) identifiable).setName(name.getText());
        ((Storagable) identifiable).setType((Typable) type.getValue());
        ((Storagable) identifiable).setCount(Double.parseDouble(count.getText()));
        ((Storagable) identifiable).setCountType((Typable) countType.getValue());
        ((Storagable) identifiable).setAdress((Typable) adress.getValue());
        ((Storagable) identifiable).setNote(note.getText());

    }
}
