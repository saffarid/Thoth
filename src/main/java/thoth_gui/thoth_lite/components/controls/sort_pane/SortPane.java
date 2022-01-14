package thoth_gui.thoth_lite.components.controls.sort_pane;

import controls.ComboBox;
import controls.ListCell;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import layout.basepane.HBox;
import thoth_gui.thoth_lite.components.scenes.db_elements_view.list_view.StoragableListView;

public abstract class SortPane extends HBox {

    protected final String labelText = "sort";
    protected ComboBox<SortBy> box = thoth_gui.thoth_lite.components.controls.ComboBox.getInstance();

    public static SortPane getInstance(){
        return new SortedPane();
    }

    public SortPane setSortItems(SortBy[] items) {
        for(SortBy item : items){
            box.getItems().add(item);
        }
        return this;
    }
    public SortPane setCell(ListCell<? extends SortBy> cell){
        box.setCellFactory(sort_byListView -> (javafx.scene.control.ListCell<SortBy>) cell);
        box.setButtonCell((javafx.scene.control.ListCell<SortBy>) cell);
        return this;
    };
    public <T extends SortBy> SortPane setValue(T value){
        box.setValue(value);
        return this;
    }

    public abstract SortPane setSortMethod(ChangeListener<SortBy> sortMethod);

}
