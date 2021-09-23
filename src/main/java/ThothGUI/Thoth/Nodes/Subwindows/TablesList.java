package ThothGUI.Thoth.Nodes.Subwindows;

import Database.Table;
import ThothCore.Thoth.Thoth;
import ThothGUI.Thoth.CloseSubwindow;
import ThothGUI.Thoth.Nodes.Subwindows.CreateTable.CreateTable;
import ThothGUI.Thoth.OpenSubwindow;
import ThothGUI.Thoth.RefreshSystem;
import controls.Button;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.SelectionMode;
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

import java.sql.SQLException;
import java.util.LinkedList;

public class TablesList extends Subwindow implements RefreshSystem {

    private final static String ID_TABLE_LIST_SUBWIND = "table_list";

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

        setId(ID_TABLE_LIST_SUBWIND);

        setCloseEvent(actionEvent -> this.close.closeSubwindow(this));

        setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) toFront();
        });


        configContent();
        setCenter(content);
    }

    private void configContent() {

        content = new BorderPane();
        content.setCenter(configTableList());
        content.setTop(configPallete());

    }

    private HBox configPallete() {
        HBox pallete = new HBox();

        pallete.getChildren().addAll(
                getButton(thoth_styleconstants.Image.PLUS, this::createTable)
                , getButton(thoth_styleconstants.Image.EDIT, this::editTable, new SimpleListProperty<>(tablesList.getSelectionModel().getSelectedItems()).sizeProperty().isNotEqualTo(1))
                , getButton(thoth_styleconstants.Image.TRASH, this::removeTable, new SimpleListProperty<>(tablesList.getSelectionModel().getSelectedItems()).emptyProperty())
        );

        return pallete;
    }

    private TableView<Table> configTableList() {
        tablesList = new TableView<>();

        tablesList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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

        open.openSubwindow(
                new CreateTable(this.close, thoth, this::refresh)
        );
    }

    private void editTable(ActionEvent event) {

    }

    private Button getButton(String url
            , EventHandler<ActionEvent> event
    ) {
        Button btn = new Button(
                new ImageView(
                        new Image(getClass().getResource(url).toExternalForm(), 20, 20, true, true)
                )
        );
        btn.setOnAction(event);
        return btn;
    }

    private Button getButton(String url
            , EventHandler<ActionEvent> event
            , ObservableValue disabledProperty
    ) {
        Button button = getButton(url, event);
        button
                .disableProperty()
                .bind(disabledProperty);
        return button;
    }

    @Override
    public void refresh() {
        tablesList.getItems().clear();
        tablesList.setItems(FXCollections.observableArrayList(thoth.getTables()));
    }

    private void removeTable(ActionEvent event) {
        try {
            thoth.removeTable(new LinkedList<>(tablesList.getSelectionModel().getSelectedItems()));
            refresh();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
