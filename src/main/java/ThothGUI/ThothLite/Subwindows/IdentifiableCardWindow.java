package ThothGUI.ThothLite.Subwindows;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothGUI.ThothLite.Components.DBElementsView.IdentifiableCard.IdentifiableCard;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import window.Subwindow;

/**
 * Subwindow для отображения карточки идентифицируемого объекта.
 * */
public class IdentifiableCardWindow extends Subwindow {

    private final String TEMPLATE_ID = "%1s:%2s";

    public IdentifiableCardWindow(
            String title
            , AvaliableTables table
            , Identifiable identifiable) {
        super(title);

        if (identifiable == null){
            setId(String.format(TEMPLATE_ID, title, "new"));
        }else{
            setId(String.format(TEMPLATE_ID, title, identifiable.getId()));
        }


        setCenter(IdentifiableCard.getInstance(table, identifiable));
    }

}
