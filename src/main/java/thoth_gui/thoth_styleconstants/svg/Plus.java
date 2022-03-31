package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import styleconstants.Styleclasses;
import styleconstants.imagesvg.DefaultSize;
import styleconstants.imagesvg.TransparentBackground;

public class Plus {

    public static final Group getInstance(){
        Group res = new Group();

        Line hor = new Line(2.5, 20, 37.5, 20);
        Line ver = new Line(20, 2.5, 20, 37.5);

        hor.setStroke(Color.WHITE);
        ver.setStroke(Color.WHITE);

        hor.setStrokeWidth(3);
        ver.setStrokeWidth(3);

        hor.getStyleClass().add(Styleclasses.SVG_LINE);
        ver.getStyleClass().add(Styleclasses.SVG_LINE);

        res.getChildren().addAll(
                TransparentBackground.getInstance()
                , hor
                , ver
        );

        return res;
    }

}
