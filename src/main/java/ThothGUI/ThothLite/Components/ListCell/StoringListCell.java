package ThothGUI.ThothLite.Components.ListCell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storing;
import thoth_styleconstants.Image;

public class StoringListCell extends IdentifiableListCellImpl{

    private static final String templateStoragable = "%1s-%2s";

    protected StoringListCell(Storing storing) {
        super(
                Image.STORAGE_CELL,
                storing.getId(),
                String.format( templateStoragable, storing.getStoragable().getId(), storing.getStoragable().getName() ),
                String.valueOf(storing.getCount())
        );
    }

}
