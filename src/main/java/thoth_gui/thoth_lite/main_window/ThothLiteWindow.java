package thoth_gui.thoth_lite.main_window;

import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_core.thoth_lite.ThothLite;
import thoth_gui.CloseSubwindow;
import thoth_gui.config.Config;
import thoth_gui.OpenSubwindow;
import thoth_gui.thoth_lite.Settings;
import thoth_gui.thoth_lite.subwindows.IdentifiableListWindow;
import thoth_gui.thoth_lite.subwindows.ListedListWindow;
import thoth_gui.thoth_lite.subwindows.Subwindow;
import controls.MenuButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.*;
import layout.basepane.StackPane;
import layout.basepane.VBox;
import layout.custompane.NavigationMenu;
import org.json.simple.parser.ParseException;
import window.PrimaryWindow;
import window.StageResizer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ThothLiteWindow
        extends PrimaryWindow
        implements
        OpenSubwindow
        , CloseSubwindow
{

    private Config config;

    public enum DEFAULT_SIZE {
        HEIGHT(600),
        WIDTH(900);
        private double size;

        DEFAULT_SIZE(double size) {
            this.size = size;
        }

        public double getSize() {
            return size;
        }
    }

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

    private ThothLiteWindow(Stage stage) {
        super(stage);

        try {
            this.thoth = ThothLite.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NotContainsException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            this.config = Config.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mainStage = stage;
        openSubwindows = new ArrayList<>();

        menuConfig();
        setLeft(createLeftNode());
        workspaceConfig();
        styleConfig();

    }

    public static ThothLiteWindow getInstance() {
        if (window == null) {
            throw new NullPointerException();
        }
        return window;
    }

    public static ThothLiteWindow getInstance(Stage stage) {
        if (window == null) {
            window = new ThothLiteWindow(stage);
        }
        return window;
    }

    private Node createLeftNode(){
        VBox vBox = new VBox();

        FinishableView finishableViewPurchase = new FinishableView();

        thoth.purchasesSubscribe(finishableViewPurchase);

        vBox.getChildren().addAll(
                navigationMenuConfig(),
                finishableViewPurchase
        );

        return vBox;
    }

    @Override
    public void close() {
        thoth.close();
        mainStage.close();
    }

    private MenuButton getNavigationMenuButton(
            String mes
            , String url
            , EventHandler<ActionEvent> event
    ) {
        MenuButton menuButton = new MenuButton(mes);
        if (url != null) {
            menuButton.setGraphic(
                    new ImageView(
                            new Image(getClass().getResource(url).toExternalForm(), 20, 20, true, true)
                    )
            );
        }
        menuButton.setOnAction(event);
        return menuButton;
    }

    private MenuButton getNavigationMenuButton(
            String mes
            , Node node
            , EventHandler<ActionEvent> event
    ) {
        MenuButton menuButton = new MenuButton(mes);
        if (node != null) {
            menuButton.setGraphic( node );
        }
        menuButton.setOnAction(event);
        return menuButton;
    }

    private void menuConfig() {

        MenuItem config = new MenuItem("config");
        config.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN));
        config.setOnAction(event -> {

            Stage settings = new Stage(StageStyle.TRANSPARENT);
            Scene settingsScene = new Scene(
                    new Settings(settings, "settings")
                    , this.config.getWindow().getWidthSecondary()
                    , this.config.getWindow().getHeightSecondary()
            );

            //Установка начального положения
            settings.setX( this.config.getWindow().getxSecondary() );
            settings.setY( this.config.getWindow().getySecondary() );
            //Установка минимальных размеров
            settings.setMinWidth( this.config.getWindow().getWidthSecondaryMin() );
            settings.setMinHeight( this.config.getWindow().getHeightSecondaryMin() );
            //Связываем свойства начальных положений
            this.config.getWindow().xSecondaryProperty().bind( settings.xProperty() );
            this.config.getWindow().ySecondaryProperty().bind( settings.yProperty() );
            //Связываем размеры окна
            this.config.getWindow().widthSecondaryProperty().bind( settings.widthProperty() );
            this.config.getWindow().heightSecondaryProperty().bind( settings.heightProperty() );

            settings.initModality(Modality.APPLICATION_MODAL);
            settings.initOwner(mainStage);

            settings.setScene(settingsScene);

            settings.show();

            new StageResizer(settings);
        });

        MenuItem about = new MenuItem("about");

        MenuItem exit = new MenuItem("exit");
        exit.setOnAction(event -> close());
        exit.setAccelerator(new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN));


        title.addContextMenu(config, about, new SeparatorMenuItem(), exit);
    }

    private Node navigationMenuConfig() {
        List<MenuButton> menuButtons = new LinkedList<>();
//        menuButtons.add(getMenuButton(
//                STRING_KEY_INCOME_EXPENSES, ThothGUI.thoth_styleconstants.Image.ANALYZE, event -> {}
//        ));
//        menuButtons.add(getMenuButton(
//                STRING_KEY_ORDERS, ThothGUI.thoth_styleconstants.Image.ORDER, event -> {openSubwindow( new IdentifiableListWindow( thoth.getTableName(AvaliableTables.ORDERABLE), AvaliableTables.ORDERABLE) );}
//        ));
        menuButtons.add(getNavigationMenuButton(
                STRING_KEY_PURCHASES, thoth_gui.thoth_styleconstants.Image.PURCHASE, event -> {
                    openSubwindow(new IdentifiableListWindow(thoth.getTableName(AvaliableTables.PURCHASABLE), AvaliableTables.PURCHASABLE));
                }
        ));
//        menuButtons.add(getMenuButton(
//                STRING_KEY_PROJECTS, ThothGUI.thoth_styleconstants.Image.PROJECT, event -> {openSubwindow( new IdentifiableListWindow( thoth.getTableName(AvaliableTables.PROJECTABLE), AvaliableTables.PROJECTABLE) );}
//        ));
//        menuButtons.add(getMenuButton(
//                STRING_KEY_STORAGE, ThothGUI.thoth_styleconstants.Image.STORAGE_CELL, event -> {openSubwindow( new IdentifiableListWindow( thoth.getTableName(AvaliableTables.STORING), AvaliableTables.STORING) );}
//        ));
        menuButtons.add(getNavigationMenuButton(
                STRING_KEY_TABLES_GUIDE, thoth_gui.thoth_styleconstants.svg.List.getInstance(), event -> {
                    openSubwindow(new ListedListWindow(STRING_KEY_TABLES_GUIDE));
                }
        ));
        menuButtons.add(getNavigationMenuButton(
                "Продукты", thoth_gui.thoth_styleconstants.Image.PRODUCT, event -> {
                    openSubwindow(new IdentifiableListWindow(thoth.getTableName(AvaliableTables.STORAGABLE), AvaliableTables.STORAGABLE));
                }
        ));
        menu = new NavigationMenu(menuButtons);
//        menu.setMinifyButton();

        return menu;
    }

    private void styleConfig() {
        getStylesheets().addAll(
                getClass().getResource("/style/list.css").toExternalForm()
        );
    }

    private void workspaceConfig() {
        workspace = new StackPane();
        setCenter(workspace);
    }

    @Override
    public void openSubwindow(Subwindow subwindow) {
        ObservableList<Node> children = workspace.getChildren();
        Optional<Node> first = children.stream()
                .filter(node -> node.getId().equals(subwindow.getId()))
                .findFirst();

        if (!first.isPresent()) {
            workspace.getChildren().add(subwindow);
            subwindow.setCloseSubwindow(this::closeSubwindow);
        } else {
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
