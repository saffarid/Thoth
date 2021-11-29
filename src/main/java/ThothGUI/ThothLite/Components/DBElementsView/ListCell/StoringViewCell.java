package ThothGUI.ThothLite.Components.DBElementsView.ListCell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storing;
import ThothGUI.ThothLite.Components.DBElementsView.ListCell.IdentifiableViewCell;
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
