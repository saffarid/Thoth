package ThothGUI.ThothLite.Components.ListCell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;
import thoth_styleconstants.Image;

public class ProductListCell
        extends IdentifiableListCellImpl {

    protected ProductListCell(Storagable product) {
        super(
                Image.PRODUCT,
                product.getId(),
                product.getName(),
                product.getType().getValue()
        );
    }

}
