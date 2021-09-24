package ThothGUI.Thoth.Nodes.Subwindows;

import Database.ContentValues;
import Database.Table;
import ThothGUI.Thoth.CloseSubwindow;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import layout.basepane.BorderPane;
import layout.basepane.TableView;
import window.Subwindow;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс реализует визуальное отображение содержимого таблицы
 */
public class TableShower extends Subwindow {

    private CloseSubwindow close;

    private BorderPane content;

    /**
     * Таблица БД
     */
    private Table table;

    /**
     * View для отображения содержимого таблицы
     */
    private TableView<ContentValues> tableContent;

    public TableShower(
            String title
            , Table table
            , CloseSubwindow close
    ) {
        super(title);
        this.table = table;
        this.close = close;

        setCloseEvent(actionEvent -> close.closeSubwindow(this));

        content = new BorderPane();

        createTable();
        content.setCenter(tableContent);
        setCenter(content);
    }

    private void createTable() {
        tableContent = new TableView<>();

        tableContent.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        List<ContentValues> contentValues = table.getContentValues();
        tableContent.getItems().setAll(contentValues);

        List<Database.TableColumn> columnCollect = table.getColumns()
                .stream()
                .filter(column -> !column.getName().equals(Table.ID))
                .collect(Collectors.toList());
        for (Database.TableColumn column : columnCollect) {
            tableContent.getColumns().add(getTableColumnView(column));
        }
    }

    private TableColumn<ContentValues, Object> getTableColumnView(Database.TableColumn column) {

        TableColumn<ContentValues, Object> columnView = new TableColumn<>();
        columnView.setGraphic(new Label(column.getName()));

        columnView.setCellValueFactory(cellData -> {
            Object value = cellData.getValue().get(column);
            Property res;
            res = new SimpleObjectProperty(value);
            return res;
        });

        return columnView;
    }

}
