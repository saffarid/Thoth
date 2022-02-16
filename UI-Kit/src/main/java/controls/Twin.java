package controls;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import layout.basepane.GridPane;

public class Twin extends GridPane {

    private Node firstNode;
    private Node secondNode;

    public Twin() {
        super();
        init();
    }

    public Twin(Node first, Node second){
        this();
        this.firstNode = first;
        this.secondNode = second;
        setFirstNode(this.firstNode);
        setSecondNode(this.secondNode);
    }

    public Node getFirstNode(){
        return firstNode;
    }

    public Node getSecondNode(){
        return secondNode;
    }

    private void init(){
        addRow(Priority.NEVER)
                .addColumn(Priority.ALWAYS, HPos.LEFT)
                .addColumn(Priority.ALWAYS, HPos.LEFT);
    }

    public Twin setFirstNode(Node left){
        add(left, 0, 0);
        return this;
    }

    public Twin setMinLeftWidth(double w){
        getColumnConstraints().get(0).setMinWidth(w);
        return this;
    }

    public Twin setPrefLeftWidth(double w){
        getColumnConstraints().get(0).setPrefWidth(w);
        return this;
    }

    public Twin setMaxLeftWidth(double w){
        getColumnConstraints().get(0).setMaxWidth(w);
        return this;
    }

    public Twin setMinRightWidth(double w){
        getColumnConstraints().get(1).setMinWidth(w);
        return this;
    }

    public Twin setPrefRightWidth(double w){
        getColumnConstraints().get(1).setPrefWidth(w);
        return this;
    }

    public Twin setMaxRightWidth(double w){
        getColumnConstraints().get(1).setMaxWidth(w);
        return this;
    }

    public Twin setPadding(double padding){
        setPadding(new Insets(padding));
        return this;
    }

    public Twin setPadding(
            double top
            , double right
            , double bottom
            , double left
    ){
        setPadding(new Insets(top, right, bottom, left));
        return this;
    }

    public Twin setSecondNode(Node right){
        add(right, 1, 0);
        return this;
    }
}
