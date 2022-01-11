package thoth_gui.thoth_lite.subwindows;

import thoth_core.thoth_lite.db_data.db_data_element.properties.Identifiable;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_gui.thoth_lite.components.scenes.db_elements_view.identifiable_card.IdentifiableCard;

/**
 * Subwindow для отображения карточки идентифицируемого объекта.
 * */
public class IdentifiableCardWindow
        extends Subwindow
{
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

        content = IdentifiableCard.getInstance(table, identifiable);
        ((IdentifiableCard)content).setParentClose(this::close);
        setCenter(content);
    }

}
