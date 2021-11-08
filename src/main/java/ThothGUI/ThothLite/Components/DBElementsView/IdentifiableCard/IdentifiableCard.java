package ThothGUI.ThothLite.Components.DBElementsView.IdentifiableCard;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;
import ThothGUI.Apply;
import ThothGUI.Cancel;
import controls.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBar;
import layout.basepane.BorderPane;

public abstract class IdentifiableCard
        extends BorderPane
        implements Apply, Cancel {

    private enum ButtonText{
        APPLY("apply"),
        CANCEL("cancel");
        private String text;
        ButtonText(String text) {
            this.text = text;
        }
    }

    protected Identifiable identifiable;

    protected IdentifiableCard(Identifiable identifiable) {
        super();
        this.identifiable = identifiable;

        setBottom(createButtonBar());
    }

    private Button getButton(
            ButtonText text
            , EventHandler<ActionEvent> event
    ){
        Button res = new Button(text.text);
        res.setId(text.text);
        res.setOnAction(event);
        return res;
    }

    private ButtonBar createButtonBar(){
        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(
                getButton(ButtonText.APPLY, event -> apply())
                , getButton(ButtonText.CANCEL, event -> cancel())
        );
        return buttonBar;
    }

    public static IdentifiableCard getInstance(Identifiable identifiable){
        if (identifiable instanceof Storagable){
            return new StoragableCard(identifiable);
        }
        return null;
    }

}
