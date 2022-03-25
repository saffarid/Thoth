package thoth_gui.thoth_lite.components.scenes.db_elements_view.identifiable_card;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import javafx.event.ActionEvent;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import layout.basepane.BorderPane;
import layout.basepane.GridPane;
import layout.basepane.HBox;
import layout.basepane.VBox;

import thoth_core.thoth_lite.db_data.db_data_element.properties.*;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_core.thoth_lite.ThothLite;

import thoth_core.thoth_lite.info.SystemInfoKeys;
import thoth_gui.thoth_lite.components.controls.*;
import thoth_gui.thoth_lite.components.controls.combo_boxes.ComboBox;
import thoth_gui.thoth_lite.components.controls.combo_boxes.FinanceComboBox;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortBy;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortPane;
import thoth_gui.thoth_lite.components.converters.StringDoubleConverter;
import thoth_gui.thoth_lite.main_window.Workspace;
import thoth_gui.thoth_lite.tools.Properties;
import thoth_gui.thoth_lite.tools.TextCase;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;

import thoth_gui.thoth_styleconstants.svg.Images;

import tools.BorderWrapper;
import tools.SvgWrapper;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompositeListView
        extends BorderPane {

    private enum SORT_BY implements SortBy {
        ID_UP("sort_by_id_up"),
        ID_DOWN("sort_by_id_down"),
        COUNT_UP("sort_by_count_up"),
        COUNT_DOWN("sort_by_count_down"),
        PRICE_UP("sort_by_price_up"),
        PRICE_DOWN("sort_by_price_down");
        private String id;

        SORT_BY(String id) {
            this.id = id;
        }

        @Override
        public String getSortName() {
            return id;
        }
    }

    private final static String USE_COURSE_FROM_FINANCE = Properties.getString("use_course_from_finance", TextCase.NORMAL);
    private final static String BACKET = Properties.getString("backet", TextCase.NORMAL);
    private final static String TITLE = Properties.getString("composite", TextCase.NORMAL);
    private final static String STORAGABLE = Properties.getString("storagable", TextCase.NORMAL);
    private final static String COUNT = Properties.getString("count", TextCase.NORMAL);
    private final static String POS_COUNT = Properties.getString("pos_count", TextCase.NORMAL);
    private final static String CURRENCIES = Properties.getString("currencies", TextCase.NORMAL);
    private final static String COUNT_TYPE = Properties.getString("count_type", TextCase.NORMAL);
    private final static String PRICE_PER_UNIT = Properties.getString("price_per_unit", TextCase.NORMAL);
    private final static String PRICE = Properties.getString("price", TextCase.NORMAL);
    private final static String DEF_CURRENCY = Properties.getString("def_currency", TextCase.NORMAL);
    private final static String COURSE = Properties.getString("course", TextCase.NORMAL);
    private final static String CONV_PRICE_PER_UNIT = Properties.getString("conv_price_per_unit", TextCase.NORMAL);
    private final static String CONV_PRICE = Properties.getString("conv_price", TextCase.NORMAL);

    private final static String SEARCH = Properties.getString("search", TextCase.NORMAL);
    private final static String TOTAL = Properties.getString("total", TextCase.NORMAL);

    private final String TOTAL_TEMPLATE = TOTAL + ":\t" + POS_COUNT + ": %1$s\t" + PRICE + ": %2$s";

    private final static double widthPallete = 50;
    private final static double maxWidthPallete = 200;

    private final static double sizeSvg = 20;
    private final static double sizeSvgViewbox = 30;

    private final controls.Label title = Label.getInstanse(BACKET).setPadding(2);
    private final controls.Label total = Label.getInstanse().setPadding(2);

    private final SortPane sortPane = SortPane.getInstance()
            .setSortItems(SORT_BY.values())
            .setSortMethod(this::sort)
            .setCell();
    private controls.ComboBox<Finance> financeList = FinanceComboBox.getInstance();

    private final boolean identifiableIsNew;

    private final SimpleDoubleProperty countProperty = new SimpleDoubleProperty();
    private final SimpleDoubleProperty courseProperty = new SimpleDoubleProperty();
    private final SimpleDoubleProperty pricePerUnitProperty = new SimpleDoubleProperty();
    private final SimpleDoubleProperty priceProperty = new SimpleDoubleProperty();
    private final SimpleDoubleProperty convPricePerUnitProperty = new SimpleDoubleProperty();
    private final SimpleDoubleProperty convPriceProperty = new SimpleDoubleProperty();
    private final SimpleStringProperty totalProperty = new SimpleStringProperty();

    private final controls.ComboBox<Storagable> storagableComboBox = getStoragableComboBox();
    private final controls.TextField count = getTextField();
    private final controls.TextField countType = TextField.getInstance();
    private final controls.TextField pricePerUnit = getTextField();
    private final controls.TextField price = getTextField();
    private final controls.ComboBox<Finance> financeComboBox = FinanceComboBox.getInstance();
    private final controls.TextField course = getTextField();
    private final javafx.scene.control.CheckBox courseCurrency = CheckBox.getInstance();
    private final controls.TextField convertedPricePerUnit = getTextField();
    private final controls.TextField convertedPrice = getTextField();
    private final controls.TextField convertedCurrency = TextField.getInstance( String.valueOf( ThothLite.getInstance().getInfoField(SystemInfoKeys.SYSTEM_CURRENCY_CODE)) );

    private final controls.Button add =
            Button.getInstance(SvgWrapper.getInstance(Images.PLUS(), sizeSvg, sizeSvg, sizeSvgViewbox, sizeSvgViewbox), this::addItem)
                    .setTool(Tooltip.getInstance("add"));

    private final List<Storing> storings;
    private final SimpleListProperty<Storing> items;

    private final controls.ListView<Storing> listView = ListView.getInstance();

    public CompositeListView(
            List<Storing> items
            , boolean identifiableIsNew
    ) {
        super();
        this.identifiableIsNew = identifiableIsNew;

        if (this.identifiableIsNew) {
            storings = items;
            this.items = new SimpleListProperty<>(FXCollections.observableList(new LinkedList<>()));
        } else {
            storings = null;
            this.items = new SimpleListProperty<>(FXCollections.observableList(items));
        }

        total.textProperty().bind(totalProperty);

        setTop(title);
        setCenter(createGrid());
    }

    public void close() {
        storagableComboBox.close();
        financeComboBox.close();
    }

    private Node createGrid() {
        GridPane content = new GridPane();

        content.setBorder(
                new BorderWrapper()
                        .addTopBorder(1)
//                        .addBottomBorder(1)
                        .setColor(Color.GREY)
                        .setStyle(BorderStrokeStyle.SOLID)
                        .commit()
        );

        content.setPadding(new Insets(0));

        if (identifiableIsNew) {
            content
                    .addRow(Priority.ALWAYS)
                    .addColumn(widthPallete, widthPallete, maxWidthPallete, Priority.ALWAYS, HPos.RIGHT, true)
                    .addColumn(Priority.ALWAYS);

            content.add(createPalette(), 0, 0);
            content.add(createList(), 1, 0);
        } else {
            content
                    .addRow(Priority.ALWAYS)
                    .addColumn(Priority.ALWAYS);

            content.add(createList(), 0, 0);
        }

        return content;
    }

    private Node createPalette() {
        BorderPane palette = new BorderPane();
        palette.setDisable(!identifiableIsNew);

        VBox controls = new VBox();
        controls.setPadding(new Insets(0, 2, 0, 0));
        palette.setBorder(
                new BorderWrapper()
                        .addRightBorder(1)
                        .setStyle(BorderStrokeStyle.SOLID)
                        .setColor(Color.GREY)
                        .commit()
        );

        Bindings.bindBidirectional(count.textProperty(), countProperty, new StringDoubleConverter());
        Bindings.bindBidirectional(course.textProperty(), courseProperty, new StringDoubleConverter());
        Bindings.bindBidirectional(pricePerUnit.textProperty(), pricePerUnitProperty, new StringDoubleConverter());

        storagableComboBox.valueProperty().addListener((observableValue, storagable, t1) -> {
            if (t1 != null) {
                countType.setText(t1.getCountType().getValue());
            }
        });

        countType.setEditable(false);
        convertedCurrency.setEditable(false);
        convertedPricePerUnit.setEditable(false);
        price.setEditable(false);
        convertedPrice.setEditable(false);

        financeComboBox.valueProperty().addListener((observableValue, finance, t1) -> {
            if (t1 == null) return;
            courseCurrency.setSelected(
                    t1.getCurrency().getCurrencyCode().equals(
                            ThothLite.getInstance().getInfoField(SystemInfoKeys.SYSTEM_CURRENCY_CODE)
                    )
            );
        });

        course.editableProperty().bind(courseCurrency.selectedProperty().not());

        courseCurrency.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1) {
                course.setText(financeComboBox.getValue().getCourse().toString());
            }
        });

        priceProperty.bind(countProperty.multiply(pricePerUnitProperty));
        convPricePerUnitProperty.bind(pricePerUnitProperty.multiply(courseProperty));
        convPriceProperty.bind(countProperty.multiply(convPricePerUnitProperty));

        price.textProperty().bind(priceProperty.asString());
        convertedPricePerUnit.textProperty().bind(convPricePerUnitProperty.asString());
        convertedPrice.textProperty().bind(convPriceProperty.asString());

        controls.getChildren().addAll(
                Row.getInstance(
                        Label.getInstanse(STORAGABLE)
                        , storagableComboBox
                )
                , Row.getInstance(
                        Label.getInstanse(COUNT)
                        , count
                        , countType
                )
                , Row.getInstance(
                        Label.getInstanse(CURRENCIES)
                        , financeComboBox
                )
                , Row.getInstance(
                        new Pane()
                        , wrap(Label.getInstanse(PRICE_PER_UNIT), pricePerUnit)
                        , wrap(Label.getInstanse(PRICE), price)
                )
                , Row.getInstance(
                        Label.getInstanse(COURSE)
                        , course
                        , courseCurrency
                )
                , Row.getInstance(
                        Label.getInstanse(DEF_CURRENCY)
                        , convertedCurrency
                )
                , Row.getInstance(
                        new Pane()
                        , wrap(Label.getInstanse(PRICE_PER_UNIT), convertedPricePerUnit)
                        , wrap(Label.getInstanse(PRICE), convertedPrice)
                )
        );

        add.disableProperty().bind(storagableComboBox.valueProperty().isNull()
                .or(countProperty.lessThanOrEqualTo(StringDoubleConverter.countMin + 0.0001))
                .or(pricePerUnitProperty.lessThanOrEqualTo(StringDoubleConverter.priceMin)))
        ;

        HBox buttons = new HBox();
        buttons.getChildren().addAll(
                add
        );

        palette.setCenter(controls);
        palette.setBottom(buttons);

        return palette;
    }

    private void addItem(ActionEvent event) {
        LocalStoringCell newStoring = new LocalStoringCell(
                storagableComboBox.getValue(),
                Double.parseDouble(count.getText()),
                storagableComboBox.getValue().getCountType(),
                Double.parseDouble(pricePerUnit.getText()),
                financeComboBox.getValue(),
                Double.parseDouble(course.getText())
        );

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
            items.add(newStoring);
            storings.add(newStoring);
        }

        storagableComboBox.setValue(null);
        count.setText("0.0");
        pricePerUnit.setText("0.0");
    }

    private Node createList() {
        BorderPane res = new BorderPane();
        res.setPadding(new Insets(2, 0, 2, 2));

        listView.setBorder(
                new BorderWrapper()
                        .addTopBorder(1)
                        .addBottomBorder(1)
                        .setColor(Color.GREY)
                        .setStyle(BorderStrokeStyle.SOLID)
                        .commit()
        );
        listView.setCellFactory(storingListView -> new CompositeCell());
        items.addListener((observableValue, storings, t1) -> {

            CompletableFuture.runAsync(() -> {
                double price = 0;
                for (Storing storing : items.getValue()) {
                    price += storing.getPrice().doubleValue() * storing.getCount().doubleValue();
                }
                double finalPrice = price;
                Platform.runLater(() ->
                        totalProperty.setValue(
                                String.format(TOTAL_TEMPLATE, String.valueOf(items.getValue().size()), String.valueOf(finalPrice))
                        ));
            });
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
        listView.setPadding(new Insets(2));

        listView.setCellFactory(storingListView -> new CompositeCell());

        res.setTop(createToolsList());
        res.setCenter(listView);
        res.setBottom(total);

        BorderPane.setMargin(listView, new Insets(1, 0, 2, 0));
        BorderPane.setAlignment(listView, Pos.CENTER_LEFT);

        return res;
    }

    private Node createToolsList() {
        GridPane toolsList = new GridPane()
                .addRow(Priority.NEVER)
                .addColumn(Priority.NEVER)
                .addColumn(Priority.NEVER);
        toolsList.setHgap(5);
        toolsList.setPadding(new Insets(0, 0, 2, 0));
        sortPane.setValue(SORT_BY.ID_UP);

        toolsList.add(sortPane, 0, 0);

        return toolsList;
    }

    public List<Storing> getComposite() {
        return items.getValue();
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
            case PRICE_UP: {
                items.sort((o1, o2) -> o1.getPrice().compareTo(o2.getPrice()));
                break;
            }
            case PRICE_DOWN: {
                items.sort((o1, o2) -> o2.getPrice().compareTo(o1.getPrice()));
                break;
            }
        }
    }

    private controls.ComboBox<Storagable> getStoragableComboBox() {

        controls.ComboBox<Storagable> res = ComboBox.getInstance();

        ThothLite.getInstance().subscribeOnTable(AvaliableTables.STORAGABLE, res);

        res.setCellFactory(storagableListView -> new StoragableCell());
        res.setButtonCell(new StoragableCell());

        return res;
    }

    private controls.TextField getTextField() {
        controls.TextField res = TextField.getInstance();

        res.setText(String.valueOf(0.0));

        res.textProperty().addListener((observableValue, s, t1) -> {
            if (t1.equals("")) return;

            Pattern pattern = Pattern.compile("^[0-9]*[.]?[0-9]*$");
            Matcher matcher = pattern.matcher(t1);

            if (!matcher.matches()) {
                res.setText(s);
            }
        });

        res.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1) {
                if (res.getText().equals("")) {
                    res.setText(String.valueOf(0.0));
                }
            }
        });

        return res;
    }

    public void open() {
        ThothLite.getInstance().subscribeOnTable(AvaliableTables.STORAGABLE, storagableComboBox);
        ThothLite.getInstance().subscribeOnTable(AvaliableTables.CURRENCIES, financeComboBox);
    }

    private VBox wrap(
            Node title,
            Node node
    ) {
        VBox res = new VBox();
        res.setSpacing(2);
        res.getChildren().addAll(title, node);
        return res;
    }

    private class StoragableCell
            extends ListCell<Storagable> {

        private final String TEMPLATE = "%1s-%2s";

        @Override
        protected void updateItem(Storagable storagable, boolean b) {
            if (storagable != null) {
                super.updateItem(storagable, b);

                setGraphic(
                        Label.getInstanse(
                                String.format(TEMPLATE, storagable.getId(), storagable.getName())
                        )
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

        private final static String COUNT_TEMPLATE = "%1$.4f %2$s";
        private final static String FINANCE_TEMPLATE = "%1$.2f %2$s";
        private final static String CONV_FINANCE_TEMPLATE = "%1$s / %2$s";

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

        private controls.Button createRemoveButton() {
            controls.Button res = Button.getInstance(SvgWrapper.getInstance(Images.TRASH(), 19, 19));

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

            res
                    .addRow(Priority.NEVER)
                    .addColumn(100, 100, Double.MAX_VALUE, Priority.SOMETIMES, HPos.CENTER, true) //Артикул
                    .addColumn(100, 100, Double.MAX_VALUE, Priority.SOMETIMES, HPos.CENTER, true) //Наименование
                    .addColumn(50, 50, Double.MAX_VALUE, Priority.ALWAYS, HPos.CENTER, true) //Кол-во
                    .addColumn(50, 50, Double.MAX_VALUE, Priority.ALWAYS, HPos.CENTER, true) //Цена
                    .addColumn(50, 50, Double.MAX_VALUE, Priority.ALWAYS, HPos.CENTER, true) //Цена итого
            ;

            res.add(Label.getInstanse(storing.getStoragable().getId()), 0, 0);
            res.add(Label.getInstanse(storing.getStoragable().getName()), 1, 0);
            res.add(Label.getInstanse(String.format(COUNT_TEMPLATE, storing.getCount(), storing.getStoragable().getCountType().getValue())), 2, 0);

            if (storing.getCurrency().getCurrency().getCurrencyCode().equals(
                    ThothLite.getInstance().getInfoField(SystemInfoKeys.SYSTEM_CURRENCY_CODE)
            )) {
                res.add(
                        Label.getInstanse(
                                String.format(FINANCE_TEMPLATE, storing.getPrice(), storing.getCurrency().getCurrency().getCurrencyCode())
                        )
                        , 3, 0
                );
                res.add(
                        Label.getInstanse(
                                String.format(FINANCE_TEMPLATE, storing.getPrice() * storing.getCount(), storing.getCurrency().getCurrency().getCurrencyCode())
                        )
                        , 4, 0
                );
            } else {
                res.add(
                        Label.getInstanse(
                                String.format(
                                        CONV_FINANCE_TEMPLATE,
                                        String.format(FINANCE_TEMPLATE, storing.getPrice(), storing.getCurrency().getCurrency().getCurrencyCode()),
                                        String.format(FINANCE_TEMPLATE, storing.getPrice() * storing.getCourse(), ((Currency) ThothLite.getInstance().getInfoField(SystemInfoKeys.SYSTEM_CURRENCY_CODE)).getCurrencyCode())
                                )
                        )
                        , 3, 0
                );
                res.add(
                        Label.getInstanse(
                                String.format(
                                        CONV_FINANCE_TEMPLATE,
                                        String.format(FINANCE_TEMPLATE, storing.getPrice().doubleValue() * storing.getCount().doubleValue(), storing.getCurrency().getCurrency().getCurrencyCode()),
                                        String.format(FINANCE_TEMPLATE, storing.getPrice().doubleValue() * storing.getCount().doubleValue() * storing.getCourse().doubleValue(), ((Currency) ThothLite.getInstance().getInfoField(SystemInfoKeys.SYSTEM_CURRENCY_CODE)).getCurrencyCode())
                                )
                        )
                        , 4, 0
                );
            }


            return res;
        }
    }

    private class LocalStoringCell
            implements Storing {

        private String id;
        private Storagable product;
        private Double count;
        private Typable countType;
        private Double price;
        private Finance currency;
        private Double course;

        public LocalStoringCell(
                Storagable product
                , Double count
                , Typable countType
                , Double price
                , Finance currency
                , Double course
        ) {
            this.id = id;
            this.product = product;
            this.count = count;
            this.countType = countType;
            this.price = price;
            this.currency = currency;
            this.course = course;
        }

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
        public Double getCourse() {
            return course;
        }

        @Override
        public void setCourse(Double course) {
            this.course = course;
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

    }
}
