package thoth_gui.thoth_lite.components.controls;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import layout.basepane.HBox;
import layout.basepane.VBox;

import java.util.Arrays;

public class Row {

    public static Node getInstance(
            Node titleNode
            , Node... enterNodes
    ){
        VBox res = new VBox();

        res.setAlignment(Pos.TOP_LEFT);
        res.setFillWidth(true);
        res.setPadding(new Insets(2));
        res.setSpacing(2);

        if (enterNodes.length > 1) {
            HBox hBox = new HBox(enterNodes);
            hBox.setSpacing(5);

            res.getChildren().addAll(
                    titleNode
                    , hBox
            );
        } else {
            res.getChildren().addAll(
                    titleNode
                    , Arrays.stream(enterNodes).findFirst().get()
            );
        }

        return res;
    }

}
