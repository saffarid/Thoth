package styleconstants.imagesvg;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import styleconstants.imagesvg.DefaultSize;

public class TransparentBackground {

    public static Rectangle getInstance(){
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(DefaultSize.WIDTH.getSize());
        rectangle.setHeight(DefaultSize.HEIGHT.getSize());
        rectangle.setFill(Color.TRANSPARENT);
        return rectangle;
    }

}
