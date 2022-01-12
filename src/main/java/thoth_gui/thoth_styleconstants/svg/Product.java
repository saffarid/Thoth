package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class Product {

    public static Group getInstance() {

        SVGPath path1 = new SVGPath();
        SVGPath path2 = new SVGPath();
        SVGPath path3 = new SVGPath();

        path1.setContent(
                "M16.559,14.063,3,8.36v12.7a2.778,2.778,0,0,0,1.644,2.5l11.817,5.572h.1Z"
        );
        path1.setTranslateX(1.931);
        path1.setTranslateY(5.38);
        path2.setContent(
                "M17.476,15.326,31.463,9.459a2.662,2.662,0,0,0-.838-.624L18.792,3.3a3.074,3.074,0,0,0-2.63,0L4.328,8.835a2.663,2.663,0,0,0-.838.624Z"
        );
        path2.setTranslateX(2.246);
        path2.setTranslateY(1.931);
        path3.setContent(
                "M12.75,14.063V29.135h.082l11.833-5.572a2.778,2.778,0,0,0,1.644-2.482V8.36Z"
        );
        path3.setTranslateX(8.205);
        path3.setTranslateY(5.38);

        path1.setFill(Color.WHITE);
        path2.setFill(Color.WHITE);
        path3.setFill(Color.WHITE);

        return new Group(
                TransparentBackground.getInstance()
                , path1
                , path2
                , path3
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
