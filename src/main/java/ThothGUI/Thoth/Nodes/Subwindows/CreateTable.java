package ThothGUI.Thoth.Nodes.Subwindows;

import ThothGUI.Thoth.CloseSubwindow;
import controls.Button;
import controls.Label;
import controls.TextField;
import controls.Toggle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
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

        setCloseEvent(actionEvent -> close.closeSubwindow(this));

        setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) toFront();
        });

        content = new BorderPane();
        setCenter(content);

        configHeader();
        configContent();
    }

    private void addRow(ActionEvent actionEvent) {
        createTableColumn();
    }

    private void createTableColumn(){
        columns.getChildren().add(
                new DropdownPane(
                        "Column 1", new Toggle(false)
                )
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
        columns = new VBox();

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

    private class TableColumnGUI extends VBox{

    }
}
