package ThothGUI.Thoth.Nodes.Subwindows;

import Database.ContentValues;
import Database.Table;
import ThothGUI.Thoth.CloseSubwindow;

import controls.Button;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import layout.basepane.BorderPane;
import layout.basepane.HBox;
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
        content.setTop(createPalette());
        setCenter(content);
    }

    private void addRows(ActionEvent event){

    }

    private Button getButton(
            String url,
            EventHandler<ActionEvent> event
    ){
        Button btn = new Button(
                new ImageView(
                        new Image(getClass().getResource(url).toExternalForm(), 15, 15, true, true
                )
        ));

        btn.setOnAction(event);

        return btn;
    }

    private HBox createPalette(){

        HBox palette = new HBox();

        palette.getChildren().addAll(
                getButton(thoth_styleconstants.Image.PLUS, this::addRows)
                , getButton(thoth_styleconstants.Image.TRASH, this::removeRows)
        );

        return palette;
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

    private void removeRows(ActionEvent event){

    }

}
