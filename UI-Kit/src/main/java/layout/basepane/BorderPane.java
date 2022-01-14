package layout.basepane;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import tools.BackgroundWrapper;

public class BorderPane extends javafx.scene.layout.BorderPane {

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

    private void addDefaultStyle(){
        setBackground(
                new BackgroundWrapper()
                        .setColor(Color.TRANSPARENT)
                        .commit()
        );
    }

    private void init() {
        addDefaultStyle();
    }
}
