package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import styleconstants.Styleclasses;
import styleconstants.imagesvg.DefaultSize;
import styleconstants.imagesvg.TransparentBackground;

public class Refresh {

    public static Group getInstance() {
        SVGPath path = new SVGPath();
        path.setContent(
                "M32.229,20.641a1.69,1.69,0,0,0-2.112,1.1A12.063,12.063,0,0,1,18.51,30.052,12,12,0,0,1,6.379,18.225,12,12,0,0,1,18.51,6.4,12.266,12.266,0,0,1,26.367,9.22L22.7,8.612a1.694,1.694,0,1,0-.541,3.345l7.164,1.183h.287a1.69,1.69,0,0,0,.574-.1.558.558,0,0,0,.169-.1,1.318,1.318,0,0,0,.338-.186l.152-.186c0-.084.152-.152.22-.253s0-.169.084-.237a2.264,2.264,0,0,0,.118-.3l1.267-6.758a1.72,1.72,0,0,0-3.379-.642L28.7,6.821a15.561,15.561,0,0,0-10.188-3.8A15.375,15.375,0,0,0,3,18.225,15.375,15.375,0,0,0,18.51,33.431a15.409,15.409,0,0,0,14.9-10.678,1.69,1.69,0,0,0-1.183-2.112Z"
        );
        path.setTranslateX(2.069);
        path.setTranslateY(2.069);

        path.setFill(Color.WHITE);

        path.getStyleClass().add(Styleclasses.SVG_PATH);

        return new Group(
                TransparentBackground.getInstance()
                , path
        );
    }

}
