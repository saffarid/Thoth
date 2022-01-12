package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;

public class Purchase {

    public static Group getInstance() {
        SVGPath path = new SVGPath();

        path.setContent("M33.195,9.54A3.27,3.27,0,0,0,30.416,7.9H9.487L8.539,4.211A1.635,1.635,0,0,0,6.9,3H3.635a1.635,1.635,0,1,0,0,3.27H5.661l4.513,16.775a1.635,1.635,0,0,0,1.635,1.211H26.524a1.635,1.635,0,0,0,1.455-.9L33.342,12.63a3.27,3.27,0,0,0-.147-3.09Z");
        path.setTranslateX(1.27);
        path.setTranslateY(1.905);
        path.setFill(Color.WHITE);

        Circle circle1 = new Circle(2, 2, 2);
        circle1.setFill(Color.WHITE);
        circle1.setTranslateX(10);
        circle1.setTranslateY(30);

        Circle circle2 = new Circle(2.452, 2.452, 2.452);
        circle2.setFill(Color.WHITE);
        circle2.setTranslateX(26.16);
        circle2.setTranslateY(29.43);

        return new Group(
                TransparentBackground.getInstance()
                , path
                , circle1
                , circle2
        );
    }
    public static Group getInstance(
            double width,
            double height
    ) {
        Group instance = getInstance();
        instance.setScaleX(DefaultSize.WIDTH.getScaleX(width));
        instance.setScaleY(DefaultSize.HEIGHT.getScaleY(height));
        return instance;
    }

}
