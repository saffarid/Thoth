package thoth_gui.thoth_lite.components.controls;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import org.json.simple.parser.ParseException;
import thoth_gui.config.Config;
import thoth_gui.thoth_lite.tools.Properties;
import thoth_gui.thoth_lite.tools.TextCase;

import java.io.IOException;

public class MenuButton {

    private static controls.MenuButton bindFont(controls.MenuButton node) {
        node.fontProperty().bind(Config.getInstance().getFont().fontProperty());
        return node;
    }

    public static controls.MenuButton getInstance() {
        controls.MenuButton res = new controls.MenuButton();
        return bindFont(res);
    }

    public static controls.MenuButton getInstance(EventHandler<ActionEvent> event) {
        controls.MenuButton res = getInstance();
        res.setOnAction(event);
        return res;
    }

    public static controls.MenuButton getInstance(String text) {
        controls.MenuButton res = getInstance();
        res.setText(Properties.getString(text, TextCase.NORMAL));
        return res;
    }

    public static controls.MenuButton getInstance(String text, EventHandler<ActionEvent> event) {
        controls.MenuButton res = getInstance(text);
        res.setOnAction(event);
        return res;
    }

    public static controls.MenuButton getInstance(String text, Node node) {
        controls.MenuButton res = getInstance(text);
        res.setGraphic(node);
        return res;
    }

    public static controls.MenuButton getInstance(String text, Node node, EventHandler<ActionEvent> event) {
        controls.MenuButton res = getInstance(text, node);
        res.setOnAction(event);
        return res;
    }

}
