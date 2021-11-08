package ThothGUI.ThothLite.Subwindows;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothGUI.ThothLite.Components.DBElementsView.IdentifiableCard.IdentifiableCard;
import window.Subwindow;

/**
 *
 * */
public class IdentifiablePane extends Subwindow {

    public IdentifiablePane(
            String title
            , Identifiable identifiable) {
        super(title);

        setCenter(IdentifiableCard.getInstance(identifiable));
    }

}
