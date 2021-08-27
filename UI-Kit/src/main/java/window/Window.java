package window;

import javafx.scene.layout.BorderPane;
import layout.title.Title;
import styleconstants.Styleclasses;
import styleconstants.Stylesheets;

public abstract class Window extends BorderPane {

    protected Title title;

    public Window() {
        super();

        getStylesheets().add(
                getClass().getResource(Stylesheets.WINDOW).toExternalForm()
        );

        getStyleClass().addAll(
                Styleclasses.WINDOW
        );
    }
}
