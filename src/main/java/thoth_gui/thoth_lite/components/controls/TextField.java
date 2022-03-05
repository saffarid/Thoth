package thoth_gui.thoth_lite.components.controls;

import org.json.simple.parser.ParseException;
import javafx.application.Platform;
import thoth_gui.config.Config;
import thoth_gui.thoth_styleconstants.Stylesheets;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class TextField {
    private static void bindFont(controls.TextField node) {
        try {
            node.fontProperty().bind(Config.getInstance().getFont().fontProperty());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void connectStyle(controls.TextField node){
        CompletableFuture
                .supplyAsync(() -> Stylesheets.TEXT_FIELD.getStylesheet())
                .thenAccept(s -> {
                    Platform.runLater(() -> node.getStylesheets().add(s));
                });
    }

    public static controls.TextField getInstance(){
        controls.TextField res = new controls.TextField();
        bindFont(res);
        connectStyle(res);
        return res;
    }
    public static controls.TextField getInstance(String text){
        controls.TextField res = getInstance();
        res.setText(text);
        connectStyle(res);
        return res;
    }
}
