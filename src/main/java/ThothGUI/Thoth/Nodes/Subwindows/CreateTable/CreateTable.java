package ThothGUI.Thoth.Nodes.Subwindows.CreateTable;

import ThothGUI.Thoth.CloseSubwindow;
import controls.Button;
import controls.Label;
import controls.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import layout.basepane.*;
import layout.custompane.DropdownPane;
import window.Subwindow;

public class CreateTable extends Subwindow {

    private final String IMG_ADD_ROW = "/image/icon/plus.png";

    private BorderPane content;

    private VBox columns;

    public CreateTable(
        CloseSubwindow close
    ) {
        super("Создать таблицу");

        setCloseEvent(event -> close.closeSubwindow(this));

        setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) toFront();
        });

        content = new BorderPane();

        setCenter(content);

        configHeader();
        configContent();

        columns.setBackground(
                new Background(
                        new BackgroundFill(Paint.valueOf("green"), null, null)
                )
        );
    }

    private void addRow(ActionEvent actionEvent) {
        createTableColumn();
    }

    private void createTableColumn(){
        TableColumnPane columnDesc = new TableColumnPane();

        DropdownPane column = new DropdownPane(
                "Column 1", columnDesc
        );

        column.bindHeader(columnDesc.getColumnName());
        columns.getChildren().add(
                new BorderPane(column)
        );
    }

    private void configHeader() {

        VBox header = new VBox();
        header.setSpacing(5);

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(
            new Label("Table name"),
            new TextField()
        );

        HBox palette = new HBox();
        palette.setSpacing(10);
        palette.getChildren().addAll(
                getButton(IMG_ADD_ROW, this::addRow)
        );

        header.getChildren().addAll(
                hBox
                ,palette
        );

        content.setTop(header);
    }

    private void configContent(){

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);

        columns = new VBox();

        columns.setPadding(new Insets(5));
        columns.setSpacing(5);
        columns.setFillWidth(true);

        scrollPane.setContent(columns);
        createTableColumn();

        content.setCenter(scrollPane);
    }

    private Button getButton(
            String url
            , EventHandler<ActionEvent> event
    ){
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
