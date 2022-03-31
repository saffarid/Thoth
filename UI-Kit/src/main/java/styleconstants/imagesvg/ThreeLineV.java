package styleconstants.imagesvg;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import styleconstants.Styleclasses;

import javax.swing.*;

public class ThreeLineV {
    public static Group getInstance() {
        Line line1 = new Line(7.5, 10,  32.5, 10);
        Line line2 = new Line(7.5, 20, 32.5, 20);
        Line line3 = new Line(7.5, 30, 32.5, 30);

        line1.setFill(Color.WHITE);
        line2.setFill(Color.WHITE);
        line3.setFill(Color.WHITE);

        line1.setStrokeWidth(3);
        line2.setStrokeWidth(3);
        line3.setStrokeWidth(3);

        line1.setStroke(Color.WHITE);
        line2.setStroke(Color.WHITE);
        line3.setStroke(Color.WHITE);

        line1.getStyleClass().add(Styleclasses.SVG_LINE);
        line2.getStyleClass().add(Styleclasses.SVG_LINE);
        line3.getStyleClass().add(Styleclasses.SVG_LINE);

        return new Group(
                TransparentBackground.getInstance()
                , line1
                , line2
                , line3
        );

    }
}
