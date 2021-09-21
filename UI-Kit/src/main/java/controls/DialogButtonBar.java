package controls;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class DialogButtonBar extends ButtonBar {

    public DialogButtonBar(
            String applyText
            , String cancelText
            , EventHandler<ActionEvent> apply
            , EventHandler<ActionEvent> cancel
    ) {
        super();

        getButtons().addAll(
                getButton(applyText, apply)
                , getButton(cancelText, cancel)
        );

    }

    private Button getButton(
            String text
            , EventHandler<ActionEvent> event
    ){
        Button btn = new Button(text);
        btn.setOnAction(event);
        return btn;
    }
}
