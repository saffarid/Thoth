package layout.basepane;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import tools.BackgroundWrapper;

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

    private void addDefaultStyle(){
        setBackground(
                new BackgroundWrapper()
                        .setColor(Color.TRANSPARENT)
                        .commit()
        );
    }

    private void init(){
        getStylesheets().addAll(
                getClass().getResource(SSHEET_SPANE).toExternalForm()
        );
        addDefaultStyle();
    }

}
