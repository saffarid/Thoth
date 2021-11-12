package layout.basepane;

import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Paint;
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
        try {
            getStyleClass().addAll(SCLASS_BPANE, Styleclasses.DARK);
            getStylesheets().addAll(
                    getClass().getResource(Stylesheets.COLOR).toExternalForm(),
                    getClass().getResource(SSHEET_BPANE).toExternalForm()
            );
//        setBackground(new Background(new BackgroundFill(Paint.valueOf("#343A40"), null, null)));
        }catch (NullPointerException e){
            
        }
    }
}
