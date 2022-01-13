package styleconstants.imagesvg;

import javafx.scene.Node;
import layout.basepane.BorderPane;

public class SvgWrapper{

    public static Node getInstance(Node node){
        return getInstance(node, DefaultSize.WIDTH.getSize(), DefaultSize.HEIGHT.getSize());
    }

    public static Node getInstance(
            Node node,
            double width, double height
    ){
        BorderPane res = new BorderPane();

        res.setMinSize(width, height);
        res.setPrefSize(width, height);
        res.setMaxSize(width, height);

        node.setScaleX(DefaultSize.WIDTH.getScaleX(width));
        node.setScaleY(DefaultSize.HEIGHT.getScaleY(height));

        res.setCenter(node);

        return res;
    }

}
