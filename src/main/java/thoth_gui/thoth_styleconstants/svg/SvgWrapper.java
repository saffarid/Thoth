package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Group;
import javafx.scene.Node;
import layout.basepane.BorderPane;

public class SvgWrapper{

    public static Node getInstance(Images node){
        return getInstance(node, DefaultSize.WIDTH.getSize(), DefaultSize.HEIGHT.getSize());
    }

    public static Node getInstance(
            Images node,
            double width, double height
    ){
        BorderPane res = new BorderPane();

        res.setMinSize(width, height);
        res.setPrefSize(width, height);
        res.setMaxSize(width, height);

        Group instance = node.getSvg();
        instance.setScaleX(DefaultSize.WIDTH.getScaleX(width));
        instance.setScaleY(DefaultSize.HEIGHT.getScaleY(height));

        res.setCenter(instance);

        return res;
    }

}
