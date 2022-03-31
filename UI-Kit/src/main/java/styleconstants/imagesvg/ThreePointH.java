package styleconstants.imagesvg;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import styleconstants.Styleclasses;

public class ThreePointH {

    public static Group getInstance() {

        Circle circle1 = new Circle(10,  20, 3);
        Circle circle2 = new Circle(20, 20, 3);
        Circle circle3 = new Circle(30, 20, 3);

        circle1.setFill(Color.WHITE);
        circle2.setFill(Color.WHITE);
        circle3.setFill(Color.WHITE);

        circle1.getStyleClass().add(Styleclasses.SVG_PATH);
        circle2.getStyleClass().add(Styleclasses.SVG_PATH);
        circle3.getStyleClass().add(Styleclasses.SVG_PATH);

        return new Group(
                TransparentBackground.getInstance()
                , circle1
                , circle2
                , circle3
        );

    }

}
