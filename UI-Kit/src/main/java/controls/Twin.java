package controls;

import controls.Label;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.layout.FlowPane;

public class Twin extends FlowPane {

    private final String STYLE_CLASS_TWIN = "twin";
    private final String STYLE_CLASS_TWINCHILD = "twin-child";

    public Twin() {
        super();
        init();
    }

    public Twin(Node left, Node right){
        super();
        init();
        setLeft(left);
        setRight(right);

        getChildren().addListener((ListChangeListener<? super Node>) change -> {
            if(change.wasAdded()){
                change.getAddedSubList().stream().forEach(node -> node.getStyleClass().add(STYLE_CLASS_TWINCHILD));
            }
        });
    }

    public Node getLeft(){
        return getChildren().get(0);
    }

    public Node getRight(){
        return getChildren().get(1);
    }

    private void init(){
        getStylesheets().add(getClass().getResource("/style/controls/twin.css").toExternalForm());
        getStyleClass().add(STYLE_CLASS_TWIN);
    }

    public void setLeft(Node left){
        if(getChildren().size() == 0){
            getChildren().add(0, left);
        }else{
            getChildren().remove(0);
            getChildren().add(0, left);
        }
    }

    public void setRight(Node right){
        if(getChildren().size() == 0){
            getChildren().add(0, new Label());
            getChildren().add(1, right);
        }else{
            if(getChildren().size() != 1){
                getChildren().remove(1);
            }
            getChildren().add(1, right);
        }
    }
}
