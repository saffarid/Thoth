package thoth_gui.thoth_lite.components.controls.sort_pane;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import thoth_gui.thoth_lite.components.controls.Label;
import thoth_gui.thoth_lite.tools.TextCase;
import tools.BackgroundWrapper;

public class SortedPane
        extends SortPane
{
    protected SortedPane() {
        super();

        setSpacing(2);
        setAlignment(Pos.CENTER_LEFT);

        getChildren().addAll(
                Label.getInstanse(labelText, TextCase.NORMAL)
                , box
        );
    }

    @Override
    public SortPane setSortMethod(ChangeListener<SortBy> sortMethod) {
        box.valueProperty().addListener(sortMethod);
        return this;
    }
}
