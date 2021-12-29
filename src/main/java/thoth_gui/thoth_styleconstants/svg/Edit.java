package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class Edit {

    public static Node getInstance(){
        Group group = new Group();

        SVGPath path = new SVGPath();
        path.setContent("M16.833,6.793,14.549,4.51a1.667,1.667,0,0,0-2.217-.058l-7.5,7.5a1.667,1.667,0,0,0-.475,1.008L4,16.435a.831.831,0,0,0,.833.908h.075l3.475-.317a1.667,1.667,0,0,0,1.008-.475l7.5-7.5a1.6,1.6,0,0,0-.058-2.258ZM14,9.576,11.766,7.343l1.625-1.667,2.275,2.275Z");
        path.setTranslateX(-0.666);
        path.setTranslateY(-0.676);
        path.setFill(Color.WHITE);

        group.getChildren().add(path);

        return group;
    }

    public static Node getInstance(
            double width
            , double height
    ){
        Node instance = getInstance();
        Pane pane = new Pane(instance);
        pane.setPrefSize(width, height);
        return pane;
    }

}
