package layout.basepane;

import javafx.scene.Node;
import styleconstants.Stylesheets;
import styleconstants.Styleclasses;

public class ScrollPane extends javafx.scene.control.ScrollPane {

    private static final String SSHEET_SPANE = "/style/layout/panes/basepanes/scroll_pane.css";

    public ScrollPane() {
        super();
        init();
    }

    public ScrollPane(Node node) {
        super(node);
        init();
    }

    private void init(){
        getStylesheets().addAll(
                getClass().getResource(Stylesheets.COLOR).toExternalForm(),
                getClass().getResource(SSHEET_SPANE).toExternalForm()
        );
        getStyleClass().addAll(Styleclasses.BORDER, Styleclasses.DARK);
    }

}
