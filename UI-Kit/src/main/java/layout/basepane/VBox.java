package layout.basepane;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import layout.BackgroundWrapper;
import styleconstants.Stylesheets;
import styleconstants.Styleclasses;

public class VBox extends javafx.scene.layout.VBox {

    private static final String VBOX = "vbox";
    private static final String SSHEET_VBOX = "/style/layout/panes/basepanes/vbox.css";

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
