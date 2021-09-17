package ThothGUI.Thoth.Nodes;

import Database.TableColumn;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import layout.basepane.BorderPane;

public class TableColumnPane extends BorderPane {

    private SimpleStringProperty name;
    private SimpleStringProperty type;
    private SimpleBooleanProperty isUnique;
    private SimpleBooleanProperty isNotNull;
    private SimpleObjectProperty<TableColumn> fkTableColumn;

    public TableColumnPane(TableColumn tableColumn) {
        super();

        name = new SimpleStringProperty(tableColumn.getName());
        type = new SimpleStringProperty(tableColumn.getType());
        isUnique = new SimpleBooleanProperty(tableColumn.isUnique());
        isNotNull = new SimpleBooleanProperty(tableColumn.isNotNull());
        fkTableColumn = new SimpleObjectProperty<>(tableColumn.getFKTableCol());


    }

}
