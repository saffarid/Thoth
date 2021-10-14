package ThothGUI.ThothLite;

import ThothCore.ThothLite.DBData.Finishable;
import ThothCore.ThothLite.ThothLite;
import ThothGUI.CloseSubwindow;
import ThothGUI.OpenSubwindow;
import ThothGUI.ThothLite.Subwindows.Products;
import ThothGUI.ThothLite.Subwindows.Purchases;

import controls.Label;
import controls.MenuButton;
import controls.Toggle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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
import java.util.Optional;
import java.util.concurrent.Flow;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThothLiteWindow
        extends PrimaryWindow
        implements
        OpenSubwindow
        , CloseSubwindow
        , Flow.Subscriber<Finishable>
{

    private final String STRING_KEY_INCOME_EXPENSES = "Доходы/расходы";
    private final String STRING_KEY_ORDERS = "Заказы";
    private final String STRING_KEY_PURCHASES = "Покупки";
    private final String STRING_KEY_PROJECTS = "Проекты";
    private final String STRING_KEY_STORAGE = "Склад";
    private final String STRING_KEY_TABLES_GUIDE = "Списочные таблицы";

    private static Logger LOG;

    private Stage mainStage;

    private NavigationMenu menu;

    private StackPane workspace;

    private List<String> openSubwindows;

    private ThothLite thoth;

    static {
        LOG = Logger.getLogger("ThothLiteGUI");
    }

    public ThothLiteWindow(Stage stage,
                           ThothLite thoth) {
        super(stage);
        this.thoth = thoth;
        mainStage = stage;

        openSubwindows = new ArrayList<>();

        menuConfig();
        workspaceConfig();
        styleConfig();

        thoth.purchasesSubscribe(this);

    }


    private MenuButton getMenuButton(String mes, String url, EventHandler<ActionEvent> event){
        MenuButton menuButton = new MenuButton(mes);
        if(url != null) {
            menuButton.setGraphic(
                    new ImageView(
                            new Image(getClass().getResource(url).toExternalForm(), 20, 20, true, true)
                    )
            );
        }
        menuButton.setOnAction(event);
        return menuButton;
    }

    private void menuConfig(){
        List<MenuButton> menuButtons = new LinkedList<>();
        menuButtons.add(getMenuButton(
                STRING_KEY_INCOME_EXPENSES, thoth_styleconstants.Image.ANALYZE, event -> {}
        ));
        menuButtons.add(getMenuButton(
                STRING_KEY_ORDERS, thoth_styleconstants.Image.ANALYZE, event -> {}
        ));
        menuButtons.add(getMenuButton(
                STRING_KEY_PURCHASES, thoth_styleconstants.Image.ANALYZE, event -> {openSubwindow(new Purchases(STRING_KEY_PURCHASES, this::closeSubwindow));}
        ));
        menuButtons.add(getMenuButton(
                STRING_KEY_PROJECTS, thoth_styleconstants.Image.ANALYZE, event -> {}
        ));
        menuButtons.add(getMenuButton(
                STRING_KEY_STORAGE, thoth_styleconstants.Image.ANALYZE, event -> {}
        ));
        menuButtons.add(getMenuButton(
                STRING_KEY_TABLES_GUIDE, thoth_styleconstants.Image.ANALYZE, event -> {}
        ));
        menuButtons.add(getMenuButton(
                "Продукты", thoth_styleconstants.Image.ANALYZE, event -> {openSubwindow(new Products("Продукты", this::closeSubwindow, thoth));}
        ));
        menu = new NavigationMenu("", true, menuButtons);
        setLeft(menu);
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

        ObservableList<Node> children = workspace.getChildren();
        Optional<Node> first = children.stream()
                .filter(node -> node.getId().equals(subwindow.getId()))
                .findFirst();

        if (!first.isPresent()){
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

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        subscription.request(1);
        LOG.log(Level.INFO, "Подписка прошла успешно");
    }

    @Override
    public void onNext(Finishable item) {
        LOG.log(Level.INFO, item.message());
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }

}
