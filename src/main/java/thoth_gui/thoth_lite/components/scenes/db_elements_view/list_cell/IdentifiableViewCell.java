package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_cell;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import layout.basepane.BorderPane;
import layout.basepane.GridPane;
import thoth_gui.thoth_lite.components.controls.Label;
import tools.BackgroundWrapper;
import tools.BorderWrapper;
import thoth_core.thoth_lite.db_data.db_data_element.properties.*;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;

import javafx.geometry.HPos;

import javafx.scene.Node;
import thoth_gui.thoth_styleconstants.svg.Images;
import tools.SvgWrapper;

public abstract class IdentifiableViewCell
        extends BorderPane {

    protected SimpleObjectProperty<Identifiable> identifiable = new SimpleObjectProperty<>(null);

    protected AvaliableTables table;

    public IdentifiableViewCell() {
        super();
        init();
    }

    /**
     * Общая инициализация для всех исполнений
     */
    private void init() {
        identifiable.addListener(this::identifiableChange);
        BorderPane.setAlignment(this, Pos.CENTER);

    }

    protected void identifiableChange(
            ObservableValue<? extends Identifiable> observableValue,
            Identifiable prevObj,
            Identifiable curObj){
        if(curObj == null) return;

        setLeft(leftNode());
        setCenter(centerNode());
        setRight(rightNode());
    }

    public void setTable(AvaliableTables table) {
        this.table = table;
    }

    static IdentifiableViewCell getInstance(Identifiable identifiable) {
        if (identifiable instanceof Storagable) {
            return new StoragableViewCell((Storagable) identifiable);
        } else if (identifiable instanceof Purchasable) {
            return new PurchasableViewCell((Purchasable) identifiable);
        } else if (identifiable instanceof Typable) {
            return new ListedViewCell((Typable) identifiable);
        } else if (identifiable instanceof Finance) {
            return new FinanceViewCell((Finance) identifiable);
        } else if(identifiable instanceof Partnership){
            return new PartnerViewCell((Partnership) identifiable);
        }
        return null;
    }

    protected abstract Node leftNode();

    protected abstract Node centerNode();

    protected abstract Node rightNode();

}
