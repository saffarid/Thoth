package controls;

import javafx.scene.Node;
import javafx.scene.control.Tooltip;

public class Button extends javafx.scene.control.Button {

    private final static String STYLE_CLASS_TEXT_BTN = "text-button";
    private final static String STYLE_CLASS_IMG_BTN = "img-button";

    public Button(String s) {
        super(s);
        init(true);
    }

    public Button(String s, Node node) {
        super(s, node);
        init(true);
    }

    public Button(Node node) {
        super();
        setGraphic(node);
        init(false);
    }

    private void init(boolean hasText) {
        if (hasText) {
            getStyleClass().add(STYLE_CLASS_TEXT_BTN);
        } else {
            getStyleClass().add(STYLE_CLASS_IMG_BTN);
        }
    }

    public Button setTool(Tooltip t){
        setTooltip(t);
        return this;
    }
}
