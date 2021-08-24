package layout.basepane;

import javafx.scene.Node;
import styleconstants.STYLESHEETS;

public class StackPane extends javafx.scene.layout.StackPane {


    public StackPane() {
        super();
        init();
    }

    public StackPane(Node... nodes) {
        super(nodes);
        init();
    }

    private void init(){
        getStylesheets().add(getClass().getResource(STYLESHEETS.BACKGROUND).toExternalForm());
    }
}
