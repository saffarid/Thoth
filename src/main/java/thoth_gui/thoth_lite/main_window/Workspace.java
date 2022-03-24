package thoth_gui.thoth_lite.main_window;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.paint.Color;
import thoth_core.thoth_lite.ThothLite;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_gui.GuiLogger;
import thoth_gui.thoth_lite.components.controls.Button;
import thoth_gui.thoth_lite.components.controls.Tooltip;
import thoth_gui.thoth_lite.components.scenes.ThothScene;
import layout.basepane.BorderPane;
import layout.basepane.HBox;
import javafx.scene.Node;
import javafx.beans.property.SimpleObjectProperty;
import thoth_gui.thoth_styleconstants.svg.Images;
import tools.BorderWrapper;
import tools.SvgWrapper;
import java.sql.SQLException;
import java.util.Stack;

public class Workspace
        extends BorderPane {

    private final static double MAX_WIDTH = 750;

    public final static double svgWidthTools = 20;
    public final static double svgHeightTools = 20;
    public final static double svgViewBoxWidthTools = 30;
    public final static double svgViewBoxHeightTools = 30;

    /**
     * Кнопка установки предыдущей сцены
     */
    private final controls.Button back = Button.getInstance(
            SvgWrapper.getInstance(Images.ARROW_LEFT(), svgWidthTools, svgHeightTools, svgViewBoxWidthTools, svgViewBoxHeightTools)
            , event -> stepToPreviousScene());
    /**
     * Кнопка установки следующей сцены
     */
    private final controls.Button next = Button.getInstance(
            SvgWrapper.getInstance(Images.ARROW_RIGHT(), svgWidthTools, svgHeightTools, svgViewBoxWidthTools, svgViewBoxHeightTools)
            , event -> stepToNextScene()
    );
    /**
     * Кнопка обновления базы данных
     */
    private final controls.Button refresh = Button.getInstance(
            SvgWrapper.getInstance(Images.REFRESH(), svgWidthTools, svgHeightTools, svgViewBoxWidthTools, svgViewBoxHeightTools)
            , event -> {
                try {
                    ThothLite.getInstance().forceRereadDatabase();
                }
                catch (SQLException e) {
                    GuiLogger.log.error("Reread database error", e);
                }
                catch (ClassNotFoundException e) {
                    GuiLogger.log.error(e.getMessage(), e);
                }
            }
    ).setTool(Tooltip.getInstance("reread"));

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
    private BorderPane toolsPanel;

    private Workspace() {
        super();
        setTop(getToolsPanel());

        previousScene = new Stack();
        nextScene = new Stack();

        setMaxWidth(MAX_WIDTH);
        BorderPane.setAlignment(this, Pos.CENTER_LEFT);
    }

    private Node getToolsPanel() {
        this.toolsPanel = new BorderPane();

        this.toolsPanel.setPadding(new Insets(2));

        this.toolsPanel.setBorder(
                new BorderWrapper()
                        .addBottomBorder(1)
                        .setColor(Color.GREY)
                        .setStyle(BorderStrokeStyle.SOLID)
                        .commit()
        );

        HBox toolsPanel = new HBox();
        toolsPanel.setSpacing(2);
        toolsPanel.setPadding(new Insets(0, 5, 0, 0));

        toolsPanel.getChildren().addAll(
                back,
                next,
                refresh
        );
        toolsPanel.setAlignment(Pos.CENTER_LEFT);

        this.toolsPanel.setLeft(toolsPanel);

        return this.toolsPanel;
    }

    public static Workspace getInstance() {
        if (workspace == null) {
            workspace = new Workspace();
        }
        return workspace;
    }

    /**
     * Функция удаляет текущую сцену и устанавливает предыдущую
     */
    public void closeScene() {
        if (!previousScene.empty()) {
            //Отписку сцены от обновлений вставлять сюда
            this.currentScene.close();
            toolsPanel.centerProperty().unbind();
            this.currentScene = previousScene.pop();
            setscene();
        }
    }

    private void setscene() {
        this.currentScene.open();
        SimpleObjectProperty<Node> value = this.currentScene.getToolsProperty();
        if (value != null) {
            toolsPanel.centerProperty().bind(value);
        }
        setCenter(this.currentScene.getContentProperty().getValue());
        this.currentScene.setCloseable(() -> closeScene());
    }

    /**
     * Функция устанавливает новую сцену для рабочего поля без сохранения в стек текущей сцены.
     */
    public void setNewSceneWithoutSave(ThothScene newScene) {
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
        if (this.currentScene != null && this.currentScene.getId().equals(newScene.getId())) return;
        if (this.currentScene != null) {
            previousScene.push(this.currentScene);
            toolsPanel.centerProperty().unbind();
            this.currentScene.close();
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
            currentScene.close();
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
            currentScene.close();
            toolsPanel.centerProperty().unbind();
            currentScene = previousScene.pop();
            setscene();
            back.setDisable(previousScene.empty());
            next.setDisable(nextScene.empty());
        }
    }

}
