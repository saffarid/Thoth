package layout.title;


import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public abstract class Title extends BorderPane {

    protected final String URL_CLOSE  = "/image/icons/close.png";
    protected final String URL_SQUARE = "/image/icons/square.png";
    protected final String URL_MINIFY = "/image/icons/minify.png";
    protected final String URL_MINIFY_TO_TASK_BAR = "/image/icons/minify-to-task-bar.png";

    private final String STYLE_CLASS_TITLE = "title";
    private final String STYLE_CLASS_CLOSE = "close";

    protected double switchSceneX;
    protected double switchSceneY;

    /**
     * Кнопка закрытия окна
     * */
    protected Button close;

    public Title() {
        super();
        init();
    }

    protected Button addButton(String url) {
        Button btn = new Button();
        ImageView closeImg = new ImageView(
                new Image(getClass().getResourceAsStream(url), 15, 15, true, false)
        );
        btn.setGraphic(closeImg);
        return btn;
    }

    public Button getClose() {
        return close;
    }

    public double getSwitchSceneX() {
        return switchSceneX;
    }

    public double getSwitchSceneY() {
        return switchSceneY;
    }

    private void init(){

        HBox imageBar = new HBox();
        setRight(imageBar);

        close = addButton(URL_CLOSE);
        imageBar.getChildren().add(0, close);

        getStyleClass().add(STYLE_CLASS_TITLE);

        getStylesheets().add(getClass().getResource("/style/layout/title/title.css").toExternalForm());
        close.getStyleClass().add(STYLE_CLASS_CLOSE);
    }

    public void setSwitchSceneX(double switchSceneX) {
        this.switchSceneX = switchSceneX;
    }

    public void setSwitchSceneY(double switchSceneY) {
        this.switchSceneY = switchSceneY;
    }

}
