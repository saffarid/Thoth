package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Plus {
    public static final Node getInstance(){
        Group res = new Group();

        Line hor = new Line(2.5, 10, 17.5, 10);
        Line ver = new Line(10, 2.5, 10, 17.5);

        hor.setStroke(Color.WHITE);
        ver.setStroke(Color.WHITE);

        hor.setStrokeWidth(1.5);
        ver.setStrokeWidth(1.5);

        res.getChildren().addAll(
                hor
                , ver
        );

        return res;
    }
}
