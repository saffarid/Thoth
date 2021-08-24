package layout.basepane;

import javafx.scene.Node;

public class HBox extends javafx.scene.layout.HBox {

    private final String STYLE_CLASS_HBOX = "hbox";

    public HBox() {
        super();
        init();
    }

    public HBox(double v) {
        super(v);
        init();
    }

    public HBox(Node... nodes) {
        super(nodes);
        init();
    }

    public HBox(double v, Node... nodes) {
        super(v, nodes);
        init();
    }

    private void init(){
        getStylesheets().add(getClass().getResource("/style/layout/panes/basepanes/hbox.css").toExternalForm());
        getStyleClass().addAll(STYLE_CLASS_HBOX);
    }
}
