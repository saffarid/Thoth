package thoth_gui.thoth_lite.main_window;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.paint.Color;

import thoth_gui.thoth_lite.components.controls.MenuButton;
import thoth_gui.thoth_lite.components.scenes.FinancialOperations;
import thoth_gui.thoth_styleconstants.Stylesheets;
import tools.BackgroundWrapper;
import tools.BorderWrapper;
import tools.SvgWrapper;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_core.thoth_lite.ThothLite;
import thoth_gui.config.Config;
import thoth_gui.thoth_lite.Settings;
import thoth_gui.thoth_lite.components.scenes.ConfigDropdownList;
import thoth_gui.thoth_lite.components.scenes.Home;
import thoth_gui.thoth_lite.components.scenes.Scenes;
import thoth_gui.thoth_lite.components.scenes.db_elements_view.list_view.IdentifiablesListView;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.*;
import layout.basepane.VBox;
import layout.custompane.NavigationMenu;
import org.json.simple.parser.ParseException;
import thoth_gui.thoth_styleconstants.svg.*;
import window.PrimaryWindow;
import window.StageResizer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public class ThothLiteWindow
        extends PrimaryWindow {

    private Config config;

    public enum DEFAULT_SIZE {
        HEIGHT(600),
        WIDTH(900),
        WIDTH_LEFT_BLOCK(200),

        ;
        private double size;

        DEFAULT_SIZE(double size) {
            this.size = size;
        }

        public double getSize() {
            return size;
        }
    }

    private static Logger LOG;

    private static ThothLiteWindow window;

    private Stage mainStage;

    private NavigationMenu menu;

    private Workspace works;

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

        initStyle();
        menuConfig();
        setLeft(createLeftNode());
        workspaceConfig();
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

    private Node createLeftNode() {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(2));

        FinishableView finishableViewPurchase = getFinishableView();
        thoth.purchasesSubscribe(finishableViewPurchase);

        vBox.setBorder(
                new BorderWrapper()
                        .addRightBorder(1)
                        .setColor(Color.valueOf("#707070"))
                        .commit()
        );

        vBox.getChildren().addAll(
                navigationMenuConfig()
                , finishableViewPurchase
        );

        return vBox;
    }

    @Override
    public void close() {
        thoth.close();
        mainStage.close();
    }

    @Override
    protected void initStyle() {
        CompletableFuture.supplyAsync(() -> {
                    return new String[]{
                            Stylesheets.COLORS.getStylesheet(),
                            Stylesheets.LIST_VIEW.getStylesheet(),
                            Stylesheets.SCROLL_BAR.getStylesheet(),
                            Stylesheets.TITLE.getStylesheet(),
                            Stylesheets.WINDOW.getStylesheet(),
                    };
                })
                .thenAccept(s -> {
                    Platform.runLater(() -> {
                        try {
                            getStyleClass().add(
                                    Config.getInstance().getScene().getTheme().name().toLowerCase()
                            );
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        for (String s1 : s) {
                            getStylesheets().add(s1);
                        }
                    });
                });
    }

    private void menuConfig() {

        MenuItem config = new MenuItem("config");
        config.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN));
        config.setOnAction(event -> {
//            works.setNewScene(Settings.getInstance());
            works.setNewScene(new Settings());
        });

        MenuItem about = new MenuItem("about");

        MenuItem exit = new MenuItem("exit");
        exit.setOnAction(event -> close());
        exit.setAccelerator(new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN));


        title.addContextMenu(config, about, new SeparatorMenuItem(), exit);
    }

    private Node navigationMenuConfig() {
        List<controls.MenuButton> menuButtons = new LinkedList<>();
        menuButtons.add(MenuButton.getInstance(
                Scenes.HOME.getSceneCode(), SvgWrapper.getInstance(Images.HOME(), 20, 20),
                event -> works.setNewScene(Home.getInstance())
        ));
        menuButtons.add(MenuButton.getInstance(
                Scenes.EXPENSES.getSceneCode(), SvgWrapper.getInstance(Images.TRADINGDOWN(), 20, 20),
                event -> works.setNewScene(new FinancialOperations(AvaliableTables.EXPENSES))
        ));
        menuButtons.add(MenuButton.getInstance(
                Scenes.INCOMES.getSceneCode(), SvgWrapper.getInstance(Images.TRADINGUP(), 20, 20),
                event -> works.setNewScene(new FinancialOperations(AvaliableTables.INCOMES))
        ));
        menuButtons.add(MenuButton.getInstance(
                Scenes.PURCHASES.getSceneCode(), SvgWrapper.getInstance(Images.PURCHASE(), 20, 20),
                event -> works.setNewScene(IdentifiablesListView.getInstance(AvaliableTables.PURCHASABLE))
        ));
        menuButtons.add(MenuButton.getInstance(
                Scenes.STORAGABLE.getSceneCode(), SvgWrapper.getInstance(Images.PRODUCT(), 20, 20),
                event -> works.setNewScene(IdentifiablesListView.getInstance(AvaliableTables.STORAGABLE))
        ));
        menuButtons.add(MenuButton.getInstance(
                Scenes.SYSTEM.getSceneCode(), SvgWrapper.getInstance(Images.LIST(), 20, 20),
                event -> works.setNewScene(ConfigDropdownList.getInstance())
        ));
        menu = new NavigationMenu(menuButtons);
        menu.setWidth(DEFAULT_SIZE.WIDTH_LEFT_BLOCK.getSize());

        return menu;
    }

    private void workspaceConfig() {
        works = Workspace.getInstance();
        setCenter(works);
        works.setNewScene(Home.getInstance());
    }

    private FinishableView getFinishableView() {
        FinishableView res = new FinishableView();

        return res;
    }

}
