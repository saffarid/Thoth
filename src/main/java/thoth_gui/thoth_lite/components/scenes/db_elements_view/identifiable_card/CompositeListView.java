package thoth_gui.thoth_lite.components.scenes.db_elements_view.identifiable_card;

import javafx.beans.value.ObservableValue;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import layout.basepane.BorderPane;
import layout.basepane.GridPane;
import layout.basepane.HBox;
import layout.basepane.VBox;

import thoth_core.thoth_lite.db_data.db_data_element.properties.Finance;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Typable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Storagable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Storing;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_core.thoth_lite.ThothLite;
import thoth_gui.thoth_lite.components.controls.ButtonBar;
import thoth_gui.thoth_lite.components.controls.combo_boxes.FinanceComboBox;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortBy;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortPane;
import thoth_gui.thoth_lite.main_window.Workspace;
import thoth_gui.thoth_styleconstants.Stylesheets;
import controls.Button;
import controls.ComboBox;
import controls.Label;
import controls.TextField;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import thoth_gui.thoth_styleconstants.svg.Images;
import tools.BorderWrapper;
import tools.SvgWrapper;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompositeListView
        extends BorderPane {

    private final static String BACKET = "backet";

    private final static String TITLE = "composite";
    private final static String STORAGABLE = "storagable";
    private final static String COUNT = "count";
    private final static String COUNT_TYPE = "count_type";
    private final static String PRICE = "price";
    private final static String CURRENCY = "currency";
    private final static String SEARCH = "search";
    private final static String SORT = "sort";

    private enum SORT_BY implements SortBy {
        ID_UP("sort_by_id_up"),
        ID_DOWN("sort_by_id_down"),
        COUNT_UP("sort_by_count_up"),
        COUNT_DOWN("sort_by_count_down"),
        COUNT_TYPE_UP("sort_by_count_type_up"),
        COUNT_TYPE_DOWN("sort_by_count_type_down");
        private String id;

        SORT_BY(String id) {
            this.id = id;
        }

        @Override
        public String getSortName() {
            return id;
        }
    }

    private final static double widthPallete = 50;
    private final static double maxWidthPallete = 200;

    private boolean identifiableIsNew;

    private SimpleListProperty<Storing> items;

    public CompositeListView(
            List<Storing> items
            , boolean identifiableIsNew
    ) {
        super();

        this.items = new SimpleListProperty<>(FXCollections.observableList(items));
        this.identifiableIsNew = identifiableIsNew;

//        setPadding(new Insets(2));

        setTop(createTitle());
//        setLeft(createNewStoringRow());
        setCenter(createGrid());
//        setBottom(createTotal());

        getStylesheets().add(Stylesheets.IDENTIFIABLE_LIST.getStylesheet());
    }

    private Node createPalette() {
        BorderPane palette = new BorderPane();

        VBox controls = new VBox();

        palette.setBorder(
                new BorderWrapper()
                        .addRightBorder(1)
                        .setStyle(BorderStrokeStyle.SOLID)
                        .setColor(Color.GREY)
                        .commit()
        );

        if (!identifiableIsNew) {
            palette.setDisable(true);
        }

        ComboBox<Storagable> storagableComboBox = getStoragableComboBox();
        TextField count = getTextField(COUNT);
        Label countType = thoth_gui.thoth_lite.components.controls.Label.getInstanse();
        TextField price = getTextField(PRICE);
        ComboBox<Finance> financeComboBox = FinanceComboBox.getInstance();
        Button addButton = thoth_gui.thoth_lite.components.controls.Button.getInstance(SvgWrapper.getInstance(Images.PLUS(), 20, 20, 30, 30));

        storagableComboBox.valueProperty().addListener((observableValue, storagable, t1) -> {
            if (t1 != null) {
                countType.setText(t1.getCountType().getValue());
            }
        });

        addButton.setOnAction(actionEvent -> {
            Storing newStoring = new Storing() {
                private String id;
                private Storagable product;
                private Double count;
                private Typable countType;
                private Double price;
                private Finance currency;

                @Override
                public Double getCount() {
                    return count;
                }

                @Override
                public String getId() {
                    return id;
                }

                @Override
                public Typable getCountType() {
                    return countType;
                }

                @Override
                public Storagable getStoragable() {
                    return product;
                }

                @Override
                public void setId(String id) {
                    this.id = id;
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
                public void setStorageable(Storagable storageable) {
                    this.product = storageable;
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
                public Finance getCurrency() {
                    return currency;
                }

                @Override
                public void setCurrency(Finance currency) {
                    this.currency = currency;
                }
            };

            newStoring.setStorageable(storagableComboBox.getValue());
            newStoring.setCount(Double.parseDouble(count.getText()));
            newStoring.setCountType(storagableComboBox.getValue().getCountType());
            newStoring.setPrice(Double.parseDouble(price.getText()));
            newStoring.setCurrency(financeComboBox.getValue());

            //Нужна проверка на наличие продукта в списке
            //Флаг на наличине продукта в списке
            boolean alreadyExsist = false;

            for (Storing storing : items.getValue()) {
                if (storing.getStoragable().equals(newStoring.getStoragable())) {
                    alreadyExsist = true;
                }
            }

            //Если продукт уже есть в списке, то не добавляем его, необходимо выдавать оповещение что продукт уже добавлен.
            if (!alreadyExsist) {
                items.add(
                        newStoring
                );
            }

            storagableComboBox.setValue(null);
            count.setText("");
//            countType.setValue(countType.getItems().get(0));
            price.setText("");
            financeComboBox.setValue(financeComboBox.getItems().get(0));
        });

        controls.getChildren().addAll(
                createRow(
                        thoth_gui.thoth_lite.components.controls.Label.getInstanse(STORAGABLE)
                        , storagableComboBox
                )
                , createRow(
                        thoth_gui.thoth_lite.components.controls.Label.getInstanse(COUNT)
                        , count
                        , countType
                )
                , createRow(
                        thoth_gui.thoth_lite.components.controls.Label.getInstanse(PRICE)
                        , price
                        , financeComboBox
                )
        );

        HBox buttons = new HBox();
        buttons.getChildren().addAll(
                thoth_gui.thoth_lite.components.controls.Button.getInstance(SvgWrapper.getInstance(Images.PLUS(), 20, 20, 30, 30))
                , thoth_gui.thoth_lite.components.controls.Button.getInstance(SvgWrapper.getInstance(Images.PLUS(), 20, 20, 30, 30))

        );

        palette.setCenter(controls);
        palette.setBottom(buttons);

        return palette;
    }

    private Node createGrid() {
        GridPane content = new GridPane();
        content.addOnlyRow();

        content.setBorder(
                new BorderWrapper()
                        .addTopBorder(1)
                        .addBottomBorder(1)
                        .setColor(Color.GREY)
                        .setStyle(BorderStrokeStyle.SOLID)
                        .commit()
        );

        ColumnConstraints addPane = new ColumnConstraints(
                widthPallete, widthPallete, maxWidthPallete, Priority.ALWAYS, HPos.RIGHT, true
        );
        ColumnConstraints list = new ColumnConstraints(
//                widthPallete, widthPallete, maxWidthPallete, Priority.SOMETIMES, HPos.RIGHT, true
        );
        list.setHgrow(Priority.ALWAYS);
        list.setFillWidth(true);

        content.getColumnConstraints().addAll(
                addPane,
                list
        );

        content.add(createPalette(), 0, 0);
        content.add(createList(), 1, 0);

        return content;
    }

    private Node createList() {
        VBox res = new VBox();
        res.setFillWidth(true);
        res.setSpacing(2);
        ListView<Storing> listView = new ListView<>();

        res.setPadding(new Insets(2, 2, 2, 2));
        listView.setBorder(
                new BorderWrapper()
                        .addBorder(1)
                        .setColor(Color.GREY)
                        .setStyle(BorderStrokeStyle.SOLID)
                        .commit()
        );

        listView.setCellFactory(storingListView -> new CompositeCell());

        items.addListener((observableValue, storings, t1) -> {

            listView.setCellFactory(null);
            listView.setCellFactory(storingListView -> new CompositeCell());

        });

        listView.itemsProperty().bind(items);

        listView.getSelectionModel().clearSelection();

        listView.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case ESCAPE: {
                    listView.getSelectionModel().clearSelection();
                    break;
                }
            }
        });

        res.getChildren().addAll(
                getSortComboBox()
                , listView
                , createTotal()
        );

        return res;
    }

    private Node createRow(
            Node titleNode
            , Node... enterNodes
    ) {
        VBox res = new VBox();

        res.setAlignment(Pos.TOP_LEFT);
        res.setFillWidth(true);
        res.setPadding(new Insets(2));

        if (enterNodes.length > 1) {
            HBox hBox = new HBox(enterNodes);
            hBox.setSpacing(5);

            res.getChildren().addAll(
                    titleNode
                    , hBox
            );
        } else {
            res.getChildren().addAll(
                    titleNode
                    , Arrays.stream(enterNodes).findFirst().get()
            );
        }

        return res;
    }

    private Node createTitle() {
        HBox res = new HBox();

        res.getChildren().addAll(
                thoth_gui.thoth_lite.components.controls.Label.getInstanse(BACKET)
        );

        res.setPadding(new Insets(2));

        return res;
    }

    private Node createTotal() {
        HBox res = new HBox();

        res.setPadding(new Insets(0));

        res.getChildren().addAll(
                thoth_gui.thoth_lite.components.controls.Label.getInstanse("total")
        );

        return res;
    }

    public List<Storing> getComposite() {
        return items.getValue();
    }

    private Node getSortComboBox() {
        SortPane res = SortPane.getInstance()
                .setSortItems(SORT_BY.values())
                .setSortMethod(this::sort)
                .setCell()
                .setValue(SORT_BY.ID_UP);
        return res;
    }

    private void sort(ObservableValue<? extends SortBy> observableValue, SortBy sortBy, SortBy sortBy1) {
        SORT_BY sort = (SORT_BY) sortBy1;
        switch (sort) {
            case ID_UP: {
                items.sort((o1, o2) -> o1.getStoragable().getId().compareTo(o2.getStoragable().getId()));
                break;
            }
            case ID_DOWN: {
                items.sort((o1, o2) -> o2.getStoragable().getId().compareTo(o1.getStoragable().getId()));
                break;
            }
            case COUNT_UP: {
                items.sort((o1, o2) -> o1.getCount().compareTo(o2.getCount()));
                break;
            }
            case COUNT_DOWN: {
                items.sort((o1, o2) -> o2.getCount().compareTo(o1.getCount()));
                break;
            }
            case COUNT_TYPE_UP: {
                items.sort((o1, o2) -> o1.getCountType().getValue().compareTo(o2.getCountType().getValue()));
                break;
            }
            case COUNT_TYPE_DOWN: {
                items.sort((o1, o2) -> o2.getCountType().getValue().compareTo(o1.getCountType().getValue()));
                break;
            }
        }
    }

    private ComboBox<Storagable> getStoragableComboBox() {

        ComboBox<Storagable> res = thoth_gui.thoth_lite.components.controls.combo_boxes.ComboBox.getInstance();

        try {
            res.setItems(FXCollections.observableList((List<Storagable>) ThothLite.getInstance().getDataFromTable(AvaliableTables.STORAGABLE)));
        } catch (NotContainsException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        res.setCellFactory(storagableListView -> new StoragableCell());
        res.setButtonCell(new StoragableCell());

        return res;
    }

    private TextField getTextField(String id) {
        return getTextField(id, null);
    }

    private TextField getTextField(
            String id
            , String text
    ) {
        TextField res = thoth_gui.thoth_lite.components.controls.TextField.getInstance();
        res.setId(id);

        if (text != null) {
            res.setText(text);
        } else {
            res.setText(String.valueOf(0.0));
        }

        res.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.equals("")) {
                Pattern pattern = Pattern.compile("^[0-9]*[.]?[0-9]*$");
                Matcher matcher = pattern.matcher(t1);

                if (!matcher.matches()) {
                    res.setText(s);
                }
            }
        });

        res.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1 == false) {
                if (res.getText().equals("")) {
                    res.setText(String.valueOf(0.0));
                }
            }
        });

        res.setMinWidth(75);
        res.setPrefWidth(75);
        res.setMaxWidth(75);

        return res;
    }

    private class StoragableCell
            extends ListCell<Storagable> {

        private final String TEMPLATE = "%1s-%2s";

        @Override
        protected void updateItem(Storagable storagable, boolean b) {
            if (storagable != null) {
                super.updateItem(storagable, b);

                setText(
                        String.format(TEMPLATE, storagable.getId(), storagable.getName())
                );
            }
        }
    }

    private class CompositeCell
            extends ListCell<Storing> {

        private final static String CLASS_NAME = "composite-cell";

        public CompositeCell() {
            setPadding(new Insets(1, 0, 1, 0));
            getStyleClass().addAll(CLASS_NAME);
        }

        @Override
        protected void updateItem(Storing storing, boolean b) {
            if (storing != null) {
                super.updateItem(storing, b);
                setGraphic(new CompositeCellView(storing));
            }
        }
    }

    private class CompositeCellView
            extends BorderPane {

        private final static String CLASS_NAME = "composite-cell-content";

        private final static String TEMPLATE_NAME = "%1s-%2s";
        private final static String TEMPLATE_COUNT = "%1s: %2s";
        private final static String COUNT = "count";
        private final static String COUNT_TYPE = "count_type";
        private final static String PRICE = "price";

        private Storing storing;

        public CompositeCellView(Storing storing) {
            this.storing = storing;

            setLeft(createPoint());
            setCenter(createInform());

            setOnMouseClicked(this::cellClick);

            getStyleClass().addAll(CLASS_NAME);
        }

        private void cellClick(MouseEvent mouseEvent) {
            switch (mouseEvent.getButton()) {
                case PRIMARY: {
                    Workspace.getInstance().setNewScene(IdentifiableCard.getInstance(AvaliableTables.STORAGABLE, storing.getStoragable()));
                }
            }
        }

        private Button createRemoveButton() {
            Button res = new Button(SvgWrapper.getInstance(Images.TRASH(), 19, 19));

            res.setDisable(!identifiableIsNew);

            res.setOnAction(actionEvent -> {
                items.remove(storing);
            });

            return res;
        }

        private Node createPoint() {
            VBox vBox = new VBox();

            vBox.getChildren().add(
                    SvgWrapper.getInstance(Images.POINT(), 9, 9)
            );

            vBox.setPadding(new Insets(0, 0, 0, 5));
            vBox.setAlignment(Pos.CENTER);

            return vBox;
        }

        private GridPane createInform() {
            GridPane res = new GridPane();

            res.setPadding(new Insets(0, 10, 0, 10));

            res.getRowConstraints().addAll(
                    new RowConstraints(21)
            );

            res.getColumnConstraints().addAll(
                    new ColumnConstraints(200, 200, Double.MAX_VALUE, Priority.ALWAYS, HPos.LEFT, true)
                    , new ColumnConstraints(50, 50, Double.MAX_VALUE, Priority.ALWAYS, HPos.LEFT, true)
                    , new ColumnConstraints(50, 50, Double.MAX_VALUE, Priority.ALWAYS, HPos.LEFT, true)
                    , new ColumnConstraints(50, 50, Double.MAX_VALUE, Priority.ALWAYS, HPos.LEFT, true)
                    , new ColumnConstraints(50, 50, Double.MAX_VALUE, Priority.ALWAYS, HPos.LEFT, true)
                    , new ColumnConstraints(50, 50, Double.MAX_VALUE, Priority.ALWAYS, HPos.RIGHT, true)
            );

            res.add(
                    new Label(String.format(TEMPLATE_NAME, storing.getStoragable().getId(), storing.getStoragable().getName()))
                    , 0, 0
            );
            res.add(
                    new Label(String.format(TEMPLATE_COUNT, COUNT, storing.getCount()))
                    , 1, 0
            );
            res.add(
                    new Label(storing.getCountType().getValue())
                    , 2, 0
            );
            res.add(
                    new Label(String.format(TEMPLATE_COUNT, PRICE, storing.getPrice()))
                    , 3, 0
            );
            res.add(
                    new Label(storing.getCurrency().getCurrency().getCurrencyCode())
                    , 4, 0
            );
            res.add(
                    createRemoveButton()
                    , 5, 0
            );

            return res;
        }

    }

}
