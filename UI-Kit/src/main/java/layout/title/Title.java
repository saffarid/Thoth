package layout.title;

import controls.Button;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public abstract class Title extends BorderPane {

    protected final String URL_CLOSE  = "/image/icons/close.png";
    protected final String URL_SQUARE = "/image/icons/square.png";
    protected final String URL_MINIFY = "/image/icons/minify.png";
    protected final String URL_MINIFY_TO_TASK_BAR = "/image/icons/minify-to-task-bar.png";

    private final String STYLE_CLASS_TITLE = "title";
    private final String STYLE_CLASS_CLOSE = "close";

    /**
     * Кнопка закрытия окна
     * */
    protected Button close;

    public Title() {
        super();
    }

    public Title(Node node) {
        super(node);
    }

    public Title(Node node, Node node1, Node node2, Node node3, Node node4) {
        super(node, node1, node2, node3, node4);
    }

    protected void init(){
        HBox imageBar = new HBox();
        setRight(imageBar);

        close = addButton(URL_CLOSE);
        imageBar.getChildren().add(0, close);

        getStyleClass().add(STYLE_CLASS_TITLE);

        getStylesheets().add(getClass().getResource("/style/layout/title/title.css").toExternalForm());
        close.getStyleClass().add(STYLE_CLASS_CLOSE);
    }

    protected Button addButton(String url) {
        Button btn = new Button();
        ImageView closeImg = new ImageView(
                new Image(getClass().getResourceAsStream(url), 25, 25, true, false)
        );
        btn.setGraphic(closeImg);
        return btn;
    }

}
