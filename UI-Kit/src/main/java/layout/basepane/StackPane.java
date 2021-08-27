package layout.basepane;

import javafx.scene.Node;
import styleconstants.Stylesheets;
import styleconstants.Styleclasses;

public class StackPane extends javafx.scene.layout.StackPane {

    private final String SCLASS_SPANE = "stack-pane";
    private final String SSHEET_SPANE = "/style/layout/panes/basepanes/stack_pane.css";

    public StackPane() {
        super();
        init();
    }

    public StackPane(Node... nodes) {
        super(nodes);
        init();
    }

    private void init(){
        getStylesheets().addAll(
                getClass().getResource(Stylesheets.COLOR).toExternalForm(),
                getClass().getResource(SSHEET_SPANE).toExternalForm()
        );
        getStyleClass().addAll(Styleclasses.DARK, SCLASS_SPANE);
    }
}
