package thoth_gui.thoth_lite.components.controls;

import javafx.scene.Node;
import thoth_gui.thoth_styleconstants.svg.Images;
import tools.SvgWrapper;

public class LabeledInfoGraphic {

    public static Node getInstance(String s){
        controls.Label res = Label.getInstanse();
        res.setGraphic(
                SvgWrapper.getInstance(
                        Images.INFO(), 15, 15, 15, 15
                )
        );
        res.setTooltip(
                Tooltip.getInstance(s)
        );
        return res;
    }

}
