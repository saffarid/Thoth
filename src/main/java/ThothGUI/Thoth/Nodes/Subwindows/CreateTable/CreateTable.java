package ThothGUI.Thoth.Nodes.Subwindows.CreateTable;

import Database.Table;
import Database.TableColumn;
import ThothCore.Thoth.DataTypes;
import ThothCore.Thoth.Thoth;
import ThothGUI.CloseSubwindow;
import ThothGUI.RefreshSystem;
import controls.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import layout.basepane.*;
import layout.custompane.DropdownPane;
import window.Subwindow;

import java.sql.SQLException;
import java.util.List;

public class CreateTable extends Subwindow {

    private static final String ID_CREATE_TABLE_SUBWIND = "create_table";

    private final String IMG_ADD_ROW = "/image/ico/plus.png";

    private BorderPane content;

    private CloseSubwindow close;
    private ComboBox type;

    private List<DataTypes> dataTypes;

    private Table createdTable;
    private TextField tableName;
    private VBox columnsList;

    private Thoth thoth;

    private RefreshSystem refreshTableList;

    public CreateTable(
            CloseSubwindow close
            , Thoth thoth
            , RefreshSystem refreshSystem
    ) {
        super("Создать таблицу");
        this.close = close;
        this.thoth = thoth;
        this.refreshTableList = refreshSystem;

        setId(ID_CREATE_TABLE_SUBWIND);

        createdTable = new Table();
        createdTable.setType(Table.TABLE);

        dataTypes = thoth.getDataTypes();

        setCloseEvent(event -> this.close.closeSubwindow(this));

        setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) toFront();
        });

        content = new BorderPane();
        content.setPadding(new Insets(5));
        content.setTop(createNameTypePane());
        content.setCenter(createColumnsPane());
        setCenter(content);

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
            refreshTableList.refresh();
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

    private Pane createColumnsPane() {

        BorderPane columnsContent = new BorderPane();
        this.columnsList = new VBox();

        //Хидер области колонок
        VBox columnsHeader = new VBox();
        columnsHeader.setSpacing(0);
        columnsHeader.getChildren().addAll(
                new Label("columns").setPadding(2),
                createPalette()
        );

        //Скрол пейн для списка колонок
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(this.columnsList);

        createTableColumn();

        columnsContent.setTop(columnsHeader);
        columnsContent.setCenter(scrollPane);

        return columnsContent;

    }

    private Pane createNameTypePane() {

        VBox header = new VBox();
        header.setSpacing(5);
        header.setFillWidth(true);

        //Формируем поле ввода наименования
        tableName = new TextField("TableName");
        tableName.textProperty().addListener((observableValue, s, t1) -> {
            if (t1 != null) {
                createdTable.setName(tableName.getText());
            }
        });

        //Формируем поле выбора типа таблицы
        type = new ComboBox<>();
        type.setItems(FXCollections.observableArrayList(thoth.getTableTypes()));
        type.valueProperty().addListener((observableValue, o, t1) -> createdTable.setType(type.getValue().toString()));

        header.getChildren().addAll(
                new Twin(
                        new Label("Table name"),
                        tableName
                )
                , new Twin(
                        new Label("Table type"),
                        type
                )
        );

        return header;
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
        TableColumnPane columnDesc = new TableColumnPane(tableColumn, dataTypes);

        DropdownPane column = new DropdownPane(
                "Column name", columnDesc
        );

        column.bindHeader(columnDesc.getColumnName());
        this.columnsList.getChildren().add(
                new BorderPane(column)
        );
    }

    private HBox createPalette(){

        HBox palette = new HBox();

        palette.setSpacing(10);

        palette.getChildren().addAll(
                getButton(thoth_styleconstants.Image.PLUS, this::addRow)
        );

        return palette;
    }

    private Button getButton(
            String url
            , EventHandler<ActionEvent> event
    ) {
        Button button = new Button(
          new ImageView(
                  new Image(
                          getClass().getResource(url).toExternalForm(), 15, 15, true, true
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
