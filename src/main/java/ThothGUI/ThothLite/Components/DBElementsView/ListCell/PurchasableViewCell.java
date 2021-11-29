package ThothGUI.ThothLite.Components.DBElementsView.ListCell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Purchasable;
import ThothGUI.ThothLite.Components.DBElementsView.ListCell.IdentifiableViewCell;
import ThothGUI.thoth_styleconstants.Image;

import java.time.format.DateTimeFormatter;

public class PurchasableViewCell
        extends IdentifiableViewCell {

    protected PurchasableViewCell(Purchasable purchase) {
        super(
                Image.PURCHASE,
                purchase.getId(),
                purchase.finishDate().format(DateTimeFormatter.ISO_DATE),
                (purchase.isDelivered())?("Доставлено"):("Не доставлено")
        );
    }

}
