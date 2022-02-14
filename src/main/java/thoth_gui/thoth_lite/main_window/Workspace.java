package thoth_gui.thoth_lite.main_window;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import main.Main;
import thoth_gui.thoth_lite.components.controls.Button;
import thoth_gui.thoth_lite.components.scenes.ThothScene;

import layout.basepane.BorderPane;
import layout.basepane.HBox;

import javafx.scene.Node;
import javafx.beans.property.SimpleObjectProperty;
import thoth_gui.thoth_styleconstants.svg.Images;
import tools.BorderWrapper;
import tools.SvgWrapper;

import java.util.Stack;
import java.util.logging.Level;

public class Workspace
        extends BorderPane {

    public final static double svgWidthTools = 20;
    public final static double svgHeightTools = 20;
    public final static double svgViewBoxWidthTools = 30;
    public final static double svgViewBoxHeightTools = 30;

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
        toolsPanel.setPadding(new Insets(0, 5, 0, 0));
        back = Button.getInstance(
                SvgWrapper.getInstance(Images.ARROW_LEFT(), svgWidthTools, svgHeightTools, svgViewBoxWidthTools, svgViewBoxHeightTools)
                , event -> stepToPreviousScene());
        next = Button.getInstance(
                SvgWrapper.getInstance(Images.ARROW_RIGHT(), svgWidthTools, svgHeightTools, svgViewBoxWidthTools, svgViewBoxHeightTools)
                , event -> stepToNextScene()
        );

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
