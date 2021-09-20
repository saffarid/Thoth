package ThothGUI.Thoth;

import ThothCore.Thoth.Thoth;
import ThothGUI.Thoth.Nodes.Subwindows.TablesList;
import controls.Label;
import controls.MenuButton;
import controls.Toggle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import layout.basepane.BorderPane;
import layout.basepane.StackPane;
import layout.basepane.VBox;
import layout.custompane.DropdownPane;
import layout.custompane.NavigationMenu;
import window.PrimaryWindow;
import window.Subwindow;
import window.SubwindowResizer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ThothWindow
        extends PrimaryWindow
        implements
        OpenSubwindow
        , CloseSubwindow
{

    private Stage mainStage;

    private NavigationMenu menu;

    private StackPane workspace;

    private List<String> openSubwindows;

    private Thoth thoth;

    public ThothWindow(Stage stage,
                       Thoth thoth) {
        super(stage);
        this.thoth = thoth;
        mainStage = stage;

        openSubwindows = new ArrayList<>();

        menuConfig();
        workspaceConfig();
        styleConfig();

    }

    private MenuButton getMenuButton(String mes, String url, EventHandler<ActionEvent> event){
        MenuButton menuButton = new MenuButton(mes);
        if(url != null) {
            menuButton.setGraphic(
                    new ImageView(
                            new Image(getClass().getResource(url).toExternalForm(), 30, 30, true, true)
                    )
            );
        }
        menuButton.setOnAction(event);
        return menuButton;
    }

    private void menuConfig(){
        List<MenuButton> menuButtons = new LinkedList<>();
        menuButtons.add(getMenuButton("Анализатор", thoth_styleconstants.Image.ANALYZE, this::openAnalyzator));
        menuButtons.add(getMenuButton("Таблицы", thoth_styleconstants.Image.TABLE, this::openTables));
        menuButtons.add(getMenuButton("Тест", null, this::openTest));
        menu = new NavigationMenu("", true, menuButtons);
        setLeft(menu);
    }

    private Subwindow createSubwindow(String title){
        Subwindow subwindow = new Subwindow(title);
        subwindow.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) subwindow.toFront();
        });
        openSubwindow(subwindow);
        subwindow.setCloseEvent(click -> closeSubwindow(subwindow));
        new SubwindowResizer(subwindow);
        return subwindow;
    }

    private void openAnalyzator(ActionEvent event) {
        Subwindow subwindow = createSubwindow("Анализатор");
    }

    private void openTables(ActionEvent event) {

        openSubwindow( new TablesList(
                "Список таблиц"
                     , subwindow -> openSubwindow(subwindow)
                     , subwindow -> closeSubwindow(subwindow)
                )
        );

    }

    private void openTest(ActionEvent event) {

        Subwindow subwindow = createSubwindow("Тестовое окно");

        Pane pane = new Pane(new DropdownPane("test", new BorderPane(new Toggle(true))));

        BorderPane pane1 = new BorderPane(new DropdownPane("test", new BorderPane(new Label("hello"))));

        BorderPane pane2 = new BorderPane(new DropdownPane("test", new Pane(new Label("hello"))));

        VBox hBox = new VBox();
        hBox.getChildren().addAll(pane, pane1, pane2);
        hBox.setPadding(new Insets(5));
        hBox.setSpacing(5);

        subwindow.setCenter(hBox);

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

    @Override
    public void openSubwindow(Subwindow subwindow) {
        //Реализовать более адекватнуб проверку на открытие окна.
        //Возможно воспользоваться мапой.
        if (!workspace.getChildren().contains(subwindow)){
            workspace.getChildren().add(subwindow);
        }else{
            subwindow.toFront();
        }
    }

    @Override
    public void closeSubwindow(Subwindow subwindow) {
        if (workspace.getChildren().contains(subwindow)) {
            workspace.getChildren().remove(subwindow);
        }
    }
}
