package ThothGUI.ThothLite.DialogWindows;

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
