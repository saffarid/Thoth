package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import styleconstants.Styleclasses;
import styleconstants.imagesvg.DefaultSize;
import styleconstants.imagesvg.TransparentBackground;

public class TradingDown {

    public static Group getInstance(){
        Group group = new Group();

        SVGPath path = new SVGPath();
        path.setContent("M33,16a1.667,1.667,0,1,0-3.333,0v3.833L22.6,11.5a1.667,1.667,0,0,0-2.117-.35L13.368,15.5,5.951,6.6A1.669,1.669,0,1,0,3.384,8.734l8.333,10a1.667,1.667,0,0,0,2.133.367l7.133-4.283,6.667,7.85H23A1.667,1.667,0,1,0,23,26h8.333a1.833,1.833,0,0,0,.6-.117l.233-.133a1.983,1.983,0,0,0,.25-.15,1.25,1.25,0,0,0,.233-.283,1.834,1.834,0,0,0,.15-.233,1.067,1.067,0,0,0,.083-.283A1.3,1.3,0,0,0,33,24.334Z");
        path.setFill(Color.WHITE);
        path.setTranslateX(1.999);
        path.setTranslateY(3.999);

        path.getStyleClass().add(Styleclasses.SVG_PATH);

        group.getChildren().addAll(
                TransparentBackground.getInstance(),
                path
        );

        return group;
    }

}
