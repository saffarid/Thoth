package thoth_gui.thoth_lite.dialog_windows;

import javafx.scene.control.ButtonType;

public class ConfirmWindow
        extends DialogWindow{

    protected ConfirmWindow(String message) {
        super(message);

        getDialogPane().getButtonTypes().addAll(
                ButtonType.YES,
                ButtonType.NO
        );
    }

}
