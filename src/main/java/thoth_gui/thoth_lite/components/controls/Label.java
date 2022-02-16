package thoth_gui.thoth_lite.components.controls;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import thoth_gui.config.Config;
import javafx.scene.Node;
import org.json.simple.parser.ParseException;
import thoth_gui.thoth_lite.tools.Properties;
import thoth_gui.thoth_lite.tools.TextCase;
import thoth_gui.thoth_styleconstants.Stylesheets;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

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
    private static void connectStyle(controls.Label node){
        CompletableFuture
                .supplyAsync(() -> Stylesheets.LABEL.getStylesheet())
                .thenAccept(s -> {
                    Platform.runLater(() -> node.getStylesheets().add(s));
                });
    }

    public static controls.Label getInstanse(){
        controls.Label label = new controls.Label();
        label.setTextFill(Color.WHITE);
        bindFont(label);
        connectStyle(label);
        return label;
    }
    public static controls.Label getInstanse(String s){
        return getInstanse(s, TextCase.NORMAL);
    }
    public static controls.Label getInstanse(String s, TextCase textCase){
        controls.Label label = getInstanse();
        label.setText(Properties.getString(s, textCase));
        return label;
    }
    public static controls.Label getInstanse(String s, Node node){
        controls.Label label = getInstanse(s);
        label.setGraphic(node);
        return label;
    }
    public static controls.Label getInstanse(String s, Node node, TextCase textCase){
        controls.Label label = getInstanse(s, textCase);
        label.setGraphic(node);
        return label;
    }
}
