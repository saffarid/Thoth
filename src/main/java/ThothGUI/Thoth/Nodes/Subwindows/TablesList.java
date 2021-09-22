package ThothGUI.Thoth.Nodes.Subwindows;

import Database.Table;
import ThothCore.Thoth.Thoth;
import ThothGUI.Thoth.CloseSubwindow;
import ThothGUI.Thoth.Nodes.Subwindows.CreateTable.CreateTable;
import ThothGUI.Thoth.OpenSubwindow;
import controls.Button;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import layout.basepane.BorderPane;
import layout.basepane.HBox;
import window.Subwindow;

public class TablesList extends Subwindow {

    private BorderPane content;

    private TableView<Table> tablesList;

    private CloseSubwindow close;
    private OpenSubwindow open;

    private Thoth thoth;

    public TablesList(String title
            , OpenSubwindow open
            , CloseSubwindow close
            , Thoth thoth
    ) {
        super(title);
        this.thoth = thoth;
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
        content.setCenter(configTableList());

    }

    private HBox configPallete() {
        HBox pallete = new HBox();

        pallete.getChildren().addAll(
                getButton(thoth_styleconstants.Image.PLUS, this::createTable)
                , getButton(thoth_styleconstants.Image.EDIT, this::editTable)
                , getButton(thoth_styleconstants.Image.TRASH, this::removeTable)
        );

        return pallete;
    }

    private TableView<Table> configTableList() {
        tablesList = new TableView<>();

        tablesList.setItems(FXCollections.observableArrayList(thoth.getTables()));

        TableColumn<Table, String> name = new TableColumn<>("Name");
        name.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getName()));

        TableColumn<Table, String> type = new TableColumn<>("Type");
        type.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getType()));

        tablesList.getColumns().addAll(
                name
                , type
        );

        return tablesList;
    }

    private void createTable(ActionEvent actionEvent) {
        open.openSubwindow(new CreateTable(this.close, thoth));
    }

    private void editTable(ActionEvent event) {

    }

    private Button getButton(String url
            , EventHandler<ActionEvent> event) {
        Button btn = new Button(
                new ImageView(
                        new Image(getClass().getResourceAsStream(url), 20, 20, true, true)
                )
        );
        btn.setOnAction(event);
        return btn;
    }

    private void removeTable(ActionEvent event) {

    }
}
