package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import styleconstants.Styleclasses;
import styleconstants.imagesvg.DefaultSize;
import styleconstants.imagesvg.TransparentBackground;

public class List {

    public static Group getInstance(){
        Circle circle1 = new Circle(7.5, 10, 2, Color.WHITE);
        Circle circle2 = new Circle(7.5, 20, 2, Color.WHITE);
        Circle circle3 = new Circle(7.5, 30, 2, Color.WHITE);

        Line line1 = new Line(15, 10, 35, 10 );
        Line line2 = new Line(15, 20, 35, 20);
        Line line3 = new Line(15, 30, 35, 30);

        line1.setStrokeWidth(3);
        line2.setStrokeWidth(3);
        line3.setStrokeWidth(3);

        line1.setStroke(Color.WHITE);
        line2.setStroke(Color.WHITE);
        line3.setStroke(Color.WHITE);

        circle1.getStyleClass().add(Styleclasses.SVG_PATH);
        circle2.getStyleClass().add(Styleclasses.SVG_PATH);
        circle3.getStyleClass().add(Styleclasses.SVG_PATH);
        line1.getStyleClass().add(Styleclasses.SVG_PATH);
        line2.getStyleClass().add(Styleclasses.SVG_PATH);
        line3.getStyleClass().add(Styleclasses.SVG_PATH);

        Group group = new Group(
                TransparentBackground.getInstance()
                , circle1
                , circle2
                , circle3
                , line1
                , line2
                , line3);

        return group;
    }

}
