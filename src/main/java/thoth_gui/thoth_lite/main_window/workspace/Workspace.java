package thoth_gui.thoth_lite.main_window.workspace;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import layout.basepane.BorderPane;
import layout.basepane.HBox;
import thoth_gui.thoth_lite.components.controls.Button;
import thoth_gui.thoth_lite.components.controls.Label;

import java.util.HashMap;
import java.util.concurrent.Flow;

public class Workspace
        extends BorderPane
    implements Flow.Subscriber<HashMap<StackType, Boolean>>
{

    private Flow.Subscription subscription;

    /**
     * Стек предыдущих сцен
     */
    private Stack previousScene;

    /**
     * Стек следующих сцен
     */
    private Stack nextScene;

    /**
     * Текущая сцена
     */
    private Node currentScene;
    private controls.Button back;
    private controls.Button next;

    public Workspace(Node node) {
        super();
        setTop(getTitle());
        setCenter(node);

        previousScene = new Stack(StackType.PREVIOUS);
        nextScene = new Stack(StackType.NEXT);

        previousScene.subscribe(this);
        nextScene.subscribe(this);

    }

    private Node getTitle(){
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
        previousScene.push(this.currentScene);
        this.currentScene = newScene;
        setCenter(this.currentScene);
    }

    /**
     * Функция устанавливает следующую сцену при её наличии
     * */
    public void stepToNextScene(){
        if(!nextScene.empty()){
            previousScene.push(currentScene);
            currentScene = nextScene.pop();
            setCenter(currentScene);
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
        }
    }

    /* --- Работа с подпиской на изменения стека сцен --- */

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(HashMap<StackType, Boolean> item) {

            if (item.containsKey(StackType.NEXT)) {
                next.setDisable(item.get(StackType.NEXT).booleanValue());
            }
            if (item.containsKey(StackType.PREVIOUS)) {
                back.setDisable(item.get(StackType.PREVIOUS).booleanValue());
            }
            subscription.request(1);

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
