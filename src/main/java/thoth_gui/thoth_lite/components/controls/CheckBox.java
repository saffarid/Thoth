package thoth_gui.thoth_lite.components.controls;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import org.json.simple.parser.ParseException;
import thoth_gui.config.Config;
import thoth_gui.thoth_styleconstants.Stylesheets;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class CheckBox {

    private static javafx.scene.control.CheckBox bindFont(javafx.scene.control.CheckBox node) {
        SimpleObjectProperty<Font> fontProperty = Config.getInstance().getFont().fontProperty();
        node.fontProperty().bind(fontProperty);
        node.tooltipProperty().addListener((observableValue, tooltip, t1) -> node.getTooltip().fontProperty().bind(fontProperty));
        return node;
    }

    private static void connectStyle(javafx.scene.control.CheckBox node) {
        CompletableFuture
                .supplyAsync(() -> Stylesheets.CHECK_BOX.getStylesheet())
                .thenAccept(s -> {
                    Platform.runLater(() -> node.getStylesheets().add(s));
                });
    }

    public static javafx.scene.control.CheckBox getInstance() {
        javafx.scene.control.CheckBox res = new javafx.scene.control.CheckBox();
        bindFont(res);
        connectStyle(res);
        return res;
    }

    public static javafx.scene.control.CheckBox getInstance(String s) {
        javafx.scene.control.CheckBox res = getInstance();
        res.setText(s);
        res.setTooltip(new Tooltip(s));
        connectStyle(res);
        return res;
    }

}
