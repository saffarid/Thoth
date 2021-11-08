package ThothGUI.ThothLite;


import ThothCore.ThothLite.DBData.DBDataElement.Properties.Finishable;
import ThothCore.ThothLite.DataTables;
import ThothCore.ThothLite.ThothLite;
import ThothGUI.CloseSubwindow;
import ThothGUI.OpenSubwindow;
import ThothGUI.ThothLite.Subwindows.IdentifiableListWindow;
import ThothGUI.ThothLite.Subwindows.Products;
import ThothGUI.ThothLite.Subwindows.Purchases;

import controls.MenuButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import layout.basepane.StackPane;
import layout.custompane.NavigationMenu;
import window.PrimaryWindow;
import window.Subwindow;

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

    private static ThothLiteWindow window;

    private Stage mainStage;

    private NavigationMenu menu;

    private StackPane workspace;

    private List<String> openSubwindows;

    private ThothLite thoth;

    static {
        LOG = Logger.getLogger("ThothLiteGUI");
    }

    private ThothLiteWindow(Stage stage,
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

    public static ThothLiteWindow getInstance(){
        if(window == null){
            throw new NullPointerException();
        }
        return window;
    }

    public static ThothLiteWindow getInstance(Stage stage,
                                              ThothLite thoth){
        if(window == null){
            window = new ThothLiteWindow(stage, thoth);
        }
        return window;
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
                STRING_KEY_ORDERS, thoth_styleconstants.Image.ORDER, event -> {}
        ));
        menuButtons.add(getMenuButton(
                STRING_KEY_PURCHASES, thoth_styleconstants.Image.PURCHASE, event -> {openSubwindow(new IdentifiableListWindow("Покупки", DataTables.PURCHASABLE));}
        ));
        menuButtons.add(getMenuButton(
                STRING_KEY_PROJECTS, thoth_styleconstants.Image.PROJECT, event -> {}
        ));
        menuButtons.add(getMenuButton(
                STRING_KEY_STORAGE, thoth_styleconstants.Image.STORAGE_CELL, event -> {}
        ));
        menuButtons.add(getMenuButton(
                STRING_KEY_TABLES_GUIDE, thoth_styleconstants.Image.ANALYZE, event -> {}
        ));
        menuButtons.add(getMenuButton(
                "Продукты", thoth_styleconstants.Image.PRODUCT, event -> {openSubwindow(new IdentifiableListWindow("Продукты", DataTables.STORAGABLE));}
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
