package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_cell;


import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import layout.basepane.BorderPane;
import layout.basepane.GridPane;

import thoth_core.thoth_lite.db_data.db_data_element.properties.Purchasable;
import thoth_gui.thoth_lite.components.controls.Label;
import thoth_gui.thoth_styleconstants.svg.Images;
import tools.SvgWrapper;

import java.time.format.DateTimeFormatter;

public class PurchasableViewCell
        extends IdentifiableViewCell {


    private static final String NOT_DELIVERED= "not-delivered";
    private static final String DELIVERED= "delivered";

    protected PurchasableViewCell(Purchasable purchase) {
        super();
        this.identifiable.setValue(purchase);
    }

    @Override
    protected Node leftNode() {
        Node instance = SvgWrapper.getInstance(Images.PURCHASE(), 30, 30, 35, 35);
        setAlignment(instance, Pos.CENTER);
        return instance;
    }

    @Override
    protected Node centerNode() {
        GridPane res = new GridPane();

        res
                .addColumn(Priority.ALWAYS, HPos.LEFT)
                .addColumn(Priority.ALWAYS, HPos.RIGHT)
                .addRow(Priority.ALWAYS)
                .addRow(Priority.ALWAYS);

        Purchasable purchase = (Purchasable) this.identifiable.getValue();

        res.add(Label.getInstanse(purchase.getId()), 0, 0);
        res.add(Label.getInstanse(purchase.finishDate().format(DateTimeFormatter.ISO_DATE)), 0, 1);
        res.add(Label.getInstanse((purchase.isDelivered())?(DELIVERED):(NOT_DELIVERED)), 1, 1);

        BorderPane.setAlignment(res, Pos.CENTER);

        return res;
    }

    @Override
    protected Node rightNode() {
        Node edit = SvgWrapper.getInstance(Images.ARROW_RIGHT(), 30, 30, 35, 35);
        return edit;
    }
}
