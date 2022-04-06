package styleconstants.imagesvg;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import styleconstants.Styleclasses;

public class Minify {
    public static Group getInstance() {

        SVGPath svgPath = new SVGPath();
        svgPath.setContent(
                        "M15,15 " +
                                "V9 " +
                                "a1.5,1.5,0,0,1,1.5,-1.5, " +
                                "H31 " +
                                "a1.5,1.5,0,0,1,1.5,1.5, " +
                                "V23.5 " +
                                "a1.5,1.5,0,0,1,-1.5,1.5, " +
                                "H25"
        );

        svgPath.setFill(Color.TRANSPARENT);
        svgPath.setStroke(Color.WHITE);
        svgPath.setStrokeWidth(2);

        Rectangle rectangle = new Rectangle();
        rectangle.setX(7.5);
        rectangle.setY(15);

        rectangle.setHeight(17.5);
        rectangle.setWidth(17.5);

        rectangle.setArcHeight(3);
        rectangle.setArcWidth(3);

        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.WHITE);
        rectangle.setStrokeWidth(2);

        svgPath.getStyleClass().add(Styleclasses.SVG_LINE);
        rectangle.getStyleClass().add(Styleclasses.SVG_LINE);

        return new Group(
                TransparentBackground.getInstance()
                , svgPath
                , rectangle
        );

    }
}
