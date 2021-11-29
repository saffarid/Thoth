package ThothGUI.ThothLite.Components.DBElementsView.ListCell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Orderable;
import ThothGUI.ThothLite.Components.DBElementsView.ListCell.IdentifiableViewCell;
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
