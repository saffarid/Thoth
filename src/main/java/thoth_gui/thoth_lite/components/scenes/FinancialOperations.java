package thoth_gui.thoth_lite.components.scenes;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import layout.basepane.BorderPane;
import thoth_core.thoth_lite.ThothLite;
import thoth_core.thoth_lite.db_data.db_data_element.properties.FinancialAccounting;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Typable;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_gui.thoth_lite.components.controls.Button;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortBy;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortPane;
import thoth_gui.thoth_lite.components.scenes.db_elements_view.identifiable_card.IdentifiableCard;
import thoth_gui.thoth_lite.main_window.Workspace;
import thoth_gui.thoth_styleconstants.svg.Images;
import tools.SvgWrapper;
import window.Closeable;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class FinancialOperations
        extends ThothSceneImpl {

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

    private TableColumn<HashMap<String, Object>, String> categoryColumn;

    private List<Typable> categories;

    /**
     * Исходные данные для отображения в таблице
     */
    private SimpleListProperty<FinancialAccounting> initialData;

    public FinancialOperations(AvaliableTables table) {
        this.table = table;

        // Запрос типов финансовых операций
        try {
            if (table == AvaliableTables.EXPENSES) {
                categories = (List<Typable>) ThothLite.getInstance().getDataFromTable(AvaliableTables.EXPENSES_TYPES);
            } else {
                categories = (List<Typable>) ThothLite.getInstance().getDataFromTable(AvaliableTables.INCOMES_TYPES);
            }
        } catch (NotContainsException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        initialData = new SimpleListProperty<>();
        initialData.addListener(this::listDataChange);

        content = new SimpleObjectProperty<>(createContent());
        tools = new SimpleObjectProperty<>(createToolsNode());
    }

    private void listDataChange(ListChangeListener.Change<? extends FinancialAccounting> change) {
    }

    private Node createToolsNode() {
        toolsNode = new BorderPane();
        toolsNode.setLeft(getSortPane());
        toolsNode.setRight(
                Button.getInstance(
                        SvgWrapper.getInstance(Images.PLUS(), 20, 20), event -> Workspace.getInstance().setNewScene(IdentifiableCard.getInstance(table, null))
                )
        );
        return toolsNode;
    }

    private SortPane getSortPane() {
        sortPane = SortPane.getInstance()
                .setSortItems(SORT_BY.values())
                .setCell()
                .setSortMethod(this::sort)
                .setValue(SORT_BY.QUARTER)
        ;
        return sortPane;
    }

    private void sort(ObservableValue<? extends SortBy> observableValue, SortBy sortBy, SortBy sortBy1) {
        SORT_BY sort_by = (SORT_BY) sortBy1;
        HashMap<Typable, HashMap<String, Double>> data = new HashMap<>();
        Set<String> keySet = new LinkedHashSet<>();
        keySet.add("category");
        List<FinancialAccounting> d = initialData.getValue();
        switch (sort_by) {
            case QUARTER: {

                LocalDate now = LocalDate.now();
                LocalDate minusMonths = now.minusMonths(2);
                LocalDate startDate = LocalDate.of(minusMonths.getYear(), minusMonths.getMonth(), 1);

                for (int i = 0; i < 3; i++){
                    LocalDate date = now.minusMonths(i);
                    keySet.add(
                            LocalDate.of(date.getYear(), date.getMonth(), 1).format(DateTimeFormatter.ISO_DATE)
                    );
                }

                d = d.stream()
                        .filter(finOp -> finOp.getDate().isAfter(startDate))
                        .collect(Collectors.toList());
                break;
            }
        }


        for (FinancialAccounting finOp : d) {
            if(!data.containsKey(finOp.getCategory())){
                data.put(finOp.getCategory(), new HashMap<>());
            }

            HashMap<String, Double> row = data.get(finOp.getCategory());

            LocalDate finOpDate = finOp.getDate();
            String key = LocalDate.of(finOpDate.getYear(), finOpDate.getMonth(), 1).format(DateTimeFormatter.ISO_DATE);

            if(row.containsKey(key)){
                row.put(key, row.get(key) + finOp.getValue());
            }else{
                row.put(key, finOp.getValue());
            }
        }

        List<HashMap<String, Object>> res = new LinkedList<>();
        for(Typable type : data.keySet()){
            HashMap<String, Object> row = new HashMap<>();
            row.put("category", type.getValue());

            HashMap<String, Double> stringDoubleHashMap = data.get(type);
            for(String k : stringDoubleHashMap.keySet()){
                row.put(k, stringDoubleHashMap.get(k));
            }
            res.add(row);
        }

        fillTable(res, keySet);

    }

    private Node createContent() {
        tableView = new TableView();
        categoryColumn = new TableColumn<>("categoty");

        try {
            initialData.setValue(
                    FXCollections.observableList((List<FinancialAccounting>) ThothLite.getInstance().getDataFromTable(table))
            );

        } catch (NotContainsException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        contentNode = new BorderPane(tableView);
        return contentNode;
    }

    private void fillTable(List<HashMap<String, Object>> formData, Set<String> keys) {

        tableView.setItems(FXCollections.observableList(formData));
        for(String key : keys){
            TableColumn<HashMap<String, Object>, String> column = new TableColumn(key);
            column.setId(key);
            column.setCellValueFactory(data -> {
                Object obj = data.getValue().get(key);
                if (obj == null){
                    return new SimpleStringProperty(String.valueOf(0));
                }
                return new SimpleStringProperty(String.valueOf(obj));
            });
            tableView.getColumns().add(column);
        }

    }

    @Override
    public void close() {

    }

    @Override
    public void setCloseable(Closeable closeable) {

    }
}
