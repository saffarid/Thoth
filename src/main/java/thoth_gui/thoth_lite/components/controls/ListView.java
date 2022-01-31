package thoth_gui.thoth_lite.components.controls;

import javafx.application.Platform;
import thoth_gui.thoth_styleconstants.Stylesheets;

import java.util.concurrent.CompletableFuture;

public class ListView {

    private static void connectStyle(controls.ListView node){
        CompletableFuture
                .supplyAsync(() -> Stylesheets.LIST_VIEW.getStylesheet())
                .thenAccept(s -> {
                    Platform.runLater(() -> node.getStylesheets().add(s));
                });
    }

    public static controls.ListView getInstance(){
        controls.ListView res = new controls.ListView();
        connectStyle(res);
        return res;
    }

}
