package layout.basepane;

import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import layout.BackgroundWrapper;
import styleconstants.Stylesheets;
import styleconstants.Styleclasses;

public class BorderPane extends javafx.scene.layout.BorderPane {

    public static final String SCLASS_BPANE = "border-pane";
    public static final String SSHEET_BPANE = "/style/layout/panes/basepanes/border_pane.css";

    public BorderPane() {
        super();
        init();
    }

    public BorderPane(Node node) {
        super(node);
        init();
    }

    public BorderPane(Node node, Node node1, Node node2, Node node3, Node node4) {
        super(node, node1, node2, node3, node4);
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
