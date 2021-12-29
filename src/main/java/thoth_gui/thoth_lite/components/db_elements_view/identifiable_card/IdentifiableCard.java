package thoth_gui.thoth_lite.components.db_elements_view.identifiable_card;

import thoth_core.thoth_lite.db_data.db_data_element.implement.Currency;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Identifiable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Listed;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_core.thoth_lite.ThothLite;
import thoth_gui.Apply;
import thoth_gui.Cancel;
import controls.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;
import thoth_gui.thoth_lite.components.controls.ButtonBar;
import window.Closeable;

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

    private Closeable parentClose;

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
                parentClose.close();
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
        parentClose.close();
    }

    private javafx.scene.control.ButtonBar createButtonBar(){
        apply  = getButton( ButtonText.APPLY , event -> apply()  );
        cancel = getButton( ButtonText.CANCEL, event -> cancel() );

        javafx.scene.control.ButtonBar buttonBar = ButtonBar.getInstance(
                apply
                , cancel
        );

        setMargin(buttonBar, new Insets(2));

        return buttonBar;
    }

    protected abstract Node createContent();

    protected abstract Identifiable identifiableInstance();

    private Button getButton(
            ButtonText text
            , EventHandler<ActionEvent> event
    ){
        Button res = thoth_gui.thoth_lite.components.controls.Button.getInstance(text.text, event);
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

    public void setParentClose(Closeable parentClose) {
        this.parentClose = parentClose;
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
