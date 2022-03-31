package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import styleconstants.Styleclasses;
import styleconstants.imagesvg.DefaultSize;
import styleconstants.imagesvg.TransparentBackground;

public class Trash {

    public static Group getInstance() {

        SVGPath path = new SVGPath();

        path.setContent(
                "M33.667,8.668H25.333V5.884A4.033,4.033,0,0,0,21.167,2h-5A4.033,4.033,0,0,0,12,5.884V8.668H3.667a1.667,1.667,0,1,0,0,3.333H5.333V30.334a5,5,0,0,0,5,5H27a5,5,0,0,0,5-5V12h1.667a1.667,1.667,0,0,0,0-3.333ZM15.333,25.334a1.667,1.667,0,0,1-3.333,0V18.668a1.667,1.667,0,1,1,3.333,0Zm0-19.45c0-.267.35-.55.833-.55h5c.483,0,.833.283.833.55V8.668H15.333Zm10,19.45a1.667,1.667,0,0,1-3.333,0V18.668a1.667,1.667,0,1,1,3.333,0Z"
        );
        path.setTranslateX(1.333);
        path.setTranslateY(1.332);
        path.setFill(Color.WHITE);

        path.getStyleClass().add(Styleclasses.SVG_PATH);

        return new Group(
                TransparentBackground.getInstance()
                , path
        );
    }
}
