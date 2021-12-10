package ThothGUI.ThothLite.Subwindows;

import ThothGUI.CloseSubwindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import layout.custompane.Title;
import window.Closeable;
import window.Window;

public abstract class Subwindow
        extends Window
        implements Closeable
{

    protected CloseSubwindow closeSubwindow;
    protected Node content;

    public Subwindow(String title) {
        super();
        this.title = new Title()
                .addClose(event -> close())
                .addText(title);

        this.title.setOnMousePressed(this::press);
        this.title.setOnMouseDragged(this::drag);

        setTop(this.title);

    }

    @Override
    public void close() {
        closeSubwindow.closeSubwindow(this);
        if(content instanceof Closeable){
            ((Closeable)content).close();
        }
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

    public void setCloseSubwindow(CloseSubwindow closeSubwindow) {
        this.closeSubwindow = closeSubwindow;
    }

    @Override
    public void toFront() {
        super.toFront();
    }
}
