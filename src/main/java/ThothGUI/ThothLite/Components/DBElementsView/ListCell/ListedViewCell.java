package ThothGUI.ThothLite.Components.DBElementsView.ListCell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothGUI.thoth_styleconstants.Image;
import controls.TextField;
import javafx.scene.image.ImageView;

public class ListedViewCell extends IdentifiableViewCell {


    protected ListedViewCell(Listed listed) {
        super(Image.LIST, listed.getValue(), "", "");

        setLeft(setImageIcon(Image.POINT));
        setCenter(new TextField( listed.getValue() ));
        setRight(setImageIcon(Image.TRASH));
    }

    @Override
    protected ImageView setImageIcon(String url) {
        ImageView node = new ImageView();
        node.setImage(
                new javafx.scene.image.Image(getClass().getResource(url).toExternalForm(), 20, 20, true, true)
        );
        return node;
    }

}
