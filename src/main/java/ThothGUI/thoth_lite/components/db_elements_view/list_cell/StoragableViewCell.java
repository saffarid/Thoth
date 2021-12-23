package ThothGUI.thoth_lite.components.db_elements_view.list_cell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;
import ThothGUI.thoth_styleconstants.Image;

public class StoragableViewCell
        extends IdentifiableViewCell {

    protected StoragableViewCell(Storagable product) {
        super(
                Image.PRODUCT,
                product.getId(),
                product.getName(),
                product.getType().getValue()
        );
    }

}
