package thoth_gui.thoth_lite.components.controls.sort_pane;

import controls.ComboBox;
import controls.ListCell;
import javafx.beans.value.ChangeListener;
import javafx.util.Callback;
import layout.basepane.HBox;

public abstract class SortPane
        extends HBox {

    protected final String labelText = "sort";
    protected ComboBox box = thoth_gui.thoth_lite.components.controls.ComboBox.getInstance();

    public static SortPane getInstance() {
        return new SortedPane();
    }

    public SortPane setSortItems(SortBy[] items) {
        for (SortBy item : items) {
            box.getItems().add(item);
        }
        return this;
    }

    public SortPane setCell() {
        box.setCellFactory(sort_byListView -> new SortCell());
        box.setButtonCell(new SortCell());
        return this;
    }

    public SortPane setCell(
            Callback callback
            , ListCell<? extends SortBy> cell
    ) {
        box.setCellFactory(callback);
        box.setButtonCell(cell);
        return this;
    }

    public <T extends SortBy> SortPane setValue(T value) {
        box.setValue(value);
        return this;
    }

    public abstract SortPane setSortMethod(ChangeListener<SortBy> sortMethod);

}
