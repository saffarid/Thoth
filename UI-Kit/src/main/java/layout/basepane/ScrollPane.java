package layout.basepane;

import javafx.scene.Node;

public class ScrollPane extends javafx.scene.control.ScrollPane {

    public static final String STYLE_CLASS_BORDER = "border";

    public ScrollPane() {
        super();
        init();
    }

    public ScrollPane(Node node) {
        super(node);
        init();
    }

    private void init(){
        getStylesheets().add(getClass().getResource("/style/layout/panes/basepanes/scroll_pane.css").toExternalForm());
        getStyleClass().add(STYLE_CLASS_BORDER);
    }

}
