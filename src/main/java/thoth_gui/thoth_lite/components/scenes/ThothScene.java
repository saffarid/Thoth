package thoth_gui.thoth_lite.components.scenes;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_gui.thoth_lite.components.scenes.db_elements_view.list_view.IdentifiablesListView;
import window.Closeable;

public interface ThothScene
        extends Closeable {

    SimpleObjectProperty<Node> getToolsProperty();
    SimpleObjectProperty<Node> getContentProperty();
    void setCloseable(Closeable closeable);

    String getId();

}
