package ThothGUI.ThothLite.Components.DBElementsView.ListCell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.*;
import ThothCore.ThothLite.DataType;
import controls.ListCell;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public abstract class IdentifiableViewCell
        extends BorderPane {

    private final static String STYLE_CLASS_CELL_CONTENT = "identifiable-cell-content";

    protected ImageView icon;
    protected Label title;
    protected Label subtitle;
    protected Label property;
    protected ImageView edit;

    protected IdentifiableViewCell(
            String url,
            String title,
            String subtitle,
            String property) {
        this.icon = setImageIcon(url);
        this.title = new Label();
        this.subtitle = new Label();
        this.property = new Label();
        this.edit = setImageIcon(thoth_styleconstants.Image.ARROW_RIGHT);

        setTextTitle(title);
        setTextSubtitle(subtitle);
        setTextProperty(property);

        setLeft(this.icon);
        setCenter(getFillCenter());
        setRight(this.edit);

        getStyleClass().add(STYLE_CLASS_CELL_CONTENT);
        setMargin(this, new Insets(0));
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

    private void setPosInGridPane(
            Node node
            , int columnIndex
            , int rowIndex
    ){
        GridPane.setColumnIndex(node, columnIndex);
        GridPane.setRowIndex(node, rowIndex);
    }

    private GridPane getFillCenter(){
        GridPane res = new GridPane();

        res.getColumnConstraints().addAll(
                new ColumnConstraints(100, 150, Double.MAX_VALUE, Priority.ALWAYS, HPos.LEFT, true)
                , new ColumnConstraints(100, 150, Double.MAX_VALUE, Priority.ALWAYS, HPos.RIGHT, true)
        );

        res.getRowConstraints().addAll(
                new RowConstraints(20)
                , new RowConstraints(20)
        );

        res.getChildren().addAll(
                this.title
                , this.subtitle
                , this.property
        );

        setPosInGridPane(this.title, 0, 0);
        setPosInGridPane(this.subtitle, 0, 1);
        setPosInGridPane(this.property, 1, 1);

        return res;
    }

    public ImageView setImageIcon(String url) {
        ImageView node = new ImageView();
        node.setImage(
                new Image(getClass().getResource(url).toExternalForm(), 40, 40, true, true)
        );
        return node;
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
