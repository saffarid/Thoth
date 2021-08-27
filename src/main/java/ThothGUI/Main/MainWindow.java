package ThothGUI.Main;

import controls.MenuButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import layout.basepane.StackPane;
import layout.custompane.NavigationMenu;
import window.PrimaryWindow;
import window.Subwindow;
import window.SubwindowResizer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainWindow extends PrimaryWindow {

    private Stage mainStage;

    private NavigationMenu menu;

    private StackPane workspace;

    private List<String> openSubwindows;

    public MainWindow(Stage stage) {
        super(stage);
        mainStage = stage;

        openSubwindows = new ArrayList<>();

        menuConfig();
        workspaceConfig();
        styleConfig();

    }

    private MenuButton getMenuButton(String mes, String url, EventHandler<ActionEvent> event){
        MenuButton menuButton = new MenuButton(mes);
        menuButton.setGraphic(
                new ImageView(
                        new Image(getClass().getResource(url).toExternalForm(), 30, 30, true, true)
                )
        );
        menuButton.setOnAction(event);
        return menuButton;
    }

    private void menuConfig(){
        List<MenuButton> menuButtons = new LinkedList<>();
        menuButtons.add(getMenuButton("Анализатор", thoth_styleconstants.Image.ANALYZE, this::openAnalyzator));
        menuButtons.add(getMenuButton("Таблицы", thoth_styleconstants.Image.TABLE, this::openTables));
        menu = new NavigationMenu("", true, menuButtons);
        setLeft(menu);
    }

    private Subwindow createSubwindow(String title){
        Subwindow subwindow = new Subwindow(title);
        subwindow.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) subwindow.toFront();
        });
        if (!workspace.getChildren().contains(subwindow)){
            workspace.getChildren().add(subwindow);
        }else{
            subwindow.toFront();
        }
        subwindow.setCloseEvent(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent click) {
                if (workspace.getChildren().contains(subwindow)) {
                    workspace.getChildren().remove(subwindow);
                }
            }
        });
        new SubwindowResizer(subwindow);
        return subwindow;
    }

    private void openAnalyzator(ActionEvent event) {
        Subwindow subwindow = createSubwindow("Анализатор");

    }

    private void openTables(ActionEvent event) {
        Subwindow subwindow = createSubwindow("Список таблиц");
    }

    private void styleConfig(){
        getStylesheets().addAll(
                getClass().getResource("/style/list.css").toExternalForm()
        );
    }

    private void workspaceConfig(){
        workspace = new StackPane();
        setCenter(workspace);
    }

}
