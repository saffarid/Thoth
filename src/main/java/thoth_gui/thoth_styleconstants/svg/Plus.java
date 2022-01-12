package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Plus {

    public static final Group getInstance(){
        Group res = new Group();

        Line hor = new Line(2.5, 20, 37.5, 20);
        Line ver = new Line(20, 2.5, 20, 37.5);

        hor.setStroke(Color.WHITE);
        ver.setStroke(Color.WHITE);

        hor.setStrokeWidth(3);
        ver.setStrokeWidth(3);

        res.getChildren().addAll(
                TransparentBackground.getInstance()
                , hor
                , ver
        );

        return res;
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
