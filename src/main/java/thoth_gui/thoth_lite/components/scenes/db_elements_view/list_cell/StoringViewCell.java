package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_cell;

import thoth_core.thoth_lite.db_data.db_data_element.properties.Storing;
import thoth_gui.thoth_styleconstants.Image;

public class StoringViewCell extends IdentifiableViewCell {

    private static final String templateStoragable = "%1s-%2s";

    protected StoringViewCell(Storing storing) {
        super(
                null,
                storing.getId(),
                String.format( templateStoragable, storing.getStoragable().getId(), storing.getStoragable().getName() ),
                String.valueOf(storing.getCount())
        );
    }

}
