package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;

public class Purchase {
    public static Node getInstance() {
        SVGPath path = new SVGPath();
        path.setContent("M17.553,6.265a1.632,1.632,0,0,0-1.387-.816H5.717L5.244,3.6A.816.816,0,0,0,4.428,3H2.8a.816.816,0,1,0,0,1.632H3.807L6.06,13.007a.816.816,0,0,0,.816.6h7.346a.816.816,0,0,0,.726-.449l2.677-5.354a1.632,1.632,0,0,0-.073-1.543Z");
        path.setTranslateX(-0.347);
        path.setTranslateY(-0.551);
        path.setFill(Color.WHITE);

        Circle circle1 = new Circle(1, 1, 1);
        circle1.setFill(Color.WHITE);
        circle1.setTranslateX(5);
        circle1.setTranslateY(15);

        Circle circle2 = new Circle(1.224, 1.224, 1.224);
        circle2.setFill(Color.WHITE);
        circle2.setTranslateX(13.06);
        circle2.setTranslateY(14.693);

        Group group = new Group(path, circle1, circle2);

        return group;
    }
}
