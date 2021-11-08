package ThothGUI.ThothLite.Components.DBElementsView.ListCell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothGUI.OpenSubwindow;
import ThothGUI.ThothLite.Subwindows.IdentifiablePane;
import ThothGUI.ThothLite.ThothLiteWindow;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import window.Subwindow;

public class IdentifiableListCell<T extends Identifiable>
        extends ListCell<T>
{

    private final static String STYLE_CLASS_IDENTIFIABLE_CELL = "identifiable-cell";

    private Identifiable identifiable;

    public IdentifiableListCell() {
        super();
        getStyleClass().add(STYLE_CLASS_IDENTIFIABLE_CELL);
        setPadding(new Insets(1, 0, 1, 0));
        
        setOnMouseClicked(this::cellClick);
    }

    private void cellClick(MouseEvent mouseEvent) {
        ((OpenSubwindow)ThothLiteWindow.getInstance()).openSubwindow(new IdentifiablePane("Карточка", identifiable));
    }

    @Override
    protected void updateItem(T identifiable, boolean b) {
        if(identifiable != null) {
            super.updateItem(identifiable, b);
            if(this.identifiable == null) this.identifiable = identifiable;
            setGraphic(
                    IdentifiableViewCell.getInstance(identifiable)
            );
        }
    }


}
