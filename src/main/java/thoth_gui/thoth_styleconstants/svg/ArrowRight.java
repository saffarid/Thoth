package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.SVGPath;

public class ArrowRight {

    public static Node getInstance() {
        Group group = new Group();

        SVGPath path = new SVGPath();
        path.setContent("M11.171,23.677a1.667,1.667,0,0,1-1.183-2.85L15.5,15.344,10.2,9.827a1.668,1.668,0,1,1,2.367-2.35L19,14.144a1.667,1.667,0,0,1,0,2.333l-6.667,6.667A1.667,1.667,0,0,1,11.171,23.677Z");
        path.setTranslateX(6.329);
        path.setTranslateY(4.656);
        path.setFill(Color.valueOf("#b8b8b8"));

        group.getChildren().addAll(
                path
        );

        return group;
    }

    public static Node getInstance(
            double width,
            double height
    ) {
        Node instance = getInstance();
        Pane pane = new Pane(instance);
        pane.setPrefSize(width, height);
        return pane;
    }

}
