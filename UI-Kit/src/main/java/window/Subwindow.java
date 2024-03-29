package window;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import layout.title.TitleSubwindow;

public class Subwindow extends Window{

    public Subwindow(String title) {
        super();
        this.title = new TitleSubwindow(title);

        this.title.setOnMousePressed(this::press);
        this.title.setOnMouseDragged(this::drag);

        setTop(this.title);

    }

    private void drag(MouseEvent mouseEvent) {
        switch (mouseEvent.getButton()) {
            case PRIMARY: {
                setTranslateX(mouseEvent.getSceneX() - title.getSwitchSceneX());
                setTranslateY(mouseEvent.getSceneY() - title.getSwitchSceneY());
            }
        }
    }

    private void press(MouseEvent mouseEvent) {
        switch (mouseEvent.getButton()) {
            case PRIMARY: {
                title.setSwitchSceneX(mouseEvent.getSceneX() - getTranslateX());
                title.setSwitchSceneY(mouseEvent.getSceneY() - getTranslateY());
                break;
            }
        }
    }

    @Override
    public void toFront() {
        super.toFront();
    }

    public void setCloseEvent(EventHandler<ActionEvent> event){
        title.getClose().setOnAction(event);
    }
}
