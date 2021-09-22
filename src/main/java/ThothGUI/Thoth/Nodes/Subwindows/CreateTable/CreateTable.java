package ThothGUI.Thoth.Nodes.Subwindows.CreateTable;

import Database.ContentValues;
import Database.Table;
import Database.TableColumn;
import ThothCore.Thoth.Thoth;
import ThothGUI.Thoth.CloseSubwindow;
import controls.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import layout.basepane.*;
import layout.custompane.DropdownPane;
import window.Subwindow;

import java.sql.SQLException;
import java.util.List;

public class CreateTable extends Subwindow {

    private final String IMG_ADD_ROW = "/image/ico/plus.png";

    private BorderPane content;

    private CloseSubwindow close;
    private ComboBox type;

    private Table createdTable;
    private TextField tableName;
    private VBox columns;

    private Thoth thoth;

    public CreateTable(
            CloseSubwindow close
            , Thoth thoth
    ) {
        super("Создать таблицу");
        this.close = close;
        this.thoth = thoth;

        createdTable = new Table();
        createdTable.setType(Table.TABLE);

        setCloseEvent(event -> this.close.closeSubwindow(this));

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

    }

    private void addRow(ActionEvent actionEvent) {
        createTableColumn();
    }

    private void apply(ActionEvent event) {
        try {
            thoth.createTable(createdTable);
            close.closeSubwindow(this);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void cancel(ActionEvent event) {
        close.closeSubwindow(this);
    }

    private void createTableColumn() {

        List<TableColumn> columns = createdTable.getColumns();
        TableColumn tableColumn = new TableColumn(
                "Column ".concat(" ").concat(String.valueOf(columns.size()))
                , "varchar(255)"
                , false
                , false
        );

        createdTable.addColumn(tableColumn);
        TableColumnPane columnDesc = new TableColumnPane(tableColumn, thoth.getDataTypes());

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

        type = new ComboBox<>();
        type.setItems(FXCollections.observableArrayList(thoth.getTableTypes()));


        HBox palette = new HBox();
        palette.setSpacing(10);
        palette.getChildren().addAll(
                getButton(thoth_styleconstants.Image.PLUS, this::addRow)

        );

        header.getChildren().addAll(
                new Twin(
                        new Label("Table name"),
                        tableName
                )
                , new Twin(
                        new Label("Table type"),
                        type
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

        columns.setTop(
                new Label("Columns")
                .setPadding(5)
        );
        columns.setCenter(scrollPane);

        content.setCenter(columns);
    }

    private Button getButton(
            String url
            , EventHandler<ActionEvent> event
    ) {
        Button button = new Button(
          new ImageView(
                  new Image(
                          getClass().getResource(url).toExternalForm(), 20, 20, true, true
                  )
          )
        );

        button.setOnAction(event);
        return button;
    }

    class ComboBoxTypeTable extends ListCell<Object>{
        @Override
        protected void updateItem(Object o, boolean b) {
            if(o != null){
                super.updateItem(o, b);
                setText(o.toString());
            }
        }
    }

}
