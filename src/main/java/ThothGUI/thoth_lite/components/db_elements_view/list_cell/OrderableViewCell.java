package ThothGUI.thoth_lite.components.db_elements_view.list_cell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Orderable;
import ThothGUI.thoth_styleconstants.Image;

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
