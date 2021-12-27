package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class Refresh {

    public static Node getInstance(){
        Pane pane = new Pane();

        SVGPath path = new SVGPath();
        path.setContent(
                "M17.615,11.807a.845.845,0,0,0-1.056.549,6.032,6.032,0,0,1-5.8,4.156A6,6,0,0,1,4.69,10.6a6,6,0,0,1,6.066-5.913A6.133,6.133,0,0,1,14.683,6.1l-1.833-.3a.847.847,0,1,0-.27,1.673l3.582.591h.144a.845.845,0,0,0,.287-.051.279.279,0,0,0,.084-.051.659.659,0,0,0,.169-.093l.076-.093c0-.042.076-.076.11-.127s0-.084.042-.118a1.132,1.132,0,0,0,.059-.152l.634-3.379a.86.86,0,0,0-1.69-.321L15.849,4.9A7.78,7.78,0,0,0,10.755,3,7.687,7.687,0,0,0,3,10.6a7.687,7.687,0,0,0,7.755,7.6,7.7,7.7,0,0,0,7.451-5.339.845.845,0,0,0-.591-1.056Z"
        );

        path.setFill(Color.WHITE);

        pane.setPrefSize(20, 20);
        pane.getChildren().add(path);

        return pane;
    }
}
