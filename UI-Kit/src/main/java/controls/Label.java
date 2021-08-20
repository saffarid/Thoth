package controls;

import javafx.scene.Node;

public class Label extends javafx.scene.control.Label {

    public Label() {
        super();
        init();
    }

    public Label(String s) {
        super(s);
        init();
    }

    public Label(String s, Node node) {
        super(s, node);
        init();
    }

    private void init(){
        getStylesheets().add(getClass().getResource("/style/controls/label.css").toExternalForm());
    }

}
