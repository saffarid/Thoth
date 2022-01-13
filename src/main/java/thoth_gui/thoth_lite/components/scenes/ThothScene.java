package thoth_gui.thoth_lite.components.scenes;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import window.Closeable;

public interface ThothScene
        extends Closeable {

    SimpleObjectProperty<Node> getToolsProperty();
    SimpleObjectProperty<Node> getContentProperty();
    void setCloseable(Closeable closeable);

}
