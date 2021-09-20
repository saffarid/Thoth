package ThothGUI.Thoth.Nodes.Subwindows.CreateTable;

import Database.TableColumn;
import controls.Label;
import controls.TextField;
import controls.Toggle;
import controls.Twin;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import layout.custompane.DropdownPane;


public class TableColumnPane extends VBox {

    private TableColumn tableColumn;

    private TextField name;
    private Toggle isPK;
    private Toggle isUniq;
    private Toggle isNotNull;

    public TableColumnPane(
            TableColumn tableColumn) {
        super();

        setSpacing(10);

        this.tableColumn = tableColumn;

        name = new TextField(tableColumn.getName());
        isPK = new Toggle(tableColumn.isPrimaryKey());
        isUniq = new Toggle(tableColumn.isUnique());
        isNotNull = new Toggle(tableColumn.isNotNull());

        Label checkLabel = new Label();
        checkLabel.textProperty().bind(
                name.textProperty()
                        .concat("; ")
                        .concat(isUniq.isTrueProperty())
                        .concat("; ")
                        .concat(isNotNull.isTrueProperty())
                        .concat("; ")
        );

        Label tableCol = new Label();
        checkLabel.textProperty().addListener((observableValue, s, t1) -> {
//            System.out.println("change all");
//            tableCol.setText(this.tableColumn.getName() + "; " + this.tableColumn.isUnique() + "; " + this.tableColumn.isNotNull());
        });


        name.textProperty().addListener((observableValue, s, t1) -> {
            if (t1 != null) {
                System.out.println("change name");
                this.tableColumn.setName(t1);
                tableCol.setText(this.tableColumn.getName() + "; " + this.tableColumn.isUnique() + "; " + this.tableColumn.isNotNull());
            }
        });
        isUniq.isTrueProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1 != null) {
                this.tableColumn.setUnique(t1);
                tableCol.setText(this.tableColumn.getName() + "; " + this.tableColumn.isUnique() + "; " + this.tableColumn.isNotNull());
            }
        });
        isNotNull.isTrueProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1 != null) {
                this.tableColumn.setNotNull(t1);
                tableCol.setText(this.tableColumn.getName() + "; " + this.tableColumn.isUnique() + "; " + this.tableColumn.isNotNull());
            }
        });


        getChildren().addAll(
                new Twin(new Label("Наименование"), name),
                new Twin(new Label("Тип данных"), new ComboBox<>()),
                new Twin(new Label("Ключ"), isPK),
                new Twin(new Label("Уникальность"), isUniq),
                new Twin(new Label("Отсутсвие записей"), isNotNull),
                checkLabel,
                tableCol
        );

    }

    public StringProperty getColumnName(){
        return name.textProperty();
    }

}
