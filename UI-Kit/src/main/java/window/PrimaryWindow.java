package window;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import layout.title.TitleWithMenu;

import java.awt.*;

public class PrimaryWindow extends Window{

    private Stage stage;

    public PrimaryWindow(Stage stage) {
        super();

        this.stage = stage;

        title = new TitleWithMenu(stage);

        title.setOnMousePressed(this::stagePress);
        title.setOnMouseDragged(this::stageDrag);

        title.getClose().setOnAction(event -> stage.close());

        setTop(this.title);
    }

    public void stagePress(MouseEvent mouseEvent) {
        switch (mouseEvent.getButton()) {
            case PRIMARY: {
                title.setSwitchSceneX( mouseEvent.getX() );
                title.setSwitchSceneY( mouseEvent.getY() );
                if(stage.isMaximized()){
                    title.setSwitchSceneX( (mouseEvent.getX() - 0.0)*(getPrefWidth() - 0.0)/(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth() - 0.0) );
                }
                break;
            }
        }
    }

    private void stageDrag(MouseEvent mouseEvent) {
        switch (mouseEvent.getButton()) {
            case PRIMARY: {
                if(stage.isMaximized()) stage.setMaximized(false);
                stage.setX(mouseEvent.getScreenX() - title.getSwitchSceneX());
                stage.setY(mouseEvent.getScreenY() - title.getSwitchSceneY());
            }
        }
    }
}
