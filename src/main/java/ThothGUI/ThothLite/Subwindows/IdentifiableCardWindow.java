package ThothGUI.ThothLite.Subwindows;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothGUI.ThothLite.Components.DBElementsView.IdentifiableCard.IdentifiableCard;
import window.Subwindow;

/**
 * Subwindow для отображения карточки идентифицируемого объекта.
 * */
public class IdentifiableCardWindow extends Subwindow {

    private final String TEMPLATE_ID = "%1s:%2s";

    public IdentifiableCardWindow(
            String title
            , Identifiable identifiable) {
        super(title);

        setId(String.format(TEMPLATE_ID, title, identifiable.getId()));
        setCenter(IdentifiableCard.getInstance(identifiable));
    }

}
