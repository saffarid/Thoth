package thoth_gui.thoth_lite.components.scenes;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import layout.basepane.BorderPane;
import layout.basepane.TableView;
import window.Closeable;

public class FinancialOperations
        extends ThothSceneImpl{


    public FinancialOperations() {
        tools = new SimpleObjectProperty<>(new Pane());
        content = new SimpleObjectProperty<>(createContent());
    }

    private Node createContent(){
        contentNode = new BorderPane(new TableView<>());

        return contentNode;
    }

    @Override
    public void close() {

    }

    @Override
    public void setCloseable(Closeable closeable) {

    }
}
