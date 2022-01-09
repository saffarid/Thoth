package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class Checkmark {

    public static Node getInstance(){
        Group group = new Group();

        SVGPath path = new SVGPath();

        path.setContent("M13.767,26.029a1.667,1.667,0,0,1-1.217-.533l-8.1-8.617A1.668,1.668,0,1,1,6.884,14.6l6.867,7.317L27.767,6.579a1.667,1.667,0,1,1,2.467,2.233L15,25.479a1.667,1.667,0,0,1-1.217.55Z");
        path.setTranslateX(2.666);
        path.setTranslateY(3.971);
        path.setFill(Color.WHITE);

        group.getChildren().addAll(path);

        return group;
    }

    public static Node getInstance(
            double width,
            double height
    ){
        Node instance = getInstance();
        instance.setScaleX( DefaultSize.WIDTH.getScaleX(width) );
        instance.setScaleY( DefaultSize.HEIGHT.getScaleY(height) );
        Pane pane = new Pane(instance);
        pane.setPrefSize(width, height);
        return pane;
    }


}