package window;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import layout.title.TitleWithMenu;

public class PrimaryWindow extends Window{

    private Stage stage;

    public PrimaryWindow(Stage stage) {
        super();

        this.stage = stage;

        title = new TitleWithMenu();

        title.setOnMousePressed(this::stagePress);
        title.setOnMouseDragged(this::stageDrag);

        title.getClose().setOnAction(event -> stage.close());

        setTop(this.title);

        new StageResizer(this.stage);
    }

    public void stagePress(MouseEvent mouseEvent) {
        switch (mouseEvent.getButton()) {
            case PRIMARY: {
                title.setSwitchSceneX( mouseEvent.getX() );
                title.setSwitchSceneY( mouseEvent.getY() );
                break;
            }
        }
    }

    private void stageDrag(MouseEvent mouseEvent) {
        switch (mouseEvent.getButton()) {
            case PRIMARY: {
                stage.setX(mouseEvent.getScreenX() - title.getSwitchSceneX());
                stage.setY(mouseEvent.getScreenY() - title.getSwitchSceneY());
            }
        }
    }
}
