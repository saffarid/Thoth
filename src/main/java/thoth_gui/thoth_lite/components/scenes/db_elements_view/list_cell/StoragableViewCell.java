package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_cell;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import layout.basepane.BorderPane;
import layout.basepane.GridPane;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Storagable;
import thoth_gui.thoth_lite.components.controls.Label;
import thoth_gui.thoth_styleconstants.svg.Images;
import tools.SvgWrapper;


public class StoragableViewCell
        extends IdentifiableViewCell {

    protected StoragableViewCell(Storagable product) {
        super( );
        this.identifiable.setValue(product);
    }

    @Override
    protected Node leftNode() {
        Node instance = SvgWrapper.getInstance(Images.PRODUCT(), 30, 30, 35, 35);
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

        Storagable product = (Storagable) this.identifiable.getValue();

        res.add(Label.getInstanse(product.getId()), 0, 0);
        res.add(Label.getInstanse(product.getName()), 0, 1);
        res.add(Label.getInstanse(product.getType().getValue()), 1, 1);

        BorderPane.setAlignment(res, Pos.CENTER);

        return res;
    }

    @Override
    protected Node rightNode() {
        Node edit = SvgWrapper.getInstance(Images.ARROW_RIGHT(), 30, 30, 35, 35);
        return edit;
    }
}
