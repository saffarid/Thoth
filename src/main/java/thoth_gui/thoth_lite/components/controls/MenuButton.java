package thoth_gui.thoth_lite.components.controls;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import org.json.simple.parser.ParseException;
import thoth_gui.config.Config;

import java.io.IOException;

public class MenuButton {

    private static controls.MenuButton bindFont(controls.MenuButton node){
        try {
            node.fontProperty().bind(Config.getInstance().getFont().fontProperty());
            node.getTooltip().fontProperty().bind(Config.getInstance().getFont().fontProperty());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return node;
    }

    public static controls.MenuButton getInstance(){
        controls.MenuButton res = new controls.MenuButton();
        return bindFont(res);
    }
    public static controls.MenuButton getInstance(EventHandler<ActionEvent> event){
        controls.MenuButton res = new controls.MenuButton();
        res.setOnAction(event);
        return bindFont(res);
    }

    public static controls.MenuButton getInstance(String text){
        controls.MenuButton res = new controls.MenuButton(text);
        return bindFont(res);
    }
    public static controls.MenuButton getInstance(String text, EventHandler<ActionEvent> event){
        controls.MenuButton res = new controls.MenuButton(text);
        res.setOnAction(event);
        return bindFont(res);
    }

    public static controls.MenuButton getInstance(String text, Node node){
        controls.MenuButton res = new controls.MenuButton(text, node);
        return bindFont(res);
    }
    public static controls.MenuButton getInstance(String text, Node node, EventHandler<ActionEvent> event){
        controls.MenuButton res = new controls.MenuButton(text, node);
        res.setOnAction(event);
        return bindFont(res);
    }

}
