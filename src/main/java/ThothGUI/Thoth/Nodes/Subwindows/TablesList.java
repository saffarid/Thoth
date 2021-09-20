package ThothGUI.Thoth.Nodes.Subwindows;

import Database.Table;
import ThothGUI.Thoth.CloseSubwindow;
import ThothGUI.Thoth.Nodes.Subwindows.CreateTable.CreateTable;
import ThothGUI.Thoth.OpenSubwindow;
import controls.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import layout.basepane.BorderPane;
import layout.basepane.HBox;
import window.Subwindow;

public class TablesList extends Subwindow {

    private final String IMG_ADD = "/image/icon/plus.png";

    private BorderPane content;

    private TableView<Table> tablesList;

    private CloseSubwindow close;
    private OpenSubwindow open;

    public TablesList(String title
                      , OpenSubwindow open
                      , CloseSubwindow close
    ) {
        super(title);
        this.open = open;
        this.close = close;
        setCloseEvent(actionEvent -> this.close.closeSubwindow(this));
        setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) toFront();
        });

        configContent();
        setCenter(content);
    }

    private void configContent() {

        content = new BorderPane();
        content.setTop(configPallete());

    }

    private HBox configPallete() {
        HBox pallete = new HBox();

        pallete.getChildren().addAll(
                getButton(IMG_ADD, this::createTable)
        );

        return pallete;
    }

    private void createTable(ActionEvent actionEvent) {
        open.openSubwindow(new CreateTable(this.close));
    }

    private Button getButton(String url
            , EventHandler<ActionEvent> event){
//        Button btn = new Button(
//                new ImageView(
//                        new Image(url, 20, 20, true, true)
//                )
//        );
        Button btn = new Button("Создать таблицу");
        btn.setOnAction(event);
        return btn;
    }
}
