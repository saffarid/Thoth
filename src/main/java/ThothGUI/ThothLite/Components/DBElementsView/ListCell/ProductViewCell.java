package ThothGUI.ThothLite.Components.DBElementsView.ListCell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;
import thoth_styleconstants.Image;

public class ProductViewCell
        extends IdentifiableViewCell {

    protected ProductViewCell(Storagable product) {
        super(
                Image.PRODUCT,
                product.getId(),
                product.getName(),
                product.getType().getValue()
        );
    }

}
