package thoth_gui.thoth_lite.components.controls;

import javafx.application.Platform;
import main.Main;
import thoth_gui.thoth_styleconstants.Stylesheets;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

public class DatePicker {

    private static void connectStyle(javafx.scene.control.DatePicker node){
        CompletableFuture
                .supplyAsync(() -> Stylesheets.DATE_PICKER.getStylesheet())
                .thenAccept(s -> {
                    Platform.runLater(() -> node.getStylesheets().add(s));
                });
    }

    public static javafx.scene.control.DatePicker getInstance(){
        javafx.scene.control.DatePicker instance = new javafx.scene.control.DatePicker(LocalDate.now());
        connectStyle(instance);
        return instance;
    }
    public static javafx.scene.control.DatePicker getInstance(LocalDate date){
        javafx.scene.control.DatePicker instance = new javafx.scene.control.DatePicker(date);
        connectStyle(instance);
        return instance;
    }

}
