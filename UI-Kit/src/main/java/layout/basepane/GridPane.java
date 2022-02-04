package layout.basepane;

import javafx.geometry.VPos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class GridPane
        extends javafx.scene.layout.GridPane {

    public GridPane() {
        super();
    }

    public GridPane addOnlyRow(){

        RowConstraints only = new RowConstraints();
        only.setVgrow(Priority.ALWAYS);
        only.setValignment(VPos.CENTER);
        only.setFillHeight(true);

        getRowConstraints().add(only);

        return this;
    }



}
