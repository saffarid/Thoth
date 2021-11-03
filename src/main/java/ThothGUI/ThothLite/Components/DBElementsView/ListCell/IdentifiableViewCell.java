package ThothGUI.ThothLite.Components.DBElementsView.ListCell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.*;
import ThothCore.ThothLite.DataType;
import controls.Label;
import controls.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public abstract class IdentifiableViewCell
        extends BorderPane {

    protected ImageView icon;
    protected Label title;
    protected Label subtitle;
    protected Label property;

    protected IdentifiableViewCell(
            String url,
            String title,
            String subtitle,
            String property) {
        this.icon = new ImageView();
        this.title = new Label();
        this.subtitle = new Label();
        this.property = new Label();

        setImageIcon(url);
        setTextTitle(title);
        setTextSubtitle(subtitle);
        setTextProperty(property);


    }

    static IdentifiableViewCell getInstance(Identifiable identifiable) {

        if(identifiable instanceof Orderable) {
            return new OrderViewCell((Orderable) identifiable);
        }else if(identifiable instanceof Storagable) {
            return new ProductViewCell((Storagable) identifiable);
        }else if(identifiable instanceof Projectable) {
            return new ProjectViewCell((Projectable) identifiable);
        }else if(identifiable instanceof Purchasable) {
            return new PurchaseViewCell((Purchasable) identifiable);
        }else if(identifiable instanceof Storing){
            return new StoringViewCell((Storing) identifiable);
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
