package layout.basepane;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import layout.BackgroundWrapper;
import styleconstants.Stylesheets;
import styleconstants.Styleclasses;

public class HBox extends javafx.scene.layout.HBox {

    public static final String SCLASS_HBOX = "hbox";
    public static final String SSHEET_HBOX = "/style/layout/panes/basepanes/hbox.css";

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
