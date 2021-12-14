package styleconstants.imagesvg;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Maximize {
    public static Node getInstance(){
        Pane pane = new Pane();

        Rectangle rectangle = new Rectangle();
        rectangle.setX(7.5);
        rectangle.setY(7.5);

        rectangle.setHeight(10);
        rectangle.setWidth(10);

        rectangle.setArcHeight(3);
        rectangle.setArcWidth(3);

        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.WHITE);
        rectangle.setStrokeWidth(1);

        pane.setPrefSize(25, 25);
        pane.getChildren().add(rectangle);

        return pane;
    }
}
