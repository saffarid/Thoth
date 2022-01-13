package window;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import layout.custompane.Title;

import java.awt.*;

public abstract class PrimaryWindow
        extends Window{

    public PrimaryWindow(Stage stage) {
        super();
        this.stage = stage;

        title = new Title()
                .addIconify(event -> iconify())
                .addMinify(event -> minify(), stage.maximizedProperty())
                .addClose(event -> close());
        isMinify.bind(stage.maximizedProperty());
        title.setOnMousePressed(this::stagePress);
        title.setOnMouseDragged(this::stageDrag);

        stage.maximizedProperty().addListener((observableValue, aBoolean, t1) -> {
            if(isMinify.getValue()){
                stage.setWidth(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth());
                stage.setHeight(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight());
            }else{

                stage.setWidth(900);
                stage.setHeight(600);
            }
        });

        setTop(this.title);
    }

    @Override
    public void close() {

    }

    private void minify(){
        stage.setMaximized(!stage.isMaximized());
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
