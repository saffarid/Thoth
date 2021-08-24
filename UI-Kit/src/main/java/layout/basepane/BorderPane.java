package layout.basepane;

import javafx.scene.Node;

public class BorderPane extends javafx.scene.layout.BorderPane {

    private final String STYLE_CLASS_BPANE = "border-pane";

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

    private void init(){
        getStylesheets().add(getClass().getResource("/style/layout/panes/basepanes/border_pane.css").toExternalForm());
        getStyleClass().addAll(STYLE_CLASS_BPANE);
    }
}
