package thoth_gui.thoth_lite.components.scenes;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import layout.basepane.BorderPane;
import thoth_gui.thoth_lite.components.controls.Label;
import window.Closeable;

/**
 * Главная сцена рабочей области
 * */
public class Home
        extends ThothSceneImpl
{

    private static Home home;

    private Home() {
        super();
        this.id = Scenes.HOME.name();
        content = new SimpleObjectProperty<>(createContentNode());
        tools = new SimpleObjectProperty<>(new Pane());
    }

    public static Home getInstance() {
        if (home == null) {
            home = new Home();
        }
        return home;
    }

    @Override
    protected BorderPane createContentNode(){
        contentNode = new BorderPane(Label.getInstanse("Home page"));
        return contentNode;
    }

    @Override
    public void close() {

    }

    @Override
    public SimpleObjectProperty<Node> getToolsProperty() {
        return tools;
    }

    @Override
    public SimpleObjectProperty<Node> getContentProperty() {
        return content;
    }

    @Override
    protected Node createToolsNode() {
        return null;
    }

    @Override
    public void setCloseable(Closeable closeable) {
    }

    @Override
    public void open() {

    }
}
