package ThothGUI.ThothLite.Components.DBElementsView.ListCell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.*;
import controls.Label;
import controls.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import layout.basepane.BorderPane;

public abstract class IdentifiableListCell
        extends ListCell<BorderPane>{

    protected ImageView icon;
    protected Label title;
    protected Label subtitle;
    protected Label property;

    protected IdentifiableListCell(
            String url,
            String title,
            String subtitle,
            String property){
        this.icon = new ImageView();
        this.title = new Label();
        this.subtitle = new Label();
        this.property = new Label();

        setImageIcon(url);
        setTextTitle(title);
        setTextSubtitle(subtitle);
        setTextProperty(property);


    }

    static IdentifiableListCell getInstance(Identifiable identifiable){

        if (identifiable instanceof Storagable) {
            return new ProductListCell( (Storagable) identifiable );
        } else if (identifiable instanceof Storing) {
            return new StoringListCell( (Storing) identifiable );
        } else if (identifiable instanceof Purchasable) {
            return new PurchaseListCell( (Purchasable) identifiable );
        } else if (identifiable instanceof Projectable) {
            return new ProjectListCell( (Projectable) identifiable );
        } else if (identifiable instanceof Orderable) {
            return new OrderListCell( (Orderable) identifiable);
        }

        return null;
    }

    public void setImageIcon(String url) {
        icon.setImage(
                new Image(getClass().getResource(url).toExternalForm(), 40, 40, true, true)
        );
    }

    public void setTextTitle(String text) {
        this.title.setText(text);
    }

    public void setTextSubtitle(String text) {
        this.subtitle.setText(text);
    }

    public void setTextProperty(String text) {
        this.property.setText(text);
    }


}
