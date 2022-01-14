package thoth_gui.thoth_lite.components.scenes;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import layout.basepane.BorderPane;

public abstract class ThothSceneImpl
    implements ThothScene
{
    protected SimpleObjectProperty<Node> tools;
    protected SimpleObjectProperty<Node> content;

    protected BorderPane contentNode;
    protected BorderPane toolsNode;

    @Override
    public SimpleObjectProperty<Node> getToolsProperty() {
        return tools;
    }

    @Override
    public SimpleObjectProperty<Node> getContentProperty() {
        return content;
    }

}
