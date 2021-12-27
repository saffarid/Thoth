package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class Product {
    public static Node getInstance() {
        Pane pane = new Pane();

        SVGPath path1 = new SVGPath();
        SVGPath path2 = new SVGPath();
        SVGPath path3 = new SVGPath();

        path1.setContent(
                "M10.54,9.214l7.051-2.958a1.342,1.342,0,0,0-.423-.315L11.2,3.149a1.549,1.549,0,0,0-1.326,0L3.913,5.941a1.342,1.342,0,0,0-.423.315Z"
        );
        path1.setTranslateX(-0.598);
        path1.setTranslateY(-0.515);
        path2.setContent(
                "M9.835,11.235,3,8.36v6.4a1.4,1.4,0,0,0,.829,1.259l5.957,2.809h.05Z"
        );
        path2.setTranslateX(-0.515);
        path2.setTranslateY(-1.434);
        path3.setContent(
                "M12.75,11.235v7.6h.041l5.965-2.809a1.4,1.4,0,0,0,.829-1.251V8.36Z"
        );
        path3.setTranslateX(-2.187);
        path3.setTranslateY(-1.434);

        path1.setFill(Color.WHITE);
        path2.setFill(Color.WHITE);
        path3.setFill(Color.WHITE);

        Group group = new Group(path1, path2, path3);

        pane.setPrefSize(20, 20);
        pane.getChildren().add(group);

        return pane;
    }
}
