package ThothGUI.ThothLite.Components.DBElementsView.ListCell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Orderable;
import thoth_styleconstants.Image;

public class OrderViewCell
        extends IdentifiableViewCell {

    protected OrderViewCell(Orderable order) {
        super(
                Image.ORDER,
                order.getId(),
                order.getProjectable().getName(),
                order.getId()
        );
    }

}
