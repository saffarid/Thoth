package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_cell;

import thoth_core.thoth_lite.db_data.db_data_element.properties.Storagable;
import thoth_gui.thoth_styleconstants.svg.Images;
import tools.SvgWrapper;

public class StoragableViewCell
        extends IdentifiableViewCell {

    protected StoragableViewCell(Storagable product) {
        super(
                SvgWrapper.getInstance(Images.PRODUCT()),
                product.getId(),
                product.getName(),
                product.getType().getValue()
        );
    }

}
