package thoth_gui.thoth_lite.components.controls;

import javafx.scene.control.Labeled;
import org.json.simple.parser.ParseException;
import thoth_gui.config.Config;

import java.io.IOException;

public class TextField {

    private static void bindFont(controls.TextField node) {
        try {
            node.fontProperty().bind(Config.getInstance().getFont().fontProperty());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static controls.TextField getInstance(){
        controls.TextField res = new controls.TextField();
        bindFont(res);
        return res;
    }
    public static controls.TextField getInstance(String text){
        controls.TextField res = getInstance();
        res.setText(text);
        return res;
    }

}
