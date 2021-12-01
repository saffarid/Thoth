package ThothGUI.ThothLite.DialogWindows;

import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

public abstract class DialogWindow<R>
        extends Dialog<R> {

    public DialogWindow(String message) {
        super();
        DialogPane dialogPane = getDialogPane();
        dialogPane.setHeaderText("Hello");
        dialogPane.setContentText(message);
    }

    public static DialogWindow getInstance(
            DialogWindowType type,
            String message
            ){
        switch (type){
            case ALARM: return new AlarmWindow(message);
            case CONFIRM: return new ConfirmWindow(message);
            case INFO: return new InfoWindow(message);
            case WARNING: return new WarningWindow(message);
            default: return null;
        }
    }


}
