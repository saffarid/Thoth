package controls;

import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import layout.basepane.GridPane;

public class Twin extends GridPane {

    private final String STYLE_CLASS_TWIN = "twin";
    private final String STYLE_CLASS_TWINCHILD = "twin-child";

    private Node firstNode;
    private Node secondNode;

    public Twin() {
        super();
        init();
    }

    public Twin(Node first, Node second){
        super();
        init();
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

        RowConstraints row1 = new RowConstraints();
        ColumnConstraints column1 = new ColumnConstraints(120, 120, Double.MAX_VALUE);
        ColumnConstraints column2 = new ColumnConstraints(120, 120, Double.MAX_VALUE);
        row1.setVgrow(Priority.ALWAYS);

        column1.setHgrow(Priority.SOMETIMES);
        column2.setHgrow(Priority.ALWAYS);

        column1.setFillWidth(true);
        column2.setFillWidth(true);
        getRowConstraints().add(row1);
        getColumnConstraints().addAll(
                column1
                , column2
        );
    }

    public void setFirstNode(Node left){
        add(left, 0, 0);
    }

    public void setSecondNode(Node right){
        add(right, 1, 0);
    }
}
