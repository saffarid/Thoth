package thoth_gui.thoth_lite.components.controls.combo_boxes;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import org.json.simple.parser.ParseException;
import thoth_gui.config.Config;
import thoth_gui.thoth_lite.components.controls.Label;
import thoth_gui.thoth_styleconstants.Stylesheets;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class ComboBox {

    private static void bindFont(controls.ComboBox node) {

    }
    private static void connectStyle(controls.ComboBox node){
        CompletableFuture
                .supplyAsync(() -> Stylesheets.COMBO_BOX.getStylesheet())
                .thenAccept(s -> {
                    Platform.runLater(() -> node.getStylesheets().add(s));
                });
        CompletableFuture
                .supplyAsync(() -> Stylesheets.LIST_VIEW.getStylesheet())
                .thenAccept(s -> {
                    Platform.runLater(() -> node.getStylesheets().add(s));
                });
    }

    public static controls.ComboBox getInstance(){
        controls.ComboBox comboBox = new controls.ComboBox();
        comboBox.setPlaceholder(Label.getInstanse("no_elements"));
        bindFont(comboBox);
        connectStyle(comboBox);
        comboBox.setMaxWidth(150);
        comboBox.setMaxWidth(500);
        return comboBox;
    }
    public static controls.ComboBox getInstance(ObservableList list){
        controls.ComboBox instance = getInstance();
        instance.setItems(list);
        return instance;
    }

}
