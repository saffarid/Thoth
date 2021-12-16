package styleconstants.imagesvg;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;

import javax.swing.*;

public class ThreeLineV {
    public static Node getInstance() {


        Line line1 = new Line(2.5, 5,  17.5, 5);
        Line line2 = new Line(2.5, 10, 17.5, 10);
        Line line3 = new Line(2.5, 15, 17.5, 15);

        line1.setFill(Color.WHITE);
        line2.setFill(Color.WHITE);
        line3.setFill(Color.WHITE);

        line1.setStrokeWidth(1);
        line2.setStrokeWidth(1);
        line3.setStrokeWidth(1);

        line1.setStroke(Color.WHITE);
        line2.setStroke(Color.WHITE);
        line3.setStroke(Color.WHITE);

        Group group = new Group(line1, line2, line3);
        group.setAutoSizeChildren(true);

        Pane pane = new Pane();
        pane.getChildren().add(group);
        pane.setPrefSize(20, 20);

        return pane;

    }
}
