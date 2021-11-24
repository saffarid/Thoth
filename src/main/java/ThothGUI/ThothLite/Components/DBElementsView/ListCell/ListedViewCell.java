package ThothGUI.ThothLite.Components.DBElementsView.ListCell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothGUI.thoth_styleconstants.Image;
import controls.TextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;

public class ListedViewCell extends IdentifiableViewCell {

    protected ListedViewCell(Listed listed) {
        super(Image.LIST, listed.getValue(), "", "");

        ImageView point = setImageIcon(Image.POINT, 7.5, 7.5);
        TextField node = new TextField(listed.getValue());

        setLeft(point);
        setCenter(node);
        setRight(setImageIcon(Image.TRASH, 20, 20));

        node.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if(!t1 && !node.getText().equals("")){
                listed.setValue(node.getText());
            }
        });

        setPadding(new Insets(2));
        setAlignment(point, Pos.CENTER);
    }

    protected ImageView setImageIcon(String url, double width, double height) {
        ImageView node = new ImageView();
        node.setImage(
                new javafx.scene.image.Image(
                        getClass().getResource(url).toExternalForm()
                        , width , height, true, true
                )
        );
        return node;
    }

}
