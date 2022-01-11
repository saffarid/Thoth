package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;

public class TradingUp {

    public static Node getInstance(){
    Group group = new Group();

    Rectangle rectangle1 = new Rectangle();
    rectangle1.setWidth(DefaultSize.WIDTH.getSize());
    rectangle1.setHeight(DefaultSize.HEIGHT.getSize());
    rectangle1.setFill(Color.TRANSPARENT);

    SVGPath path = new SVGPath();
    path.setContent("M33,7.667a1.3,1.3,0,0,0,0-.35,1.067,1.067,0,0,0-.083-.283,1.833,1.833,0,0,0-.15-.233,1.25,1.25,0,0,0-.233-.283l-.2-.117a1.15,1.15,0,0,0-.317-.167h-.333A1.167,1.167,0,0,0,31.335,6H23a1.667,1.667,0,1,0,0,3.333h4.717l-6.667,7.85-7.2-4.283a1.667,1.667,0,0,0-2.133.367l-8.333,10A1.669,1.669,0,0,0,5.951,25.4l7.417-8.9,7.117,4.267a1.667,1.667,0,0,0,2.117-.35l7.067-8.25V16A1.667,1.667,0,0,0,33,16Z");
    path.setFill(Color.WHITE);
    path.setTranslateX(1.999);
    path.setTranslateY(4);

    group.getChildren().addAll(rectangle1, path);

    return group;
}

    public static Node getInstance(
            double width
            , double height
    ){
        Node instance = getInstance();
        instance.setScaleX(DefaultSize.WIDTH.getScaleX(width));
        instance.setScaleY(DefaultSize.HEIGHT.getScaleY(height));
        return instance;
    }
}
