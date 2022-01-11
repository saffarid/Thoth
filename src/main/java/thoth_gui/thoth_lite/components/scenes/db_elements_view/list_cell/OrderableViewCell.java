package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_cell;

import thoth_core.thoth_lite.db_data.db_data_element.properties.Orderable;
import thoth_gui.thoth_styleconstants.Image;

public class OrderableViewCell
        extends IdentifiableViewCell {

    protected OrderableViewCell(Orderable order) {
        super(
                Image.ORDER,
                order.getId(),
                order.getProjectable().getName(),
                order.getId()
        );
    }

}
