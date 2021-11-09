package ThothGUI.ThothLite.Components.DBElementsView.IdentifiableCard;

import ThothCore.ThothLite.DBData.DBDataElement.Implements.Currency;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;
import ThothGUI.Apply;
import ThothGUI.Cancel;
import controls.Button;
import controls.ListCell;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.BorderPane;

import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class IdentifiableCard
        extends BorderPane
        implements Apply, Cancel {

    protected static final Logger LOG;

    private enum ButtonText{
        APPLY("apply"),
        CANCEL("cancel");
        private String text;
        ButtonText(String text) {
            this.text = text;
        }
    }

    protected Identifiable identifiable;

    static {
        LOG = Logger.getLogger(IdentifiableCard.class.getName());
    }

    protected IdentifiableCard(Identifiable identifiable) {
        super();
        this.identifiable = identifiable;

        setBottom(createButtonBar());
    }

    @Override
    public void apply() {
        LOG.log(Level.INFO, identifiable.toString());
    }

    @Override
    public void cancel() {

    }

    private ButtonBar createButtonBar(){
        ButtonBar buttonBar = new ButtonBar();
        buttonBar.setPadding(new Insets(2));
        buttonBar.getButtons().addAll(
                getButton(ButtonText.APPLY, event -> apply())
                , getButton(ButtonText.CANCEL, event -> cancel())
        );
        return buttonBar;
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

    public static IdentifiableCard getInstance(Identifiable identifiable){
        if (identifiable instanceof Storagable){
            return new StoragableCard(identifiable);
        }
        return null;
    }

    protected class ComboBoxListedCell extends ListCell<Listed>{
        @Override
        protected void updateItem(Listed listed, boolean b) {
            if(listed != null){
                super.updateItem(listed, b);
                setText(listed.getValue());
            }
        }
    }

    protected class ComboBoxCurrencyCell extends ListCell<Currency>{
        @Override
        protected void updateItem(Currency currency, boolean b) {
            if(currency != null){
                super.updateItem(currency, b);
                setText(currency.getCurrency());
            }
        }
    }

}
