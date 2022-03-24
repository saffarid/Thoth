package thoth_gui.thoth_lite.components.scenes.db_elements_view.identifiable_card;

import javafx.beans.property.SimpleObjectProperty;

import layout.basepane.BorderPane;

import layout.basepane.HBox;
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

import thoth_gui.GuiLogger;
import thoth_gui.thoth_lite.components.controls.Button;

import thoth_gui.thoth_lite.components.controls.ToolsPane;
import thoth_gui.thoth_lite.components.controls.Tooltip;
import thoth_gui.thoth_lite.components.scenes.ThothSceneImpl;

import thoth_gui.thoth_styleconstants.svg.Images;
import tools.SvgWrapper;
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

    protected final controls.Button apply = Button.getInstance(
            SvgWrapper.getInstance(Images.CHECKMARK(), svgWidthTool, svgHeightTool, svgViewBoxWidthTool, svgViewBoxHeightTool),
            event -> apply()
    ).setTool(Tooltip.getInstance(ButtonText.APPLY.name()));
    protected final controls.Button cancel = Button.getInstance(
            SvgWrapper.getInstance(Images.CLOSE(), svgWidthTool, svgHeightTool, svgViewBoxWidthTool, svgViewBoxHeightTool),
            event -> cancel()
    ).setTool(Tooltip.getInstance(ButtonText.CANCEL.name()));

    protected static final Logger LOG;

    private EventHandler<ActionEvent> closeEvent;

    protected enum ButtonText {
        APPLY("apply"),
        CANCEL("cancel");
        private String text;

        ButtonText(String text) {
            this.text = text;
        }
    }

    protected final SimpleObjectProperty<Identifiable> identifiable = new SimpleObjectProperty<>(null);

    protected boolean identifiableIsNew;
    //    protected Identifiable identifiable;
    protected AvaliableTables table;

    static {
        LOG = Logger.getLogger(IdentifiableCard.class.getName());
    }

    protected IdentifiableCard(
            Identifiable identifiable
            , AvaliableTables table
    ) {

        GuiLogger.log.info("Show card " + this.table);
        content = new SimpleObjectProperty<>(null);
        this.table = table;
        this.identifiable.addListener((observableValue, identifiable1, t1) -> {
            content.setValue(createContentNode());
        });

        if (identifiable != null) {
            identifiableIsNew = false;
            this.identifiable.setValue(identifiable);
        } else {
            identifiableIsNew = true;
            this.identifiable.setValue(identifiableInstance());
        }
    }

    @Override
    public void apply() {
        LOG.log(Level.INFO, identifiable.toString());
        if (identifiable.getValue() == null) return;
        updateIdentifiable();
        List<Identifiable> list = new LinkedList<>();
        list.add(identifiable.getValue());
        try {
            if (identifiableIsNew) {
                GuiLogger.log.info("Insert into " + this.table);
                ThothLite.getInstance().insertToTable(table, list);
            } else {
                GuiLogger.log.info("Update in " + this.table);
                ThothLite.getInstance().updateInTable(table, list);
            }
            closeable.close();
        } catch (NotContainsException | ClassNotFoundException e) {
            GuiLogger.log.error(e.getMessage(), e);
        }

    }

    @Override
    public void cancel() {
        closeable.close();
    }

    @Override
    protected Node createContentNode() {
        contentNode = new BorderPane();
        return contentNode;
    }

    @Override
    protected Node createToolsNode() {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(0, 0, 0, 5));

        hBox.getChildren().addAll(
                apply,
                cancel
        );

        return new ToolsPane()
                .addAdditional(hBox);
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
