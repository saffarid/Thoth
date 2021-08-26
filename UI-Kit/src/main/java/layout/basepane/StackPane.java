package layout.basepane;

import javafx.scene.Node;
import styleconstants.STYLESHEETS;
import styleconstants.Styleclasses;

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
        getStylesheets().add(getClass().getResource(STYLESHEETS.COLOR).toExternalForm());
        getStyleClass().addAll(Styleclasses.DARK);
    }
}
