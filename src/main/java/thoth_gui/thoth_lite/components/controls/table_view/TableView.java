package thoth_gui.thoth_lite.components.controls.table_view;

import javafx.application.Platform;
import thoth_gui.thoth_styleconstants.Stylesheets;

import java.util.concurrent.CompletableFuture;

public class TableView{

    private static void connectStyle(controls.table_view.TableView node){
        CompletableFuture
                .supplyAsync(() -> Stylesheets.TABLE_VIEW.getStylesheet())
                .thenAccept(s -> {
                    Platform.runLater(() -> node.getStylesheets().add(s));
                });
    }

    public static controls.table_view.TableView getInstance(){
        controls.table_view.TableView res = new controls.table_view.TableView<>();
        connectStyle(res);
        return res;
    }

}
