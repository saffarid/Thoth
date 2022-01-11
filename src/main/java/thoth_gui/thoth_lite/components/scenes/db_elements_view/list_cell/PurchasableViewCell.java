package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_cell;

import thoth_core.thoth_lite.db_data.db_data_element.properties.Purchasable;
import thoth_gui.thoth_styleconstants.Image;

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
