package controls;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.Border;

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
    }

    public Label addBorder(Border border){
        super.setBorder(border);
        return this;
    }

    public Label setPadding(double padding){
        setPadding(new Insets(padding));
        return this;
    }

    public Label setPadding(
            double top
            , double right
            , double bottom
            , double left
    ){
        setPadding(new Insets(top, right, bottom, left));
        return this;
    }

    public Label setInfoGraph(Node node){
        setGraphic(node);
        setContentDisplay(ContentDisplay.RIGHT);
        return this;
    }

}
