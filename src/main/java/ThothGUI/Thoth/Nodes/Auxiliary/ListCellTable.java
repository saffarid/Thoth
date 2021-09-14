package ThothGUI.Thoth.Nodes.Auxiliary;

import Database.Table;
import controls.ListCell;

public class ListCellTable extends ListCell<Table> {

    @Override
    protected void updateItem(Table table, boolean b) {
        if (table != null) {
            super.updateItem(table, b);
            setText(table.getName());
        }
    }
}
