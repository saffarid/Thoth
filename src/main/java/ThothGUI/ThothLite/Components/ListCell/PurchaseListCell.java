package ThothGUI.ThothLite.Components.ListCell;

import ThothCore.ThothLite.DBData.DBDataElement.Purchasable;
import thoth_styleconstants.Image;

import java.time.format.DateTimeFormatter;

public class PurchaseListCell
        extends IdentifiableListCellImpl{

    protected PurchaseListCell(Purchasable purchase) {
        super(
                Image.PURCHASE,
                purchase.getId(),
                purchase.finishDate().format(DateTimeFormatter.ISO_DATE),
                (purchase.isDelivered())?("Доставлено"):("Не доставлено")
        );
    }

}
