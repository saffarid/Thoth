package ThothGUI.ThothLite.Components.Controls;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

/**
 * Класс для централизованного создания объекта кнопки
 * */
public class Button {

    public static controls.Button getInstance(String s){
        return new controls.Button(s);
    }

    public static controls.Button getInstance(String s, EventHandler<ActionEvent> event){
        controls.Button instance = getInstance(s);
        instance.setOnAction(event);
        return instance;
    }

    public static controls.Button getInstance(Node node){
        return new controls.Button(node);
    }

    public static controls.Button getInstance(Node node, EventHandler<ActionEvent> event){
        controls.Button instance = getInstance(node);
        instance.setOnAction(event);
        return instance;
    }

    public static controls.Button getInstance(String s, Node node){
        return new controls.Button(s, node);
    }

    public static controls.Button getInstance(String s, Node node, EventHandler<ActionEvent> event){
        controls.Button instance = getInstance(s, node);
        instance.setOnAction(event);
        return instance;
    }

}
