package controls;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import layout.basepane.GridPane;

public class Twin extends GridPane {

    private final static String styleclass = "twin";
    private final static int LEFT = 0;
    private final static int RIGHT = 1;

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
        setHgap(5);
        getStyleClass().add(styleclass);
        addRow(Priority.NEVER)
                .addColumn(Priority.ALWAYS, HPos.LEFT)
                .addColumn(Priority.ALWAYS, HPos.LEFT);
    }

    public Twin setFirstNode(Node left){
        add(left, 0, 0);
        return this;
    }

    public Twin setMinLeftWidth(double w){
        getColumnConstraints().get(LEFT).setMinWidth(w);
        return this;
    }
    public Twin setPrefLeftWidth(double w){
        getColumnConstraints().get(LEFT).setPrefWidth(w);
        return this;
    }
    public Twin setMaxLeftWidth(double w){
        getColumnConstraints().get(LEFT).setMaxWidth(w);
        return this;
    }
    public Twin setPriorityLeft(Priority p){
        getColumnConstraints().get(LEFT).setHgrow(p);
        return this;
    }
    public Twin setHPosLeft(HPos pos){
        getColumnConstraints().get(LEFT).setHalignment(pos);
        return this;
    }
    public Twin setFillWidthLeft(boolean fill){
        getColumnConstraints().get(LEFT).setFillWidth(fill);
        return this;
    }

    public Twin setMinRightWidth(double w){
        getColumnConstraints().get(RIGHT).setMinWidth(w);
        return this;
    }
    public Twin setPrefRightWidth(double w){
        getColumnConstraints().get(RIGHT).setPrefWidth(w);
        return this;
    }
    public Twin setMaxRightWidth(double w){
        getColumnConstraints().get(RIGHT).setMaxWidth(w);
        return this;
    }
    public Twin setPriorityRight(Priority p){
        getColumnConstraints().get(RIGHT).setHgrow(p);
        return this;
    }
    public Twin setHPosRight(HPos pos){
        getColumnConstraints().get(RIGHT).setHalignment(pos);
        return this;
    }
    public Twin setFillWidthRight(boolean fill){
        getColumnConstraints().get(RIGHT).setFillWidth(fill);
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
