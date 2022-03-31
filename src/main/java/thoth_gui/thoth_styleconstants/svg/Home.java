package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import styleconstants.Styleclasses;
import styleconstants.imagesvg.DefaultSize;
import styleconstants.imagesvg.TransparentBackground;

public class Home {

    public static Group getInstance(){
        Group group = new Group();

        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(6.667);
        rectangle.setHeight(11.667);
//        rectangle.setFill(Color.WHITE);
        rectangle.setTranslateX(16.667);
        rectangle.setTranslateY(23.333);

        SVGPath path = new SVGPath();
        path.setContent("M32.033,15.631,19.183,2.5a1.667,1.667,0,0,0-2.367,0L3.967,15.647A3.333,3.333,0,0,0,3,18.031V32a3.333,3.333,0,0,0,3.15,3.333h5.183v-15A1.667,1.667,0,0,1,13,18.664H23a1.667,1.667,0,0,1,1.667,1.667v15H29.85A3.333,3.333,0,0,0,33,32V18.031a3.45,3.45,0,0,0-.967-2.4Z");
//        path.setFill(Color.WHITE);
        path.setTranslateX(2);
        path.setTranslateY(1.336);

        rectangle.getStyleClass().add(Styleclasses.SVG_PATH);
        path.getStyleClass().add(Styleclasses.SVG_PATH);

        group.getChildren().addAll(
                TransparentBackground.getInstance()
                , rectangle
                , path
        );

        return group;
    }


}
