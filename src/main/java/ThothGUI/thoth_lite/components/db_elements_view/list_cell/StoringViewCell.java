package ThothGUI.thoth_lite.components.db_elements_view.list_cell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storing;
import ThothGUI.thoth_styleconstants.Image;

public class StoringViewCell extends IdentifiableViewCell {

    private static final String templateStoragable = "%1s-%2s";

    protected StoringViewCell(Storing storing) {
        super(
                Image.STORAGE_CELL,
                storing.getId(),
                String.format( templateStoragable, storing.getStoragable().getId(), storing.getStoragable().getName() ),
                String.valueOf(storing.getCount())
        );
    }

}
