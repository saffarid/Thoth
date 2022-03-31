package styleconstants.imagesvg;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import styleconstants.Styleclasses;

public class Iconify {

    public static Group getInstance(){

        Line line = new Line(7.5, 20, 32.5, 20);

        line.setStroke(Color.WHITE);
        line.setStrokeWidth(3);

        line.getStyleClass().add(Styleclasses.SVG_LINE);

        return new Group(
                TransparentBackground.getInstance(),
                line
        );
    }

}
