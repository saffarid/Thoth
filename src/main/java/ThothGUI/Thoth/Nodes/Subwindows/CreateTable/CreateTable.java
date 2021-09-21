package ThothGUI.Thoth.Nodes.Subwindows.CreateTable;

import Database.Table;
import Database.TableColumn;
import ThothGUI.Thoth.CloseSubwindow;
import controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Paint;
import layout.basepane.*;
import layout.custompane.DropdownPane;
import window.Subwindow;

import java.util.List;

public class CreateTable extends Subwindow {

    private final String IMG_ADD_ROW = "/image/icon/plus.png";

    private BorderPane content;

    private Table createdTable;
    private TextField tableName;
    private VBox columns;

    public CreateTable(
            CloseSubwindow close
    ) {
        super("Создать таблицу");

        createdTable = new Table();

        setCloseEvent(event -> close.closeSubwindow(this));

        setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) toFront();
        });

        content = new BorderPane();
        content.setPadding(new Insets(5));
        setCenter(content);

        configNameTypePane();
        configColumnsPane();
        content.setBottom(
                new DialogButtonBar(
                        "APPLY"
                        , "CANCEL"
                        , this::apply
                        , this::cancel
                )
        );

        columns.setBackground(
                new Background(
                        new BackgroundFill(Paint.valueOf("green"), null, null)
                )
        );
    }

    private void apply(ActionEvent event){

    }

    private void addRow(ActionEvent actionEvent) {
        createTableColumn();
    }

    private void cancel(ActionEvent event){

    }

    private void createTableColumn() {

        List<TableColumn> columns = createdTable.getColumns();
        TableColumn tableColumn = new TableColumn(
                "Column ".concat(" ").concat(String.valueOf(columns.size()))
                , "Текстовый"
                , false
                , false
        );

        columns.add(tableColumn);
        TableColumnPane columnDesc = new TableColumnPane(tableColumn);

        DropdownPane column = new DropdownPane(
                "Column name", columnDesc
        );

        column.bindHeader(columnDesc.getColumnName());
        this.columns.getChildren().add(
                new BorderPane(column)
        );
    }


    private void configNameTypePane() {

        VBox header = new VBox();
        header.setSpacing(5);

        tableName = new TextField();
        tableName.textProperty().addListener((observableValue, s, t1) -> {
            if (t1 != null) {
                createdTable.setName(tableName.getText());
            }
        });

        HBox palette = new HBox();
        palette.setSpacing(10);
        palette.getChildren().addAll(
                getButton(IMG_ADD_ROW, this::addRow)
        );

        header.getChildren().addAll(
                new Twin(
                        new Label("Table name"),
                        tableName
                )
                , palette
        );

        content.setTop(header);
    }

    private void configColumnsPane() {

        BorderPane columns = new BorderPane();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);

        this.columns = new VBox();

//        columns.setPadding(new Insets(5));
        this.columns.setSpacing(5);
        this.columns.setFillWidth(true);

        scrollPane.setContent(this.columns);
        createTableColumn();

        columns.setTop();
        columns.setCenter(this.columns);

        content.setCenter(scrollPane);
    }

    private Button getButton(
            String url
            , EventHandler<ActionEvent> event
    ) {
//        Button button = new Button(
//          new ImageView(
//                  new Image(
//                          url, 25, 25, true, true
//                  )
//          )
//        );

        Button button = new Button("AddRow");

        button.setOnAction(event);

        return button;
    }

}
