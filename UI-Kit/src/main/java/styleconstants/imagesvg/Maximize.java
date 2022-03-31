package styleconstants.imagesvg;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import styleconstants.Styleclasses;

public class Maximize {
    public static Group getInstance(){
        Rectangle rectangle = new Rectangle();
        rectangle.setX(7.5);
        rectangle.setY(7.5);

        rectangle.setHeight(25);
        rectangle.setWidth(25);

        rectangle.setArcHeight(5);
        rectangle.setArcWidth(5);

        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.WHITE);
        rectangle.setStrokeWidth(3);

        rectangle.getStyleClass().add(Styleclasses.SVG_LINE);

        return new Group(
                TransparentBackground.getInstance(),
                rectangle
        );
    }
}
