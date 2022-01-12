package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class ArrowRight {

    public static Group getInstance() {
        Group group = new Group();

        SVGPath path = new SVGPath();
        path.setContent("M10.668,28.353a1.666,1.666,0,0,1-1.283-2.733l7.467-8.933-7.2-8.95a1.7,1.7,0,1,1,2.683-2.1l8.05,10a1.667,1.667,0,0,1,0,2.117l-8.333,10A1.667,1.667,0,0,1,10.668,28.353Z");
        path.setTranslateX(5.999);
        path.setTranslateY(3.314);
        path.setFill(Color.WHITE);

        group.getChildren().addAll(
                TransparentBackground.getInstance(),
                path
        );

        return group;
    }
    public static Group getInstance(
            double width,
            double height
    ) {
        Group instance = new Group();

        SVGPath path = new SVGPath();
        path.setContent("M10.668,28.353a1.666,1.666,0,0,1-1.283-2.733l7.467-8.933-7.2-8.95a1.7,1.7,0,1,1,2.683-2.1l8.05,10a1.667,1.667,0,0,1,0,2.117l-8.333,10A1.667,1.667,0,0,1,10.668,28.353Z");
        path.setTranslateX(5.999);
        path.setTranslateY(3.314);
        path.setFill(Color.WHITE);

        instance.getChildren().addAll(
                TransparentBackground.getInstance(),
                path
        );
        instance.setScaleX(DefaultSize.WIDTH.getScaleX(width));
        instance.setScaleY(DefaultSize.HEIGHT.getScaleY(height));
        return instance;
    }

}
