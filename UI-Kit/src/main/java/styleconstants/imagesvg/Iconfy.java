package styleconstants.imagesvg;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Iconfy {

    public static Node getInstance(){
        Pane pane = new Pane();

        Line line = new Line(7.5, 12.5, 17.5, 12.5);

        line.setStroke(Color.WHITE);
        line.setStrokeWidth(1);

        line.setFill(Color.TRANSPARENT);

        pane.setPrefSize(25, 25);
        pane.getChildren().add(line);

        return pane;
    }

}
