package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import styleconstants.imagesvg.TransparentBackground;

public class History {

    public static Group getInstance(){
        Group group = new Group();

        Rectangle list = new Rectangle();

        list.setWidth(30);
        list.setHeight(35);
        list.setFill(Color.TRANSPARENT);
        list.setTranslateX(5);
        list.setTranslateY(2.5);
        list.setStrokeWidth(1.5);
        list.setStroke(Color.WHITE);

        Line line1 = new Line(10, 11.25, 30, 11.25);
        Line line2 = new Line(10, 20, 30, 20);
        Line line3 = new Line(10, 28.75, 30, 28.75);
//        Line line4 = new Line(10, 37.5, 30, 37.5);

        line1.setStrokeWidth(1.5);
        line1.setStroke(Color.WHITE);
        line2.setStrokeWidth(1.5);
        line2.setStroke(Color.WHITE);
        line3.setStrokeWidth(1.5);
        line3.setStroke(Color.WHITE);
//        line4.setStrokeWidth(1.5);
//        line4.setStroke(Color.WHITE);

        group.getChildren().addAll(
                TransparentBackground.getInstance()
                , list
                , line1
                , line2
                , line3
//                , line4
        );

        return group;
    }

}
