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
        content = new SimpleObjectProperty<>(createContent());
        tools = new SimpleObjectProperty<>(new Pane());
    }

    public static Home getInstance() {
        if (home == null) {
            home = new Home();
        }
        return home;
    }

    private BorderPane createContent(){
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
    public void setCloseable(Closeable closeable) {
    }
}
