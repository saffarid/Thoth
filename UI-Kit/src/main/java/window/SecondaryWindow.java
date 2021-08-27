package window;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import layout.title.TitleWithoutMenu;

public class SecondaryWindow extends Window{

    private Stage stage;

    public SecondaryWindow(Stage stage, String title) {
        super();
        this.stage = stage;
        this.title = new TitleWithoutMenu(title);

        this.title.setOnMousePressed(this::stagePress);
        this.title.setOnMouseDragged(this::stageDrag);

        this.title.getClose().setOnAction(event -> stage.close());

        setTop(this.title);
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
