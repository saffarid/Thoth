package thoth_gui.thoth_lite.components.controls;

import javafx.application.Platform;
import thoth_gui.thoth_styleconstants.Stylesheets;

import java.util.concurrent.CompletableFuture;

public class ListView {

    private static void connectStyle(controls.ListView node){
        CompletableFuture
                .supplyAsync(() -> new String[]{Stylesheets.LIST_VIEW.getStylesheet(), Stylesheets.SCROLL_BAR.getStylesheet()})
                .thenAccept(s -> {
                    Platform.runLater(() -> {
                        for(String s1: s) {
                            node.getStylesheets().add(s1);
                        }
                    });
                });
    }

    public static controls.ListView getInstance(){
        controls.ListView res = new controls.ListView();
        connectStyle(res);
        res.setPlaceholder(Label.getInstanse("no_elements"));
        return res;
    }

}
