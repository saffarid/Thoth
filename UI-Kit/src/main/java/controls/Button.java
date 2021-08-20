package controls;

import javafx.scene.Node;

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
        getStylesheets().add(getClass().getResource("/style/controls/button.css").toExternalForm());
    }
}
