package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_cell;

import thoth_core.thoth_lite.db_data.db_data_element.properties.Storagable;
import thoth_gui.thoth_styleconstants.Image;

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
