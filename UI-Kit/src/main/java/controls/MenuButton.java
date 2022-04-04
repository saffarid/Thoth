package controls;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Button;

public class MenuButton extends Button {

    private final String STYLE_CLASS_MENU_BUTTON = "navigation-menu-button";

    private String text;
    private SimpleBooleanProperty isMini;

    public MenuButton(String s) {
        super(s);
        this.text = s;
        setTooltip(new Tooltip(s));
        init();
    }

    public MenuButton(String s, Node node) {
        super(s, node);
        this.text = s;
        setTooltip(new Tooltip(s));
        init();
    }

    private void init(){
        isMini = new SimpleBooleanProperty();
        isMini.addListener((observableValue, aBoolean, t1) -> {
            if(t1){
                setText("");
            }else{
                setText(text);
            }
        });

        getStyleClass().add(STYLE_CLASS_MENU_BUTTON);
        if(getStyleClass().contains("button")){
            getStyleClass().remove("button");
        }
        if(getStyleClass().contains("text-button")){
            getStyleClass().remove("text-button");
        }

        setMaxWidth(Double.MAX_VALUE);
    }

    public boolean isIsMini() {
        return isMini.get();
    }

    public SimpleBooleanProperty isMiniProperty() {
        return isMini;
    }
}
