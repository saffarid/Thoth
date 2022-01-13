package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import styleconstants.imagesvg.DefaultSize;
import styleconstants.imagesvg.TransparentBackground;

public class Edit {

    public static Group getInstance(){
        Group group = new Group();

        SVGPath path = new SVGPath();
        path.setContent("M36.088,10.911,30.38,5.19a4.16,4.16,0,0,0-5.542-.146L6.088,23.834A4.18,4.18,0,0,0,4.9,26.361L4,35.067a2.081,2.081,0,0,0,2.083,2.276h.188l8.688-.793a4.163,4.163,0,0,0,2.521-1.19l18.75-18.791a4.015,4.015,0,0,0-.146-5.658ZM29,17.884l-5.583-5.6,4.063-4.176,5.688,5.7Z");
        path.setTranslateX(-0.666);
        path.setTranslateY(-0.676);
        path.setFill(Color.WHITE);

        group.getChildren().addAll(
                TransparentBackground.getInstance(),
                path
        );

        return group;
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
