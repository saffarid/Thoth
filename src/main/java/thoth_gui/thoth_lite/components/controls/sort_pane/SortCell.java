package thoth_gui.thoth_lite.components.controls.sort_pane;

import controls.ListCell;
import javafx.geometry.Pos;
import javafx.scene.Node;
import layout.basepane.HBox;
import org.json.simple.parser.ParseException;
import thoth_gui.config.Config;
import thoth_gui.thoth_lite.components.controls.Label;
import thoth_gui.thoth_lite.tools.TextCase;
import thoth_gui.thoth_styleconstants.svg.Images;
import tools.SvgWrapper;

import java.io.IOException;

public class SortCell
        extends ListCell<SortBy> {

    private static final double sizeSvg = 15;
    private static final double sizeViewboxSvg = 20;

    private final Node down = SvgWrapper.getInstance(
            Images.ARROW_DOWN(), sizeSvg, sizeSvg, sizeViewboxSvg, sizeViewboxSvg
    );

    private final Node up = SvgWrapper.getInstance(
            Images.ARROW_UP(), sizeSvg, sizeSvg, sizeViewboxSvg, sizeViewboxSvg
    );

    public SortCell() {
        super();
    }

    private Node createGraphic(String text) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);

        Node node = (text.endsWith("down") || text.endsWith("up")) ? ((text.endsWith("down")) ? (down) : (up)) : (null);

        hBox.getChildren().addAll(
                Label.getInstanse(
                        (text.endsWith("down")) ? (text.replace("_down", "")) : (text.replace("_up", "")), TextCase.LOWER
                )
        );

        if (node != null) hBox.getChildren().addAll(node);

        return hBox;
    }

    @Override
    protected void updateItem(SortBy t, boolean b) {
        if (t != null) {
            super.updateItem(t, b);
            fontProperty().bind(Config.getInstance().getFont().fontProperty());
            setGraphic(
                    createGraphic(t.getSortName())
            );
        }
    }
}
