package ThothGUI.ThothLite.Components.DBElementsView.ListCell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import javafx.scene.control.ListCell;

public class IdentifiableListCell<T extends Identifiable>
        extends ListCell<T>
{
    @Override
    protected void updateItem(T identifiable, boolean b) {
        if(identifiable != null) {
            super.updateItem(identifiable, b);
            setGraphic(
                    IdentifiableViewCell.getInstance(identifiable)
            );
        }
    }
}
