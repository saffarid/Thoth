package thoth_gui.thoth_lite.components.controls;

import thoth_gui.config.Config;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Класс для централизованного создания объекта кнопки
 * */
public class Button {

    private static void bindFont(controls.Button button) {
        try {
            button.fontProperty().bind(Config.getInstance().getFont().fontProperty());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static controls.Button getInstance(String s){
        controls.Button button = new controls.Button(s);
        bindFont(button);
        return button;
    }

    public static controls.Button getInstance(String s, EventHandler<ActionEvent> event){
        controls.Button instance = getInstance(s);
        instance.setOnAction(event);
        return instance;
    }

    public static controls.Button getInstance(Node node){
        controls.Button button = new controls.Button(node);
        bindFont(button);
        return button;
    }

    public static controls.Button getInstance(Node node, EventHandler<ActionEvent> event){
        controls.Button instance = getInstance(node);
        instance.setOnAction(event);
        return instance;
    }

    public static controls.Button getInstance(String s, Node node){
        controls.Button button = new controls.Button(s, node);
        bindFont(button);
        return button;
    }

    public static controls.Button getInstance(String s, Node node, EventHandler<ActionEvent> event){
        controls.Button instance = getInstance(s, node);
        instance.setOnAction(event);
        return instance;
    }

}
