package styleconstants.imagesvg;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import styleconstants.Styleclasses;

public class Close {

    public static Group getInstance(){
        Line line1 = new Line(7.5, 7.5, 32.5, 32.5);
        line1.setStrokeWidth(3);
        line1.setStroke(Color.WHITE);

        Line line2 = new Line(7.5, 32.5, 32.5, 7.5);
        line2.setStrokeWidth(3);
        line2.setStroke(Color.WHITE);

        line1.getStyleClass().add(Styleclasses.SVG_LINE);
        line2.getStyleClass().add(Styleclasses.SVG_LINE);

        return new Group(
                TransparentBackground.getInstance()
                , line1
                , line2
        );
    }

}
