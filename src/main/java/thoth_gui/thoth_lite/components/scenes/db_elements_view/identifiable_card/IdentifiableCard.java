package thoth_gui.thoth_lite.components.scenes.db_elements_view.identifiable_card;

import javafx.beans.property.SimpleObjectProperty;

import layout.basepane.BorderPane;

import thoth_core.thoth_lite.db_data.db_data_element.properties.Identifiable;

import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_core.thoth_lite.ThothLite;
import thoth_gui.Apply;
import thoth_gui.Cancel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;

import thoth_gui.thoth_lite.components.controls.Button;
import thoth_gui.thoth_lite.components.controls.ButtonBar;

import thoth_gui.thoth_lite.components.scenes.ThothSceneImpl;

import window.Closeable;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class IdentifiableCard
    extends ThothSceneImpl
        implements Apply
        , Cancel {

    protected static final Logger LOG;

    private EventHandler<ActionEvent> closeEvent;

    private enum ButtonText {
        APPLY("apply"),
        CANCEL("cancel");
        private String text;

        ButtonText(String text) {
            this.text = text;
        }
    }

    protected controls.Button apply;
    protected controls.Button cancel;

    protected boolean identifiableIsNew;
    protected Identifiable identifiable;
    protected AvaliableTables table;

    private Closeable closeable;

    static {
        LOG = Logger.getLogger(IdentifiableCard.class.getName());
    }

    protected IdentifiableCard(
            Identifiable identifiable
            , AvaliableTables table
    ) {

        if (identifiable != null) {
            this.identifiable = identifiable;
            identifiableIsNew = false;
        } else {
            this.identifiable = identifiableInstance();
            identifiableIsNew = true;
        }
        this.table = table;

        content = new SimpleObjectProperty<>(createContent());

    }

    @Override
    public void apply() {
        LOG.log(Level.INFO, identifiable.toString());
        if (identifiable != null) {
            updateIdentifiable();
            List<Identifiable> list = new LinkedList<>();
            list.add(identifiable);
            try {
                if (identifiableIsNew) {
                    ThothLite.getInstance().insertToTable(table, list);
                } else {
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

    private javafx.scene.control.ButtonBar createButtonBar() {
        apply = Button.getInstance(ButtonText.APPLY.text, event -> apply());
        cancel = Button.getInstance(ButtonText.CANCEL.text, event -> cancel());

        return ButtonBar.getInstance(
                apply
                , cancel
        );
    }

    protected Node createContent() {
        contentNode = new BorderPane();

        javafx.scene.control.ButtonBar buttonBar = createButtonBar();
        contentNode.setBottom(buttonBar);
        contentNode.setMargin(buttonBar, new Insets(2));

        return contentNode;
    }

    protected abstract Identifiable identifiableInstance();

    public static IdentifiableCard getInstance(
            AvaliableTables table
            , Identifiable identifiable
    ) {
        switch (table) {
            case STORAGABLE: {
                return new StoragableCard(identifiable, table);
            }
            case PURCHASABLE: {
                return new PurchasableCard(identifiable, table);
            }
            case EXPENSES:
            case INCOMES: {
                return new FinOperationCard(table);
            }
        }
        return null;
    }

    protected abstract void updateIdentifiable();

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
