package window;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import layout.custompane.Title;
import styleconstants.Styleclasses;
import styleconstants.Stylesheets;

import java.net.URL;

public abstract class Window
        extends BorderPane
        implements Closeable {

    protected Title title;
    protected SimpleBooleanProperty isMinify;
    protected Stage stage;

    public Window() {
        super();
        isMinify = new SimpleBooleanProperty(true);
        getStyleClass().addAll(
                Styleclasses.WINDOW
        );
        getStylesheets().add(
                Window.class.getResource(Stylesheets.WINDOW).toExternalForm()
        );
    }

    public Title getTitle() {
        return title;
    }

    protected void iconify() {
        stage.setIconified(true);
    }

    public boolean isIsMinify() {
        return isMinify.get();
    }

    public SimpleBooleanProperty isMinifyProperty() {
        return isMinify;
    }

    public void setIsMinify(boolean isMinify) {
        this.isMinify.set(isMinify);
    }
}
