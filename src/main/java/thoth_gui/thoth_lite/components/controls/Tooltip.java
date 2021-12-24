package thoth_gui.thoth_lite.components.controls;

import thoth_gui.config.Config;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Tooltip {
    private static void bindFont(javafx.scene.control.Tooltip tooltip) {
        try {
            tooltip.fontProperty().bind(Config.getInstance().getFont().fontProperty());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static javafx.scene.control.Tooltip getInstance(){
        javafx.scene.control.Tooltip tooltip = new javafx.scene.control.Tooltip();
        bindFont(tooltip);
        return tooltip;
    }
    public static javafx.scene.control.Tooltip getInstance(String s){
        javafx.scene.control.Tooltip tooltip = new javafx.scene.control.Tooltip(s);
        bindFont(tooltip);
        return tooltip;
    }
}
