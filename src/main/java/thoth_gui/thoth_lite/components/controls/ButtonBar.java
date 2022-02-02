package thoth_gui.thoth_lite.components.controls;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.paint.Color;
import tools.BorderWrapper;

public class ButtonBar {

    public static javafx.scene.control.ButtonBar getInstance() {
        javafx.scene.control.ButtonBar buttonBar = new javafx.scene.control.ButtonBar();

        buttonBar.setPadding(new Insets(2));

        return buttonBar;
    }

    public static javafx.scene.control.ButtonBar getInstance(
            EventHandler<ActionEvent> apply
            , EventHandler<ActionEvent> cancel
    ) {
        javafx.scene.control.ButtonBar buttonBar = getInstance();

        buttonBar.getButtons().addAll(
                Button.getInstance("apply", apply)
                , Button.getInstance("cancel", cancel)
        );

        return buttonBar;
    }

    public static javafx.scene.control.ButtonBar getInstance(
            controls.Button apply
            , controls.Button cancel
    ) {
        javafx.scene.control.ButtonBar buttonBar = getInstance();

        buttonBar.getButtons().addAll(
                apply
                , cancel
        );

        return buttonBar;
    }

}
