package ThothGUI.Thoth.Nodes.Subwindows.CreateTable;

import Database.TableColumn;
import controls.Label;
import controls.TextField;
import controls.Toggle;
import controls.Twin;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;


public class TableColumnPane extends VBox {

    private VBox columns;

    private TableColumn tableColumn;

    private TextField name;
    private ComboBox type;
    private Toggle isUniq;
    private Toggle isNotNull;

    public TableColumnPane(
            TableColumn tableColumn) {
        super();
        init(tableColumn);
    }

    public TableColumnPane() {
        super();
        init(null);
    }

    private void init(TableColumn tableColumn) {

//        columns = new VBox();
//        columns.setSpacing(10);
//        columns.setPadding(new Insets(0));
        setSpacing(10);

        if (tableColumn == null) {
            tableColumn = new TableColumn(
                    null
                    , "Текстовый"
                    , false
                    , false
            );
        }
        this.tableColumn = tableColumn;

        name = new TextField(this.tableColumn.getName());
        type = new ComboBox();
        isUniq = new Toggle(this.tableColumn.isUnique());
        isNotNull = new Toggle(this.tableColumn.isNotNull());

        type.setValue(this.tableColumn.getType());

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

        name.textProperty().addListener((observableValue, s, t1) -> {
            if (t1 != null) {
                this.tableColumn.setName(t1);
                tableCol.setText(
                        this.tableColumn.getName() + "; " +
                                this.tableColumn.getType() + "; " +
                                this.tableColumn.isUnique() + "; " +
                                this.tableColumn.isNotNull()
                );
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
                new Twin(new Label("Тип данных"), type),
                new Twin(new Label("Уникальность"), isUniq),
                new Twin(new Label("Отсутсвие записей"), isNotNull),
                checkLabel,
                tableCol
        );

//        setCenter(columns);
    }

    public TableColumn getTableColumn(){
        return tableColumn;
    }

    public StringProperty getColumnName() {
        return name.textProperty();
    }

}
