package controls;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.Collection;

public class MenuButton extends Button {

    private final String STYLE_CLASS_MENU_BUTTON = "menu-button";

    private String text;

    private SimpleBooleanProperty isMini;

    public MenuButton() {
        super();
        init();
    }

    public MenuButton(String s) {
        super(s);
        this.text = s;
        init();
    }

    public MenuButton(String s, Node node) {
        super(s, node);
        this.text = s;
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
        setMaxWidth(Double.MAX_VALUE);
        getStylesheets().add(getClass().getResource("/style/controls/menu_button.css").toExternalForm());

    }

    public boolean isIsMini() {
        return isMini.get();
    }

    public SimpleBooleanProperty isMiniProperty() {
        return isMini;
    }
}
