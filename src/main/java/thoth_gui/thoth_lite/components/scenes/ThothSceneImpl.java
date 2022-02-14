package thoth_gui.thoth_lite.components.scenes;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import layout.basepane.BorderPane;
import thoth_gui.thoth_lite.components.controls.ToolsPane;
import thoth_gui.thoth_lite.main_window.Workspace;
import window.Closeable;

public abstract class ThothSceneImpl
    implements ThothScene
{
    protected static final double svgHeightTool = Workspace.svgHeightTools;
    protected static final double svgWidthTool = Workspace.svgWidthTools;

    protected static final double svgViewBoxHeightTool = Workspace.svgViewBoxHeightTools;
    protected static final double svgViewBoxWidthTool = Workspace.svgViewBoxWidthTools;

    protected SimpleObjectProperty<Node> tools;
    protected SimpleObjectProperty<Node> content;

    protected BorderPane contentNode;
    protected ToolsPane toolsNode;

    @Override
    public SimpleObjectProperty<Node> getToolsProperty() {
        return tools;
    }

    @Override
    public SimpleObjectProperty<Node> getContentProperty() {
        return content;
    }

    protected abstract Node createToolsNode();
    protected abstract Node createContentNode();

}
