package tools;

import javafx.scene.Node;
import layout.basepane.BorderPane;
import styleconstants.imagesvg.DefaultSize;

public class SvgWrapper{

    public static Node getInstance(Node node){
        return getInstance(node, DefaultSize.WIDTH.getSize(), DefaultSize.HEIGHT.getSize());
    }

    public static Node getInstance(
            Node node,
            double width, double height
    ){
        return getInstance(node, width, height, width, height);
    }

    public static Node getInstance(
            Node node,
            double width, double height,
            double viewboxWidth, double viewboxHeight
            ){
        BorderPane res = new BorderPane();

        res.setMinSize (viewboxWidth, viewboxHeight);
        res.setPrefSize(viewboxWidth, viewboxHeight);
        res.setMaxSize (viewboxWidth, viewboxHeight);

        node.setScaleX(DefaultSize.WIDTH.getScaleX(width));
        node.setScaleY(DefaultSize.HEIGHT.getScaleY(height));

        res.setCenter(node);

        return res;
    }

}
