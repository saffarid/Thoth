package controls;

import javafx.scene.Node;
import styleconstants.STYLESHEETS;

public class Button extends javafx.scene.control.Button {

    public Button() {
        super();
        init();
    }

    public Button(String s) {
        super(s);
        init();
    }

    public Button(String s, Node node) {
        super(s, node);
        init();
    }

    public Button(Node node){
        super();
        setGraphic(node);
        init();
    }

    private void init(){
        getStylesheets().add(getClass().getResource(STYLESHEETS.BUTTON).toExternalForm());
    }
}
