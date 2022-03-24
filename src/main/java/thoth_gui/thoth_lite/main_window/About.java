package thoth_gui.thoth_lite.main_window;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.stage.Stage;
import layout.basepane.BorderPane;
import layout.basepane.VBox;
import thoth_gui.config.Config;
import thoth_gui.thoth_lite.components.controls.Label;
import thoth_gui.thoth_styleconstants.Stylesheets;
import window.SecondaryWindow;

public class About
        extends SecondaryWindow {

//    <a href='https://ru.freepik.com/photos/background'>Background фото создан(а) kjpargeter - ru.freepik.com</a>

    private final String RUNTIME_TEMPLATE = "Runtime version: %1s";
    private final String VIRTUAL_MACHINE_TEMPLATE = "VM: %1s";
    private final String RUNTIME_FX_TEMPLATE = "Runtime FX: %1s";
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
                Label.getInstanse( String.format(RUNTIME_TEMPLATE, System.getProperty("java.runtime.version")) ),
                Label.getInstanse( String.format(VIRTUAL_MACHINE_TEMPLATE, System.getProperty("java.vm.name")) ),
                Label.getInstanse( String.format(RUNTIME_FX_TEMPLATE, System.getProperty("javafx.runtime.version")) )
        );

        content.setCenter(vBox);

        return content;
    }

    @Override
    protected void initStyle() {

        getStyleClass().add(Config.getInstance().getScene().getTheme().getName().toLowerCase());

        getStylesheets().add(Stylesheets.COLORS.getStylesheet());
        getStylesheets().add(Stylesheets.LABEL.getStylesheet());
        getStylesheets().add(Stylesheets.TITLE.getStylesheet());
        getStylesheets().add(Stylesheets.WINDOW.getStylesheet());
    }
}
