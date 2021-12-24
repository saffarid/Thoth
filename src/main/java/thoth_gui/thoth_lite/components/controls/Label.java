package thoth_gui.thoth_lite.components.controls;

import thoth_gui.config.Config;
import javafx.scene.Node;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Label {
    private static void bindFont(controls.Label label) {
        try {
            label.fontProperty().bind(Config.getInstance().getFont().fontProperty());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static controls.Label getInstanse(){
        controls.Label label = new controls.Label();
        bindFont(label);
        return label;
    }
    public static controls.Label getInstanse(String s){
        controls.Label label = new controls.Label(s);
        bindFont(label);
        return label;
    }
    public static controls.Label getInstanse(String s, Node node){
        controls.Label label = new controls.Label(s, node);
        bindFont(label);
        return label;
    }
}
