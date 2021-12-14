package styleconstants.imagesvg;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

public class Minify {
    public static Node getInstance() {

        SVGPath svgPath = new SVGPath();
        svgPath.setContent(
                        "M8.5,8.5 " +
                                "V6 " +
                                "a1.5,1.5,0,0,1,1.5,-1.5, " +
                                "H19, " +
                                "a1.5,1.5,0,0,1,1.5,1.5, " +
                                "V15, " +
                                "a1.5,1.5,0,0,1,-1.5,1.5, " +
                                "H16.5"
        );

        svgPath.setFill(Color.TRANSPARENT);
        svgPath.setStroke(Color.WHITE);
        svgPath.setStrokeWidth(1);

        Rectangle rectangle = new Rectangle();
        rectangle.setX(4.5);
        rectangle.setY(8.5);

        rectangle.setHeight(12);
        rectangle.setWidth(12);

        rectangle.setArcHeight(3);
        rectangle.setArcWidth(3);

        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.WHITE);
        rectangle.setStrokeWidth(1);

        Group group = new Group(svgPath, rectangle);


        Pane pane = new Pane();
        pane.getChildren().add(group);
        pane.setPrefSize(25, 25);

        return pane;

    }
}
