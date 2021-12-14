package styleconstants.imagesvg;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class ThreePointH {

    public static Node getInstance() {

        Circle circle1 = new Circle(3,  12.5, 1.5);
        Circle circle2 = new Circle(10,   12.5, 1.5);
        Circle circle3 = new Circle(17, 12.5, 1.5);

        Shape union = Shape.union(Shape.union(circle1, circle2), circle3);
        union.setFill(Color.WHITE);
        union.setStrokeWidth(0);

        Pane pane = new Pane();
        pane.getChildren().add(union);
        pane.setPrefSize(25, 25);

        return pane;

    }

}
