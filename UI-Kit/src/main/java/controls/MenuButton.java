package controls;

import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.Collection;

public class MenuButton extends Button {

    private final String STYLE_CLASS_MENU_BUTTON = "menu-button";

    public MenuButton() {
        super();
        init();
    }

    public MenuButton(String s) {
        super(s);
        init();
    }

    public MenuButton(String s, Node node) {
        super(s, node);
        init();
    }

    private String getLinearGradient(String color, int midGrad, int gradRange){
        if(midGrad <= gradRange){
            return "linear-gradient(to right, " +
                    color + " 0%,  " +
                    color + " " + midGrad + "%,  " +
                    color + " " + (midGrad + gradRange) + "%, " +
                    "#343A40)";
        }
        if((midGrad + gradRange) >= 100){
            return "linear-gradient(to right, " +
                    "#343A40,  " +
                    color + " " + (midGrad - gradRange) + "%, " +
                    color + " " + midGrad + "%,  " +
                    color + ")";
        }
        return "linear-gradient(to right, " +
                "#343A40,  " +
                color + " " + (midGrad - gradRange) + "%, " +
                color + " " + midGrad + "%,  " +
                color + " " + (midGrad + gradRange) + "%, " +
                "#343A40)";
    }

    private void init(){
        getStyleClass().add(STYLE_CLASS_MENU_BUTTON);
        setMaxWidth(Double.MAX_VALUE);
        getStylesheets().add(getClass().getResource("/style/controls/menu_button.css").toExternalForm());

        setOnMouseMoved(mouseEvent -> {
            int perX = (int) ((mouseEvent.getSceneX() / getWidth()) * 100);
            StringBuilder style = new StringBuilder("");

            style.append("-fx-background-color: " + getLinearGradient("#23272B", perX, 25) + ";");
            style.append("-fx-border-color: " + getLinearGradient("grey", perX, 15) + ";");

            setStyle(style.toString());
        });
        setOnMouseExited(mouseEvent -> setStyle(""));

    }
}
