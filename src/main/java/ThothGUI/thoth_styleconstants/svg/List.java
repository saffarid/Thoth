package ThothGUI.thoth_styleconstants.svg;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class List {
    public static Node getInstance(){
        Pane pane = new Pane();

        Circle circle1 = new Circle(3.5, 5,  1.5, Color.WHITE);
        Circle circle2 = new Circle(3.5, 10, 1.5, Color.WHITE);
        Circle circle3 = new Circle(3.5, 15, 1.5, Color.WHITE);

        Line line1 = new Line(8, 5,  18, 5 );
        Line line2 = new Line(8, 10, 18, 10);
        Line line3 = new Line(8, 15, 18, 15);

        line1.setFill(Color.WHITE);
        line2.setFill(Color.WHITE);
        line3.setFill(Color.WHITE);

        line1.setStroke(Color.WHITE);
        line2.setStroke(Color.WHITE);
        line3.setStroke(Color.WHITE);

        Group group = new Group(circle1, circle2, circle3, line1, line2, line3);

        pane.setPrefSize(20, 20);
        pane.getChildren().add(group);

        return pane;
    }
}
