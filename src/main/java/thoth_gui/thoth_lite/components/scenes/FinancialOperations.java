package thoth_gui.thoth_lite.components.scenes;

import controls.table_view.TableView;
import javafx.application.Platform;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;

import javafx.scene.layout.BorderStrokeStyle;
import javafx.util.Callback;
import layout.basepane.BorderPane;
import layout.basepane.HBox;
import org.json.simple.parser.ParseException;
import thoth_core.thoth_lite.ThothLite;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Finance;
import thoth_core.thoth_lite.db_data.db_data_element.properties.FinancialAccounting;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Typable;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_gui.config.Config;
import thoth_gui.thoth_lite.components.controls.Button;
import thoth_gui.thoth_lite.components.controls.ButtonBar;
import thoth_gui.thoth_lite.components.controls.Label;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortBy;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortPane;
import thoth_gui.thoth_lite.components.scenes.db_elements_view.identifiable_card.IdentifiableCard;
import thoth_gui.thoth_lite.main_window.Workspace;
import thoth_gui.thoth_styleconstants.color.ColorTheme;
import thoth_gui.thoth_styleconstants.svg.Images;
import tools.BorderWrapper;
import tools.SvgWrapper;
import window.Closeable;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;
import java.util.stream.Collectors;

public class FinancialOperations
        extends ThothSceneImpl
        implements Flow.Subscriber<List<FinancialAccounting>> {

    private enum SORT_BY implements SortBy {
        MONTH("last_month"),
//        CURRENT_MONTH("current_month"),
        QUARTER("quarter"),
        HALFYEAR("halfyear"),
        YEAR("year"),
        ALL("all");
        private String sortCode;

        SORT_BY(String sortCode) {
            this.sortCode = sortCode;
        }

        @Override
        public String getSortName() {
            return sortCode;
        }
    }

    private enum Columns {
        CATEGORY,
        TOTAL
    }

    private final String columnNamePattern = "%1s.%2d";

    /**
     * Объект подписки на обновления
     */
    private Flow.Subscription subscription;

    /**
     * Отображаемая таблица
     */
    private AvaliableTables table;

    /**
     * Панель сортировки
     */
    private SortPane sortPane;

    /**
     * Таблица с суммарными данными
     */
    private TableView<HashMap<String, Object>> finOpSumTable;

    /**
     * Таблица с суммарными данными
     */
    private TableView<FinancialAccounting> finOpHistoryTable;

    /**
     * Исходные данные для отображения в таблице
     */
    private SimpleListProperty<FinancialAccounting> initialData;

    /**
     * Подготовленные данные для отображения в таблице
     */
    private SimpleListProperty<HashMap<String, Object>> data;

    /**
     * Ключи доступа к текущим столбцам
     */
    private Set<LocalDate> columnKeys;

    /* ---  --- */

    public FinancialOperations(AvaliableTables table) {
        this.table = table;

        // Запрос типов финансовых операций
//        try {
//            if (table == AvaliableTables.EXPENSES) {
//                categories = (List<Typable>) ThothLite.getInstance().getDataFromTable(AvaliableTables.EXPENSES_TYPES);
//            } else {
//                categories = (List<Typable>) ThothLite.getInstance().getDataFromTable(AvaliableTables.INCOMES_TYPES);
//            }
//        } catch (NotContainsException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        init();

        tools = new SimpleObjectProperty<>(createToolsNode());
        content = new SimpleObjectProperty<>(createContent());
    }

    @Override
    public void close() {
    }

    private Node createContent() {
//        tableView = new TableView();

        contentNode = new BorderPane(finOpSumTable);
        return contentNode;
    }

    private TableColumn<HashMap<String, Object>, String> getTableColumn(String title, String key) {
        TableColumn<HashMap<String, Object>, String> column = new TableColumn<>();

        BorderPane titleNode = new BorderPane(Label.getInstanse(title));
//        titleNode.setBackground(
//                new BackgroundWrapper()
//                        .setColor(theme.PRIMARY())
//                        .commit()
//        );
//        titleNode.setBorder(
//                new BorderWrapper()
//                        .addBottomBorder(2)
//                        .setColor(theme.SECONDARY())
//                        .setStyle(BorderStrokeStyle.SOLID)
//                        .commit()
//        );

        column.setGraphic(titleNode);

//        column.setCellFactory(data -> new FinCell());
        column.setCellValueFactory(data -> {
            Object value = data.getValue().get(key);
            if (value == null) {
                return new SimpleStringProperty(String.valueOf(0));
            }
            return new SimpleStringProperty(String.valueOf(value));
        });

        return column;
    }

    private Node createToolsNode() {
        toolsNode = new BorderPane();
        toolsNode.setLeft(sortPane);
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(0, 0, 0, 5));
        hBox.getChildren().addAll(
                Button.getInstance(
                        SvgWrapper.getInstance(Images.HISTORY(), svgWidthTool, svgHeightTool, svgViewBoxWidthTool, svgViewBoxHeightTool),
                        event -> contentNode.setCenter(finOpHistoryTable)
                ),
                Button.getInstance(
                        SvgWrapper.getInstance(Images.STATISTIC(), svgWidthTool, svgHeightTool, svgViewBoxWidthTool, svgViewBoxHeightTool),
                        event -> contentNode.setCenter(finOpSumTable)
                )
        );
        toolsNode.setCenter(
                hBox
        );
        toolsNode.setRight(
                Button.getInstance(
                        SvgWrapper.getInstance(Images.PLUS(), svgWidthTool, svgHeightTool, svgViewBoxWidthTool, svgViewBoxHeightTool),
                        event -> Workspace.getInstance().setNewScene(IdentifiableCard.getInstance(table, null))
                )
        );
        return toolsNode;
    }

    private void init() {
        finOpSumTable = thoth_gui.thoth_lite.components.controls.table_view.TableView.getInstance();
        finOpSumTable.setPlaceholder(Label.getInstanse("no_elements"));

        initHistoryTable();

        data = new SimpleListProperty<>();
        initialData = new SimpleListProperty<>();

        sortPane = SortPane.getInstance()
                .setSortItems(SORT_BY.values())
                .setCell()
                .setValue(SORT_BY.QUARTER)
                .setSortMethod((observableValue, sortBy, t1) -> initialDataChange())
        ;

        initialData.addListener((ListChangeListener<? super FinancialAccounting>) change -> initialDataChange());
        data.addListener(this::showData);

        try {
            initialData.setValue(
                    FXCollections.observableList((List<FinancialAccounting>) ThothLite.getInstance().getDataFromTable(table))
            );
            ThothLite.getInstance().subscribeOnTable(table, this);
        } catch (NotContainsException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        initStyle();
    }

    private void initHistoryTable(){
        finOpHistoryTable = thoth_gui.thoth_lite.components.controls.table_view.TableView.getInstance();
        finOpHistoryTable.setPlaceholder(Label.getInstanse("no_elements"));

        controls.table_view.TableColumn<FinancialAccounting, LocalDate> dateColumn = thoth_gui.thoth_lite.components.controls.table_view.TableColumn.getInstance();
        dateColumn.setGraphic(new BorderPane(Label.getInstanse("date")));
        dateColumn.setCellValueFactory(finance -> {
            return new SimpleObjectProperty<LocalDate>(finance.getValue().getDate());
        });
        dateColumn.setCellFactory(finance -> new TableCell<>(){
            @Override
            protected void updateItem(LocalDate localDate, boolean b) {
                if(localDate != null) {
                    super.updateItem(localDate, b);
//                    setText( String.format("%1s.%2s.%3s", localDate.getDayOfMonth(), localDate.getMonth().name(), localDate.getYear()) );
                    setText( localDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)) );
                }
            }
        });

        controls.table_view.TableColumn<FinancialAccounting, Typable> catColumn = thoth_gui.thoth_lite.components.controls.table_view.TableColumn.getInstance();
        catColumn.setGraphic(new BorderPane(Label.getInstanse("category")));
        catColumn.setCellValueFactory(finance -> {
            return new SimpleObjectProperty<Typable>(finance.getValue().getCategory());
        });
        catColumn.setCellFactory(finance -> new TableCell<>(){
            @Override
            protected void updateItem(Typable typable, boolean b) {
                if(typable != null) {
                    super.updateItem(typable, b);
                    setText( typable.getValue() );
                }
            }
        });

        controls.table_view.TableColumn<FinancialAccounting, Double> valueColumn = thoth_gui.thoth_lite.components.controls.table_view.TableColumn.getInstance();
        valueColumn.setGraphic(new BorderPane(Label.getInstanse("value")));
        valueColumn.setCellValueFactory(finance -> {
            return new SimpleObjectProperty<Double>(finance.getValue().getValue());
        });
        valueColumn.setCellFactory(finance -> new TableCell<>(){
            @Override
            protected void updateItem(Double value, boolean b) {
                if(value != null) {
                    super.updateItem(value, b);
                    setText( value.toString() );
                }
            }
        });

        finOpHistoryTable.getColumns().addAll(dateColumn, catColumn, valueColumn);

    }

    private void initialDataChange() {
        CompletableFuture.supplyAsync(() -> {
            HashMap<Typable, HashMap<String, Double>> data = new HashMap<>();

            columnKeys = new HashSet<>();

            List<FinancialAccounting> d = initialData.getValue();
            LocalDate now = LocalDate.now();
            LocalDate startDate;
            //Определяем начало расчетного периода
            if (sortPane != null) {
                switch ((SORT_BY) sortPane.getValue()) {
                    case MONTH:
                    default: {
                        startDate = LocalDate.of(now.getYear(), now.getMonth(), 1).minusDays(1);
                        break;
                    }
//                    case CURRENT_MONTH:{
//                        startDate = LocalDate.of(now.getYear(), now.getMonth(), 1).minusDays(1);
//                        break;
//                    }
                    case QUARTER: {
                        LocalDate minusMonths = now.minusMonths(2);
                        startDate = LocalDate.of(minusMonths.getYear(), minusMonths.getMonth(), 1).minusDays(1);
                        break;
                    }
                    case HALFYEAR: {
                        LocalDate minusMonths = now.minusMonths(5);
                        startDate = LocalDate.of(minusMonths.getYear(), minusMonths.getMonth(), 1).minusDays(1);
                        break;
                    }
                    case YEAR: {
                        LocalDate minusMonths = now.minusMonths(11);
                        startDate = LocalDate.of(minusMonths.getYear(), minusMonths.getMonth(), 1).minusDays(1);
                        break;
                    }
                    case ALL: {
                        startDate = LocalDate.of(2000, 1, 1).minusDays(1);
                        break;
                    }
                }
            } else {
                startDate = LocalDate.of(now.getYear(), now.getMonth(), 1).minusDays(1);
            }
            d = d.stream()
                    .filter(finOp -> finOp.getDate().isAfter(startDate))
                    .sorted((o1, o2) -> o1.getDate().compareTo(o2.getDate()))
                    .collect(Collectors.toList());

            List<FinancialAccounting> finalD = d;
            Platform.runLater(() -> {
                finOpHistoryTable.setItems(FXCollections.observableList(finalD));
                finOpHistoryTable.refresh();
            });

            for (FinancialAccounting finOp : d) {
                if (!data.containsKey(finOp.getCategory())) {
                    data.put(finOp.getCategory(), new HashMap<>());
                }

                HashMap<String, Double> row = data.get(finOp.getCategory());

                LocalDate finOpDate = finOp.getDate();
                String key = LocalDate.of(finOpDate.getYear(), finOpDate.getMonth(), 1).format(DateTimeFormatter.ISO_DATE);
                if (sortPane != null && (SORT_BY) sortPane.getValue() == SORT_BY.ALL) {
                    key = LocalDate.of(finOpDate.getYear(), 1, 1).format(DateTimeFormatter.ISO_DATE);
                }

                columnKeys.add(LocalDate.parse(key));

                if (row.containsKey(key)) {
                    row.put(key, row.get(key) + finOp.getValue());
                } else {
                    row.put(key, finOp.getValue());
                }
            }

            List<HashMap<String, Object>> res = new LinkedList<>();
            for (Typable type : data.keySet()) {
                HashMap<String, Object> row = new HashMap<>();
                row.put(Columns.CATEGORY.name(), type.getValue());
                row.put(Columns.TOTAL.name(), 0.);
                HashMap<String, Double> stringDoubleHashMap = data.get(type);
                for (String k : stringDoubleHashMap.keySet()) {
                    row.put(k, stringDoubleHashMap.get(k));
                    row.put(Columns.TOTAL.name(), ((Double) row.get(Columns.TOTAL.name())) + stringDoubleHashMap.get(k));
                }
                res.add(row);
            }

            return res;
        }).thenAccept(res -> this.data.setValue(FXCollections.observableList(res)));
    }

    private void initStyle() {
        finOpSumTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        finOpHistoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        tableView.setRowFactory(data -> new FinRow());
    }

    @Override
    public void setCloseable(Closeable closeable) {

    }

    private void showData(ListChangeListener.Change<? extends HashMap<String, Object>> change) {
        Platform.runLater(() -> {
            finOpSumTable.getColumns().clear();
            //Формируем колонку с категориями
            TableColumn<HashMap<String, Object>, String> category = getTableColumn(Columns.CATEGORY.name(), Columns.CATEGORY.name());
            finOpSumTable.getColumns().add(category);
            if(!data.isEmpty()){

                finOpSumTable.setItems(data);

                //Формируем колонки по датам
                for (LocalDate date : columnKeys.stream()
                        .sorted(LocalDate::compareTo)
                        .collect(Collectors.toList())) {
                    if (sortPane != null && sortPane.getValue() == SORT_BY.ALL) {
                        finOpSumTable.getColumns().add(getTableColumn(
                                String.valueOf(date.getYear())
                                , date.format(DateTimeFormatter.ISO_DATE)));
                    } else {
                        finOpSumTable.getColumns().add(getTableColumn(
                                String.format(this.columnNamePattern, date.getMonth().name(), date.getYear())
                                , date.format(DateTimeFormatter.ISO_DATE)));
                    }
                }

                //формируем итоговый столбец если стобцов по датам больше 1
                if (columnKeys.size() > 1) {
                    TableColumn<HashMap<String, Object>, String> total = getTableColumn(Columns.TOTAL.name(), Columns.TOTAL.name());
                    finOpSumTable.getColumns().add(total);
                }

            }
            finOpSumTable.refresh();

        });
    }

    /* --- Работа с подпиской --- */

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(List<FinancialAccounting> item) {
        initialData.setValue(FXCollections.observableList(item));
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }


    private class FinCell
            extends TableCell<HashMap<String, Object>, String> {

        @Override
        protected void updateItem(String s, boolean b) {
            if (s != null) {
                super.updateItem(s, b);
                BorderPane node = new BorderPane(Label.getInstanse(s));
                setGraphic(node);
            }
        }
    }
}
