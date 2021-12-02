package ThothGUI.ThothLite.Components.DBElementsView.IdentifiableCard;

import ThothCore.ThothLite.DBData.DBDataElement.Implements.Currency;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothCore.ThothLite.Exceptions.NotContainsException;
import ThothCore.ThothLite.ThothLite;
import ThothGUI.Apply;
import ThothGUI.Cancel;
import controls.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class IdentifiableCard
        extends BorderPane
        implements Apply, Cancel {

    protected static final Logger LOG;

    private EventHandler<ActionEvent> closeEvent;

    private enum ButtonText{
        APPLY("apply"),
        CANCEL("cancel");
        private String text;
        ButtonText(String text) {
            this.text = text;
        }
    }

    protected Button apply;
    protected Button cancel;

    protected boolean identifiableIsNew;
    protected Identifiable identifiable;
    protected AvaliableTables table;

    static {
        LOG = Logger.getLogger(IdentifiableCard.class.getName());
    }

    protected IdentifiableCard(
            Identifiable identifiable
            , AvaliableTables table
    ) {
        super();
        if(identifiable != null) {
            this.identifiable = identifiable;
            identifiableIsNew = false;
        }else{
            this.identifiable = identifiableInstance();
            identifiableIsNew = true;
        }
        this.table = table;

        setPadding(new Insets(2));

        setBottom(createButtonBar());
        setCenter(createContent());
    }

    @Override
    public void apply() {
        LOG.log(Level.INFO, identifiable.toString());
        if(identifiable != null){
            updateIdentifiable();
            List<Identifiable> list = new LinkedList<>();
            list.add(identifiable);
            try {
                if (identifiableIsNew) {
                    ThothLite.getInstance().insertToTable(table, list);
                }else{
                    ThothLite.getInstance().updateInTable(table, list);
                }
            } catch (NotContainsException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void cancel() {

    }

    private ButtonBar createButtonBar(){
        ButtonBar buttonBar = new ButtonBar();
        buttonBar.setPadding(new Insets(2));
        apply = getButton(ButtonText.APPLY, event -> apply());
        cancel = getButton(ButtonText.CANCEL, event -> cancel());
        buttonBar.getButtons().addAll(
                apply
                , cancel
        );

        buttonBar.setStyle("" +
                "-fx-border-width: 1px 0 0 0;" +
                "-fx-border-color:grey;" +
                "-fx-border-style:solid;" +
                "");

        return buttonBar;
    }

    protected abstract Node createContent();

    protected abstract Identifiable identifiableInstance();

    private Button getButton(
            ButtonText text
            , EventHandler<ActionEvent> event
    ){
        Button res = ThothGUI.ThothLite.Components.Controls.Button.getInstance(text.text, event);
        res.setId(text.text);
        return res;
    }

    public static IdentifiableCard getInstance(
            AvaliableTables table
            , Identifiable identifiable
    ){
        switch (table){
            case STORAGABLE:{
                return new StoragableCard(identifiable, table);
            }
            case PURCHASABLE:{
                return new PurchasableCard(identifiable, table);
            }
        }
        return null;
    }

    protected abstract void updateIdentifiable();

    protected class ComboBoxListedCell extends ListCell<Listed> {
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
