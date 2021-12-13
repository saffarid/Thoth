package styleconstants.imagesvg;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

public class Minify {

    public static Node getInstance() {

        Rectangle rectangle = new Rectangle();
        rectangle.setX(5);
        rectangle.setY(10);

        rectangle.setHeight(11);
        rectangle.setWidth(11);

        rectangle.setArcHeight(3);
        rectangle.setArcWidth(3);

        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.WHITE);
        rectangle.setStrokeWidth(1.5);


        Pane pane = new Pane();
        pane.getChildren().add(rectangle);
        pane.setPrefSize(25, 25);

        return pane;

    }
}
