package ThothGUI.thoth_lite.components.db_elements_view.list_cell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Purchasable;
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
