package thoth_gui.thoth_lite.components.controls;

import javafx.application.Platform;
import org.json.simple.parser.ParseException;
import thoth_gui.config.Config;
import thoth_gui.thoth_styleconstants.Stylesheets;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class TextArea {

    private static void bindFont(controls.TextArea node) {
        try {
            node.fontProperty().bind(Config.getInstance().getFont().fontProperty());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private static void connectStyle(controls.TextArea node){
        CompletableFuture
                .supplyAsync(() -> Stylesheets.TEXT_FIELD.getStylesheet())
                .thenAccept(s -> {
                    Platform.runLater(() -> node.getStylesheets().add(s));
                });
    }

    public static controls.TextArea getInstance(){
        controls.TextArea res = new controls.TextArea();
        bindFont(res);
        connectStyle(res);
        return res;
    }

    public static controls.TextArea getInstance(String s){
        controls.TextArea res = getInstance();
        res.setText(s);
        return res;
    }

}
