package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.SVGPath;
import styleconstants.imagesvg.TransparentBackground;

public class Info {

    public static Group getInstance(){

        SVGPath path1 = new SVGPath();
        path1.setContent("M18.526,2A16.526,16.526,0,1,0,35.052,18.526,16.526,16.526,0,0,0,18.526,2Zm0,29.747A13.221,13.221,0,1,1,31.747,18.526,13.221,13.221,0,0,1,18.526,31.747Z");
        path1.setTranslateX(1.305);
        path1.setTranslateY(1.305);
        path1.setFill(Color.WHITE);

        Circle circle = new Circle(1.5, 1.5 ,1.5, Color.WHITE);
        circle.setTranslateX(18);
        circle.setTranslateY(12);

        SVGPath path2 = new SVGPath();
        path2.setContent("M12.653,10A1.653,1.653,0,0,0,11,11.653v8.263a1.653,1.653,0,1,0,3.305,0V11.653A1.653,1.653,0,0,0,12.653,10Z");
        path2.setTranslateX(7.178);
        path2.setTranslateY(6.526);
        path2.setFill(Color.WHITE);

        return new Group(
                TransparentBackground.getInstance()
                , path1
                , circle
                , path2
                );
    }

}
