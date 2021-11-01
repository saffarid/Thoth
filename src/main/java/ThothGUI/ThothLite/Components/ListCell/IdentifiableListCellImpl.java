package ThothGUI.ThothLite.Components.ListCell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import controls.Label;
import controls.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import layout.basepane.BorderPane;

public abstract class IdentifiableListCellImpl
        extends ListCell<BorderPane>
        implements IdentifiableListCell{

    protected ImageView icon;
    protected Label title;
    protected Label subtitle;
    protected Label property;

    protected IdentifiableListCellImpl(
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

    @Override
    public void setImageIcon(String url) {
        icon.setImage(
                new Image(getClass().getResource(url).toExternalForm(), 40, 40, true, true)
        );
    }

    @Override
    public void setTextTitle(String text) {
        this.title.setText(text);
    }

    @Override
    public void setTextSubtitle(String text) {
        this.subtitle.setText(text);
    }

    @Override
    public void setTextProperty(String text) {
        this.property.setText(text);
    }
}
