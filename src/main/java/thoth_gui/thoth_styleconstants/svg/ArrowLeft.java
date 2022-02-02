package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import styleconstants.imagesvg.TransparentBackground;

public class ArrowLeft {

    public static Group getInstance() {
        Group group = new Group();

        SVGPath path = new SVGPath();
        path.setContent("M17.722,28.334a1.667,1.667,0,0,1-1.3-.617l-8.05-10a1.667,1.667,0,0,1,0-2.117L16.7,5.6a1.669,1.669,0,0,1,2.567,2.133l-7.45,8.933,7.2,8.933a1.667,1.667,0,0,1-1.3,2.733Z");
        path.setTranslateX(5.328);
        path.setTranslateY(3.332);
        path.setFill(Color.WHITE);

        group.getChildren().addAll(
                TransparentBackground.getInstance(),
                path
        );

        return group;
    }

}
