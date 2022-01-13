package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import styleconstants.imagesvg.DefaultSize;
import styleconstants.imagesvg.TransparentBackground;

public class Point {

    public static Group getInstance(){

        Circle circle = new Circle(20, 20, 10);
        circle.setFill(Color.WHITE);

        Group group = new Group();

        group.getChildren().addAll(
                TransparentBackground.getInstance(),
                circle
        );

        return group;
    }
    public static Group getInstance(
            double width,
            double height
    ) {
        Group instance = getInstance();
        instance.setScaleX(DefaultSize.WIDTH.getScaleX(width));
        instance.setScaleY(DefaultSize.HEIGHT.getScaleY(height));
        return instance;
    }

}
