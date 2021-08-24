package layout.basepane;

import javafx.scene.Node;

public class VBox extends javafx.scene.layout.VBox {

    private final String STYLE_CLASS_VBOX = "vbox";

    public VBox() {
        super();
        init();
    }

    public VBox(double v) {
        super(v);
        init();
    }

    public VBox(Node... nodes) {
        super(nodes);
        init();
    }

    public VBox(double v, Node... nodes) {
        super(v, nodes);
        init();
    }

    private void init(){
        getStylesheets().add(getClass().getResource("/style/layout/panes/basepanes/vbox.css").toExternalForm());
        getStyleClass().addAll(STYLE_CLASS_VBOX);
    }
}
