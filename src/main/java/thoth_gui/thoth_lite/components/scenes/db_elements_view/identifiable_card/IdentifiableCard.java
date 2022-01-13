package thoth_gui.thoth_lite.components.scenes.db_elements_view.identifiable_card;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Pane;
import layout.basepane.BorderPane;
import thoth_core.thoth_lite.db_data.db_data_element.implement.Currency;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Identifiable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Typable;
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
import thoth_gui.thoth_lite.components.controls.ButtonBar;
import thoth_gui.thoth_lite.components.scenes.ThothScene;
import window.Closeable;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class IdentifiableCard
        implements Apply
        , Cancel
        , ThothScene
{

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

    private Closeable closeable;

    private SimpleObjectProperty<Node> tools;
    private SimpleObjectProperty<Node> content;

    protected BorderPane contentNode;
    protected BorderPane toolsNode;

    static {
        LOG = Logger.getLogger(IdentifiableCard.class.getName());
    }

    protected IdentifiableCard(
            Identifiable identifiable
            , AvaliableTables table
    ) {

        if(identifiable != null) {
            this.identifiable = identifiable;
            identifiableIsNew = false;
        }else{
            this.identifiable = identifiableInstance();
            identifiableIsNew = true;
        }
        this.table = table;

        content = new SimpleObjectProperty<>(createContent());
        tools = new SimpleObjectProperty<>(new Pane());
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
                closeable.close();
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
        closeable.close();
    }

    private javafx.scene.control.ButtonBar createButtonBar(){
        apply  = getButton( ButtonText.APPLY , event -> apply()  );
        cancel = getButton( ButtonText.CANCEL, event -> cancel() );

        return ButtonBar.getInstance(
                apply
                , cancel
        );
    }

//    protected abstract Node createContent();
    protected Node createContent(){
        contentNode = new BorderPane();

        javafx.scene.control.ButtonBar buttonBar = createButtonBar();
        contentNode.setBottom(buttonBar);
        contentNode.setMargin(buttonBar, new Insets(2));

        return contentNode;
    };

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

    protected abstract void updateIdentifiable();

    protected class ComboBoxListedCell extends ListCell<Typable> {
        @Override
        protected void updateItem(Typable typable, boolean b) {
            if(typable != null){
                super.updateItem(typable, b);
                setText(typable.getValue());
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

    @Override
    public void close() {

    }

    @Override
    public SimpleObjectProperty<Node> getToolsProperty() {
        return tools;
    }

    @Override
    public SimpleObjectProperty<Node> getContentProperty() {
        return content;
    }

    @Override
    public void setCloseable(Closeable closeable) {
        this.closeable = closeable;
    }
}
