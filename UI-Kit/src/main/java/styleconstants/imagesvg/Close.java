package styleconstants.imagesvg;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class Close {

    public static Node getInstance(){
        Pane pane = new Pane();

        Line line1 = new Line(5, 5, 20, 20);
        line1.setStrokeWidth(1.5);

        Line line2 = new Line(5, 20, 20, 5);
        line2.setStrokeWidth(1.5);

        Shape union = Shape.union(line1, line2);
        union.setStroke(Color.TRANSPARENT);
        union.setFill(Color.WHITE);

        pane.setPrefSize(25, 25);
        pane.getChildren().add(union);

        return pane;
    }

}
