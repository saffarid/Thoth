package thoth_gui.thoth_lite.main_window;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import thoth_gui.thoth_lite.components.controls.Button;
import thoth_gui.thoth_lite.components.scenes.ThothScene;

import layout.basepane.BorderPane;
import layout.basepane.HBox;

import javafx.scene.Node;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Stack;

public class Workspace
        extends BorderPane {

    private static Workspace workspace;

    /**
     * Стек предыдущих сцен
     */
    private Stack<ThothScene> previousScene;

    /**
     * Стек следующих сцен
     */
    private Stack<ThothScene> nextScene;

    /**
     * Текущая сцена
     */
    private ThothScene currentScene;
    private controls.Button back;
    private controls.Button next;
    private BorderPane toolsPanel;

    private Workspace() {
        super();
        setTop(getToolsPanel());

        previousScene = new Stack();
        nextScene = new Stack();

    }

    private Node getToolsPanel() {
        this.toolsPanel = new BorderPane();
        this.toolsPanel.setPadding(new Insets(2));
        HBox toolsPanel = new HBox();
        toolsPanel.setSpacing(2);
        back = Button.getInstance("back", event -> stepToPreviousScene());
        next = Button.getInstance("next", event -> stepToNextScene());

        toolsPanel.getChildren().addAll(
                back,
                next
        );
        toolsPanel.setAlignment(Pos.CENTER_LEFT);

        this.toolsPanel.setLeft(toolsPanel);

        return this.toolsPanel;
    }

    public static Workspace getInstance(){
        if(workspace == null){
            workspace = new Workspace();
        }
        return workspace;
    }

    /**
     * Функция удаляет текущую сцену и устанавливает предыдущую
     * */
    public void closeScene(){
        if (!previousScene.empty()){
            toolsPanel.centerProperty().unbind();
            this.currentScene = previousScene.pop();
            setscene();
        }
    }

    private void setscene() {
        SimpleObjectProperty<Node> value = this.currentScene.getToolsProperty();
        if (value != null) {
            toolsPanel.centerProperty().bind(value);
        }
        setCenter(this.currentScene.getContentProperty().getValue());
        this.currentScene.setCloseable( () -> closeScene() );
    }

    /**
     * Функция устанавливает новую сцену для рабочего поля без сохранения в стек текущей сцены.
     * */
    public void setNewSceneWithoutSave(ThothScene newScene){
        if (this.currentScene != null) {
            previousScene.push(this.currentScene);
            toolsPanel.centerProperty().unbind();
        }
        this.currentScene = newScene;
        setscene();
        back.setDisable(previousScene.empty());
        next.setDisable(nextScene.empty());
    }

    /**
     * Функция устанавливает новую сцену для рабочего поля
     */
    public void setNewScene(ThothScene newScene) {
        if (this.currentScene != null) {
            previousScene.push(this.currentScene);
            toolsPanel.centerProperty().unbind();
        }
        this.currentScene = newScene;
        setscene();
        back.setDisable(previousScene.empty());
        nextScene.clear();
        next.setDisable(nextScene.empty());
    }

    /**
     * Функция устанавливает следующую сцену при её наличии
     */
    public void stepToNextScene() {
        if (!nextScene.empty()) {
            previousScene.push(currentScene);

            toolsPanel.centerProperty().unbind();
            currentScene = nextScene.pop();
            setscene();
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

            toolsPanel.centerProperty().unbind();
            currentScene = previousScene.pop();
            setscene();
            back.setDisable(previousScene.empty());
            next.setDisable(nextScene.empty());
        }
    }

}
