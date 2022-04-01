package window;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import layout.custompane.Title;

public abstract class SecondaryWindow
        extends Window{


    public SecondaryWindow(Stage stage, String title) {
        super();
        this.stage = stage;
        this.title = new Title()
                .addText(title)
                .addClose(event -> close());

        this.title.setOnMousePressed(this::stagePress);
        this.title.setOnMouseDragged(this::stageDrag);

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

    @Override
    public void close() {
        this.stage.close();
    }
}
