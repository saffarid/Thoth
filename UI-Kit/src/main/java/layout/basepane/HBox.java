package layout.basepane;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import tools.BackgroundWrapper;

public class HBox extends javafx.scene.layout.HBox {

    public HBox() {
        super();
        init();
    }

    public HBox(double v) {
        super(v);
        init();
    }

    public HBox(Node... nodes) {
        super(nodes);
        init();
    }

    public HBox(double v, Node... nodes) {
        super(v, nodes);
        init();
    }

    private void addDefaultStyle(){
        setBackground(
                new BackgroundWrapper()
                        .setColor(Color.TRANSPARENT)
                        .commit()
        );
    }

    private void init() {
        addDefaultStyle();
    }
}
