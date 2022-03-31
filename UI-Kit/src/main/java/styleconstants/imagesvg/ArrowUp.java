package styleconstants.imagesvg;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import styleconstants.Styleclasses;

public class ArrowUp {

    public static Group getInstance() {

        SVGPath path = new SVGPath();

        path.setContent("M26.46,19.559a1.651,1.651,0,0,1-1.056-.38l-8.847-7.395-8.864,7.13a1.655,1.655,0,1,1-2.08-2.575l9.9-7.972a1.651,1.651,0,0,1,2.1,0l9.9,8.253a1.666,1.666,0,0,1-1.056,2.938Z");
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
