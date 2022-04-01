package layout.custompane;

import controls.Twin;
import layout.basepane.BorderPane;
import layout.basepane.ScrollPane;
import layout.basepane.VBox;
import styleconstants.Stylesheets;
import styleconstants.Styleclasses;

public class TwinPane extends BorderPane {

    private final String STYLE_CLASS_TWIN_PANE = "twin-pane";
    private final String STYLE_CLASS_CONTENT = "content";

    private ScrollPane scrollPane;

    private VBox content;

    public TwinPane() {
        super();
        init();
    }

    public TwinPane(Twin... nodes) {
        super();
        init();

        content.getChildren().addAll(nodes);
    }

    private void init(){
        scrollPane = new ScrollPane();
        content = new VBox();
        scrollPane.setContent(content);
        setCenter(scrollPane);

        getStylesheets().add(getClass().getResource("/style/layout/panes/custom/twin_pane.css").toExternalForm());

        scrollPane.getStyleClass().remove(Styleclasses.BORDER);
        content.getStyleClass().add(STYLE_CLASS_CONTENT);
    }

    public void add(Twin twin){
        content.getChildren().add(twin);
    }

    public void addAll(Twin... twins){
        content.getChildren().addAll(twins);
    }

}
