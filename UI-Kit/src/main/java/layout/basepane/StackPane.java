package layout.basepane;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import layout.BackgroundWrapper;
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

    private void init() {
        addStyle();
    }

    private void addStyle(){
        setBackground(
                new BackgroundWrapper()
                        .setColor(Color.TRANSPARENT)
                        .commit()
        );
    }
}
