package thoth_gui.thoth_lite.components.controls.sort_pane;

import javafx.scene.paint.Color;
import thoth_gui.thoth_lite.components.controls.Label;

public class SortCellImpl<T extends SortBy>
        extends SortCell<T>{

    @Override
    protected void updateItem(T t, boolean b) {
        if (t != null) {
            super.updateItem(t, b);
            System.out.println(t.getSortName());
            setText(t.getSortName());
        }
    }
}
