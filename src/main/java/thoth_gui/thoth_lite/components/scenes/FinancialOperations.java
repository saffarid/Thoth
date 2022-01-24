package thoth_gui.thoth_lite.components.scenes;

import javafx.application.Platform;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import layout.basepane.BorderPane;
import main.Main;
import org.json.simple.parser.ParseException;
import thoth_core.thoth_lite.ThothLite;
import thoth_core.thoth_lite.db_data.db_data_element.properties.FinancialAccounting;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Typable;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_gui.config.Config;
import thoth_gui.thoth_lite.components.controls.Button;
import thoth_gui.thoth_lite.components.controls.Label;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortBy;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortPane;
import thoth_gui.thoth_lite.components.scenes.db_elements_view.identifiable_card.IdentifiableCard;
import thoth_gui.thoth_lite.main_window.Workspace;
import thoth_gui.thoth_styleconstants.color.ColorTheme;
import thoth_gui.thoth_styleconstants.color.Dark;
import thoth_gui.thoth_styleconstants.svg.Images;
import tools.BackgroundWrapper;
import tools.BorderWrapper;
import tools.SvgWrapper;
import window.Closeable;

import javax.print.attribute.standard.PresentationDirection;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class FinancialOperations
        extends ThothSceneImpl
        implements Flow.Subscriber<List<FinancialAccounting>> {

    private ColorTheme theme;

    private enum SORT_BY implements SortBy {
        MONTH("last_month"),
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
     * Таблица с данными
     */
    private TableView<HashMap<String, Object>> tableView;

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

        contentNode = new BorderPane(tableView);
        return contentNode;
    }

    private TableColumn<HashMap<String, Object>, String> getTableColumn(String title, String key) {
        TableColumn<HashMap<String, Object>, String> column = new TableColumn<>();

        BorderPane titleNode = new BorderPane(Label.getInstanse(title));
        titleNode.setBackground(
                new BackgroundWrapper()
                        .setColor(theme.PRIMARY())
                        .commit()
        );
        titleNode.setBorder(
                new BorderWrapper()
                        .addBottomBorder(2)
                        .setColor(theme.SECONDARY())
                        .setStyle(BorderStrokeStyle.SOLID)
                        .commit()
        );

        column.setGraphic(titleNode);

        column.setCellFactory(data -> new FinCell());
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
        toolsNode.setRight(
                Button.getInstance(
                        SvgWrapper.getInstance(Images.PLUS(), 20, 20), event -> Workspace.getInstance().setNewScene(IdentifiableCard.getInstance(table, null))
                )
        );
        return toolsNode;
    }

    private void init() {
        tableView = new TableView<>();

        try {
            theme = Config.getInstance().getScene().getTheme();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
                    .collect(Collectors.toList());

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
        tableView.getChildrenUnmodifiable().addListener((ListChangeListener<? super Node>) change -> Main.LOG.log(Level.INFO, change.toString()));
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setRowFactory(data -> new FinRow());
    }

    @Override
    public void setCloseable(Closeable closeable) {

    }

    private void showData(ListChangeListener.Change<? extends HashMap<String, Object>> change) {
        Platform.runLater(() -> {
            tableView.getColumns().clear();
//            tableView.getItems().clear();
            tableView.setItems(data);
            tableView.refresh();
            //Формируем колонку с категориями
            TableColumn<HashMap<String, Object>, String> category = getTableColumn(Columns.CATEGORY.name(), Columns.CATEGORY.name());
            tableView.getColumns().add(category);
            //Формируем колонки по датам
            for (LocalDate date : columnKeys.stream()
                    .sorted(LocalDate::compareTo)
                    .collect(Collectors.toList())) {
//            getTableColumn(
//                    String.format(this.columnNamePattern, date.getMonth().name(), date.getYear())
//                    , date.format(DateTimeFormatter.ISO_DATE));
                if (sortPane != null && sortPane.getValue() == SORT_BY.ALL) {
                    tableView.getColumns().add(getTableColumn(
                            String.valueOf(date.getYear())
                            , date.format(DateTimeFormatter.ISO_DATE)));
                } else {
                    tableView.getColumns().add(getTableColumn(
                            String.format(this.columnNamePattern, date.getMonth().name(), date.getYear())
                            , date.format(DateTimeFormatter.ISO_DATE)));
                }
            }

            //формируем итоговый столбец если стобцов по датам больше 1
            if (columnKeys.size() > 1) {
                TableColumn<HashMap<String, Object>, String> total = getTableColumn(Columns.TOTAL.name(), Columns.TOTAL.name());
                tableView.getColumns().add(total);
            }

            System.out.println("hello");
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

    private class FinRow
            extends TableRow<HashMap<String, Object>>{
        public FinRow() {
            super();
            initStyle();
        }

        private void initStyle(){
            setBackground(
                    new BackgroundWrapper()
                            .setColor(theme.PRIMARY())
                            .commit()
            );
            setBorder(
                    new BorderWrapper()
                            .addBottomBorder(1)
                            .setColor(theme.PRIMARY())
                            .setStyle(BorderStrokeStyle.SOLID)
                            .commit()
            );
        }

        @Override
        protected void updateItem(HashMap<String, Object> data, boolean b) {
            if(data != null) {
                super.updateItem(data, b);
                hoverProperty().addListener((observableValue, aBoolean, t1) -> {
                    if(t1){
                        setBorder(
                                new BorderWrapper()
                                        .addBottomBorder(1)
                                        .setColor(theme.SECONDARY())
                                        .setStyle(BorderStrokeStyle.SOLID)
                                        .commit()
                        );
                    }else {
                        setBorder(
                                new BorderWrapper()
                                        .addBottomBorder(1)
                                        .setColor(theme.PRIMARY())
                                        .setStyle(BorderStrokeStyle.SOLID)
                                        .commit()
                        );
                    }
                });
            }
        }
    }

    private class FinCell
            extends TableCell<HashMap<String, Object>, String> {

        @Override
        protected void updateItem(String s, boolean b) {
            if (s != null) {
                super.updateItem(s, b);
                BorderPane node = new BorderPane(Label.getInstanse(s));
                node.setBackground(
                        new BackgroundWrapper()
                                .setColor(theme.PRIMARY())
                                .commit()
                );
                setGraphic(node);
            }
        }
    }
}
