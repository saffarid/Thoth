package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import styleconstants.Styleclasses;
import styleconstants.imagesvg.DefaultSize;
import styleconstants.imagesvg.TransparentBackground;

public class Point {

    public static Group getInstance(){

        Circle circle = new Circle(20, 20, 10);
        circle.setFill(Color.WHITE);

        Group group = new Group();

        circle.getStyleClass().add(Styleclasses.SVG_PATH);

        group.getChildren().addAll(
                TransparentBackground.getInstance(),
                circle
        );

        return group;
    }

}
