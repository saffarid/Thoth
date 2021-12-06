package window;

import javafx.scene.layout.BorderPane;
import layout.custompane.Title;
import styleconstants.Styleclasses;
import styleconstants.Stylesheets;

public abstract class Window extends BorderPane {

    protected Title title;

    public Window() {
        super();
        try {
            getStylesheets().add(
                    getClass().getResource(Stylesheets.WINDOW).toExternalForm()
            );

            getStyleClass().addAll(
                    Styleclasses.WINDOW
            );
        }catch (Exception e){
            setStyle("-fx-border-color:#707070;\n" +
                    "    -fx-border-style:solid;\n" +
                    "    -fx-border-width:3px;\n" +
                    "-fx-background-color:#343A40");
        }
    }
}
