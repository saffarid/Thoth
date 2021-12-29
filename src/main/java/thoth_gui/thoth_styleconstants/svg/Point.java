package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Point {

    public static Node getInstance(){
        Group group = new Group();

        Circle circle = new Circle(4.5, 4.5, 4.5);

        circle.setFill(Color.WHITE);

        group.getChildren().addAll(
                circle
        );

        return group;
    }

    public static Node getInstance(
            double width,
            double height
    ){
        Node instance = getInstance();
        Pane pane = new Pane(instance);
        pane.setPrefSize(width, height);
        return pane;
    }

}
