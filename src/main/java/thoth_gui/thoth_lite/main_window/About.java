package thoth_gui.thoth_lite.main_window;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.stage.Stage;
import layout.basepane.BorderPane;
import layout.basepane.VBox;
import org.json.simple.parser.ParseException;
import thoth_gui.config.Config;
import thoth_gui.thoth_lite.components.controls.Label;
import thoth_gui.thoth_styleconstants.Stylesheets;
import window.SecondaryWindow;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class About
        extends SecondaryWindow {

    private final String RUNTIME_TEMPLATE = "Runtime version: %1s";
    private final Insets marginVBox = new Insets(5);

    private BorderPane content;

    public About(Stage stage, String title) {
        super(stage, title);
        initStyle();
        setCenter(createContent());
    }

    private Node createContent() {
        content = new BorderPane();
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        BorderPane.setMargin(vBox, marginVBox);

        vBox.getChildren().addAll(
                Label.getInstanse("Thoth v.1.0.0"),
                Label.getInstanse(String.format(RUNTIME_TEMPLATE, Runtime.version().toString()))
        );

        content.setCenter(vBox);

        return content;
    }

    @Override
    protected void initStyle() {
        try {
            getStyleClass().add(Config.getInstance().getScene().getTheme().getName().toLowerCase());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        getStylesheets().add(Stylesheets.COLORS.getStylesheet());
        getStylesheets().add(Stylesheets.LABEL.getStylesheet());
        getStylesheets().add(Stylesheets.TITLE.getStylesheet());
        getStylesheets().add(Stylesheets.WINDOW.getStylesheet());
    }
}
