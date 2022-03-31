package styleconstants.imagesvg;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import styleconstants.Styleclasses;

public class ArrowDown {

    public static Group getInstance() {

        SVGPath path = new SVGPath();

        path.setContent("M16.677,20.677a1.668,1.668,0,0,1-1.068-.384L5.6,11.953A1.67,1.67,0,0,1,7.736,9.385l8.941,7.473,8.941-7.206a1.71,1.71,0,1,1,2.118,2.686L17.728,20.394a1.668,1.668,0,0,1-1.051.284Z");
        path.setTranslateX(3.25);
        path.setTranslateY(5.2);
        path.setFill(Color.WHITE);

        path.getStyleClass().add(Styleclasses.SVG_PATH);
        return new Group(
                TransparentBackground.getInstance(),
                path
        );
    }

}
