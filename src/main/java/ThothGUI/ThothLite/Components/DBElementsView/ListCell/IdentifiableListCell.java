package ThothGUI.ThothLite.Components.DBElementsView.ListCell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;

public class IdentifiableListCell<T extends Identifiable>
        extends ListCell<T>
{

    private final static String STYLE_CLASS_IDENTIFIABLE_CELL = "identifiable-cell";

    public IdentifiableListCell() {
        super();
        getStyleClass().add(STYLE_CLASS_IDENTIFIABLE_CELL);
        setPadding(new Insets(1, 0, 1, 0));
    }

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
