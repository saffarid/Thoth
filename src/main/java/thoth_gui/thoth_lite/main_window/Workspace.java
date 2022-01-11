package thoth_gui.thoth_lite.main_window;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import layout.basepane.BorderPane;
import layout.basepane.HBox;
import main.Main;
import thoth_gui.thoth_lite.components.controls.Button;
import thoth_gui.thoth_lite.components.controls.Label;

import java.util.HashMap;
import java.util.Stack;
import java.util.concurrent.Flow;
import java.util.logging.Level;

public class Workspace
        extends BorderPane {

    /**
     * Стек предыдущих сцен
     */
    private Stack<Node> previousScene;

    /**
     * Стек следующих сцен
     */
    private Stack<Node> nextScene;

    /**
     * Текущая сцена
     */
    private Node currentScene;
    private controls.Button back;
    private controls.Button next;

    public Workspace() {
        super();
        setTop(getTitle());

        previousScene = new Stack();
        nextScene = new Stack();

    }

    private Node getTitle() {
        HBox hBox = new HBox();

        back = Button.getInstance("back", event -> stepToPreviousScene());
        next = Button.getInstance("next", event -> stepToNextScene());

        hBox.getChildren().addAll(
                back,
                next
        );

        return hBox;
    }

    /**
     * Функция устанавливает новую сцену для рабочего поля
     */
    public void setNewScene(Node newScene) {
        if (this.currentScene != null) {
            previousScene.push(this.currentScene);
        }
        this.currentScene = newScene;
        setCenter(this.currentScene);
        back.setDisable(previousScene.empty());
        nextScene.clear();
        next.setDisable(true);
    }

    /**
     * Функция устанавливает следующую сцену при её наличии
     */
    public void stepToNextScene() {
        if (!nextScene.empty()) {
            previousScene.push(currentScene);
            currentScene = nextScene.pop();
            setCenter(currentScene);
            back.setDisable(previousScene.empty());
            next.setDisable(nextScene.empty());
        }
    }

    /**
     * Функция устанавливает предыдущую сцену при её наличии
     */
    public void stepToPreviousScene() {
        if (!previousScene.empty()) {
            nextScene.push(currentScene);
            currentScene = previousScene.pop();
            setCenter(currentScene);
            back.setDisable(previousScene.empty());
            next.setDisable(nextScene.empty());
        }
    }

}
