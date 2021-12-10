package ThothGUI.ThothLite.DialogWindows;

import javafx.scene.control.ButtonBase;
import javafx.scene.control.ButtonType;
import javafx.scene.control.skin.ButtonSkin;

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
