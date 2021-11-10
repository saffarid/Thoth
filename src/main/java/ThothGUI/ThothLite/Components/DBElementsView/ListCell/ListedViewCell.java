package ThothGUI.ThothLite.Components.DBElementsView.ListCell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothGUI.thoth_styleconstants.Image;

public class ListedViewCell extends IdentifiableViewCell {
    protected ListedViewCell(Listed listed) {
        super(Image.LIST, listed.getValue(), "", "");
    }

}
