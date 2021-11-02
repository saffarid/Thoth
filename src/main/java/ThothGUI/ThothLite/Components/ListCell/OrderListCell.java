package ThothGUI.ThothLite.Components.ListCell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Orderable;
import thoth_styleconstants.Image;

import java.time.format.DateTimeFormatter;

public class OrderListCell
        extends IdentifiableListCellImpl{

    protected OrderListCell(Orderable order) {
        super(
                Image.ORDER,
                order.getId(),
                order.getProjectable().getName(),
                order.getId()
        );
    }

}
