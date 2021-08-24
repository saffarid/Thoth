package layout.title;

import controls.Button;
import javafx.scene.Node;
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

    private double switchSceneX;
    private double switchSceneY;

    private Stage stage;

    /**
     * Кнопка закрытия окна
     * */
    protected Button close;

    public Title(Stage stage) {
        super();
        init(stage);
    }

    public Title(Stage stage, Node node) {
        super(node);
        init(stage);
    }

    public Title(Stage stage, Node node, Node node1, Node node2, Node node3, Node node4) {
        super(node, node1, node2, node3, node4);
        init(stage);
    }

    private void init(Stage stage){
        this.stage = stage;

        HBox imageBar = new HBox();
        setRight(imageBar);

        close = addButton(URL_CLOSE);
        imageBar.getChildren().add(0, close);

        getStyleClass().add(STYLE_CLASS_TITLE);

        getStylesheets().add(getClass().getResource("/style/layout/title/title.css").toExternalForm());
        close.getStyleClass().add(STYLE_CLASS_CLOSE);

        setOnMousePressed(this::stagePress);
        setOnMouseDragged(this::stageDrag);

        close.setOnAction(event -> stage.close());
    }

    protected Button addButton(String url) {
        Button btn = new Button();
        ImageView closeImg = new ImageView(
                new Image(getClass().getResourceAsStream(url), 25, 25, true, false)
        );
        btn.setGraphic(closeImg);
        return btn;
    }

    private void savePos(MouseEvent mouseEvent) {
//        Main.LOG.log(Level.INFO, "Сохранение положения окна");
//        Config.getConfig().getWindow().setStartX(stage.getX());
//        Config.getConfig().getWindow().setStartY(stage.getY());
    }


    public void stagePress(MouseEvent mouseEvent) {
        switch (mouseEvent.getButton()) {
            case PRIMARY: {
//                Main.LOG.log(Level.INFO, "Подготовка к перетаскиванию окна");
//                Main.LOG.log(Level.INFO, "Считывание значений: положение мыши");
                switchSceneX = mouseEvent.getX();
                switchSceneY = mouseEvent.getY();
//                if(stage.equals(stage) && stage.isMaximized()){
//                    stage.setMaximized(false);
//                    switchSceneX = (mouseEvent.getX() - 0.0)*(Config.getConfig().getWindow().getWidth() - 0.0)/(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth() - 0.0);
//                }
                break;
            }
        }
    }

    private void stageDrag(MouseEvent mouseEvent) {
        switch (mouseEvent.getButton()) {
            case PRIMARY: {
                stage.setX(mouseEvent.getScreenX() - switchSceneX);
                stage.setY(mouseEvent.getScreenY() - switchSceneY);
            }
        }
    }

}
