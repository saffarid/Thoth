package thoth_gui.thoth_lite.components.scenes.db_elements_view.identifiable_card;

import javafx.scene.layout.*;
import layout.basepane.BorderPane;
import layout.basepane.HBox;
import layout.basepane.VBox;
import thoth_core.thoth_lite.db_data.db_data_element.implement.Currency;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Finance;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Typable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Storagable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Storing;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_core.thoth_lite.ThothLite;
import thoth_gui.thoth_lite.main_window.ThothLiteWindow;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import thoth_gui.thoth_styleconstants.svg.Plus;

import java.sql.SQLException;
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

    private enum SORT_BY {
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
    }

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
        setCenter(createContent());
        setBottom(createTotal());

        getStylesheets().add(getClass().getResource(Stylesheets.IDENTIFIABLE_LIST).toExternalForm());
    }

    private Node createNewStoringRow() {
        FlowPane newStoringNode = new FlowPane(Orientation.HORIZONTAL, 5, 5);

        newStoringNode.setColumnHalignment(HPos.LEFT);
        newStoringNode.setRowValignment(VPos.CENTER);

        newStoringNode.setAlignment(Pos.CENTER_LEFT);

        newStoringNode.setPadding(new Insets(2));

        if (!identifiableIsNew) {
            newStoringNode.setDisable(true);
        }

        ComboBox<Storagable> storagableComboBox = getStoragableComboBox();
        TextField count = getTextField(COUNT);
        ComboBox<Typable> countTypeComboBox = getCountTypeComboBox();
        Button addButton = thoth_gui.thoth_lite.components.controls.Button.getInstance( Plus.getInstance() );
        TextField price = getTextField(PRICE);
        ComboBox<Currency> currencyComboBox = getCurrencyComboBox();

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
            newStoring.setCountType(countTypeComboBox.getValue());
            newStoring.setPrice(Double.parseDouble(price.getText()));
            newStoring.setCurrency(currencyComboBox.getValue());

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
            countTypeComboBox.setValue(countTypeComboBox.getItems().get(0));
            price.setText("");
            currencyComboBox.setValue(currencyComboBox.getItems().get(0));
        });

        HBox wrapStoragable = getHBox();
        wrapStoragable.getChildren().addAll(
                getLabel(STORAGABLE)
                , storagableComboBox
        );

        HBox wrapCount = getHBox();
        wrapCount.getChildren().addAll(
                getLabel(COUNT_TYPE)
                , count
                , countTypeComboBox
        );

        HBox wrapPrice = getHBox();
        wrapPrice.getChildren().addAll(
                getLabel(PRICE)
                , price
                , currencyComboBox
        );

        newStoringNode.getChildren().addAll(
                wrapStoragable
                , wrapCount
                , wrapPrice
                , addButton
        );

        return newStoringNode;
    }

    private Node createList() {
        VBox res = new VBox();
        res.setFillWidth(true);

        ListView<Storing> listView = new ListView<>();

        res.setPadding(new Insets(5, 0, 0, 2));

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

        res.getChildren().add(listView);

        return res;
    }

    private Node createContent() {
        VBox res = new VBox();

        res.setPadding(new Insets(2));

        res.getChildren().addAll(
                createPallete()
                , createList()
        );

        return res;
    }

    private Node createPallete() {
        VBox res = new VBox();
        res.setSpacing(5);
        res.setPadding(new Insets(2));
        res.getChildren().addAll(
                createNewStoringRow()
                , createSortRow()
        );

        res.setStyle("" +
                "-fx-border-width: 1px 0 1px 0;" +
                "-fx-border-color:grey;" +
                "-fx-border-style:solid;" +
                "");

        return res;
    }

    private HBox createSortRow() {
        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.setPadding(new Insets(2));

        hBox.getChildren().addAll(
                getLabel(SORT)
                , getSortComboBox()
        );

        return hBox;
    }

    private Node createTitle() {
        HBox res = new HBox();

        res.getChildren().addAll(
                getLabel(BACKET)
        );

        res.setPadding(new Insets(2));

        return res;
    }

    private Node createTotal() {
        HBox res = new HBox();

        res.setPadding(new Insets(2, 0, 2, 0));

        res.getChildren().addAll(
                getLabel("total")
        );

        res.setStyle("" +
                "-fx-border-width: 1px 0 0 0;" +
                "-fx-border-color:grey;" +
                "-fx-border-style:solid;" +
                "");

        return res;
    }

    public List<Storing> getComposite() {
        return items.getValue();
    }

    private ComboBox<Typable> getCountTypeComboBox() {
        ComboBox<Typable> res = thoth_gui.thoth_lite.components.controls.ComboBox.getInstance();

        try {
            res.setItems(FXCollections.observableList((List<Typable>) ThothLite.getInstance().getDataFromTable(AvaliableTables.COUNT_TYPES)));
            if(!res.getItems().isEmpty()){
                res.setValue(res.getItems().get(0));
            }
        } catch (NotContainsException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        res.setCellFactory(listedListView -> new ListedCell());
        res.setButtonCell(new ListedCell());

        res.setId(COUNT_TYPE);

        res.setMinWidth(80);
        res.setPrefWidth(80);
        res.setMaxWidth(100);

        return res;
    }

    private Label getLabel(String text) {
        Label res = thoth_gui.thoth_lite.components.controls.Label.getInstanse(text);
        return res;
    }

    private ComboBox<Currency> getCurrencyComboBox() {
        ComboBox<Currency> res = thoth_gui.thoth_lite.components.controls.ComboBox.getInstance();

        try {
            res.setItems(FXCollections.observableList((List<Currency>) ThothLite.getInstance().getDataFromTable(AvaliableTables.CURRENCIES)));
            if (!res.getItems().isEmpty()) {
                res.setValue(res.getItems().get(0));
            }
        } catch (NotContainsException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        res.setCellFactory(listedListView -> new CurrencyCell());
        res.setButtonCell(new CurrencyCell());

        res.setId(CURRENCY);

        res.setMinWidth(75);
        res.setPrefWidth(75);
        res.setMaxWidth(75);

        return res;
    }

    private HBox getHBox(){
        HBox hBox = new HBox();

        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(5);

        return hBox;
    }

    private ComboBox<SORT_BY> getSortComboBox() {
        ComboBox<SORT_BY> res = thoth_gui.thoth_lite.components.controls.ComboBox.getInstance();

        for (SORT_BY sort : SORT_BY.values()) {
            res.getItems().add(sort);
        }

        res.valueProperty().addListener((observableValue, sort_by, t1) -> {

            switch (res.getValue()) {
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

        });

        res.setValue(SORT_BY.ID_UP);

        res.setMinWidth(120);
        res.setPrefWidth(120);
        res.setMaxWidth(120);

        res.setCellFactory(sort_byListView -> new SortedCell());
        res.setButtonCell(new SortedCell());

        return res;
    }

    private ComboBox<Storagable> getStoragableComboBox() {

        ComboBox<Storagable> res = thoth_gui.thoth_lite.components.controls.ComboBox.getInstance();

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

        res.setId(STORAGABLE);

        res.setMinWidth(120);
        res.setPrefWidth(120);
        res.setMaxWidth(120);
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

        if (text != null){
            res.setText(text);
        }else{
            res.setText( String.valueOf(0.0) );
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

    private class CurrencyCell
            extends ListCell<Currency> {
        @Override
        protected void updateItem(Currency currency, boolean b) {
            if (currency != null) {
                super.updateItem(currency, b);
                setText(currency.getCurrency());
            }
        }
    }

    private class ListedCell
            extends ListCell<Typable> {

        @Override
        protected void updateItem(Typable typable, boolean b) {
            if (typable != null) {
                super.updateItem(typable, b);

                setText(typable.getValue());
            }
        }
    }

    private class SortedCell
            extends ListCell<SORT_BY> {
        @Override
        protected void updateItem(SORT_BY sort_by, boolean b) {
            if (sort_by != null) {
                super.updateItem(sort_by, b);
                setText(sort_by.id);
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
            Button res = new Button(
                    new ImageView(
                            new Image(
                                    getClass().getResource(thoth_gui.thoth_styleconstants.Image.TRASH).toExternalForm(), 19, 19, true, true
                            )
                    )
            );

            res.setDisable(!identifiableIsNew);

            res.setOnAction(actionEvent -> {
                items.remove(storing);
            });

            return res;
        }

        private Pane createPoint() {
            VBox vBox = new VBox();

            vBox.getChildren().add(
                    new ImageView(
                            new Image(
                                    getClass().getResource(thoth_gui.thoth_styleconstants.Image.POINT).toExternalForm(), 9, 9, true, true
                            )
                    )
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
                    new Label(storing.getCurrency().getCurrency())
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
