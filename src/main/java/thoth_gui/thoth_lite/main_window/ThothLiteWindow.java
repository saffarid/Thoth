package thoth_gui.thoth_lite.main_window;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

import layout.basepane.GridPane;
import thoth_gui.GuiLogger;
import thoth_gui.thoth_lite.components.controls.MenuButton;
import thoth_gui.thoth_lite.components.scenes.FinancialOperations;
import thoth_gui.thoth_lite.tools.Properties;
import thoth_gui.thoth_lite.tools.TextCase;
import thoth_gui.thoth_styleconstants.Stylesheets;
import thoth_gui.config.ColorTheme;
import tools.BorderWrapper;
import tools.SvgWrapper;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_core.thoth_lite.ThothLite;
import thoth_gui.config.Config;
import thoth_gui.thoth_lite.components.scenes.Settings;
import thoth_gui.thoth_lite.components.scenes.ConfigDropdownList;
import thoth_gui.thoth_lite.components.scenes.Home;
import thoth_gui.thoth_lite.components.scenes.Scenes;
import thoth_gui.thoth_lite.components.scenes.db_elements_view.list_view.IdentifiablesListView;

import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.*;
import layout.custompane.NavigationMenu;
import thoth_gui.thoth_styleconstants.svg.*;
import window.PrimaryWindow;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ThothLiteWindow
        extends PrimaryWindow {

    private final String STYLE_CLASS_LEFT = "left";
    private Config config = Config.getInstance();

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

    private static ThothLiteWindow window;

    private final SimpleObjectProperty<ColorTheme> theme = new SimpleObjectProperty<>();

    private Stage mainStage;

//    private NavigationMenu menu;

    private Workspace works;

    private ThothLite thoth;

    private ThothLiteWindow(Stage stage) {
        super(stage);

        GuiLogger.log.info("Get thoth-core");
        this.thoth = ThothLite.getInstance();

        GuiLogger.log.info("Init thoth-core");

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
        GuiLogger.log.info("Create left node on main-scene");

        GridPane left = new GridPane()
                .addColumn(Priority.NEVER)
                .addRow(150., VPos.TOP)
                .addRow(Priority.ALWAYS);

        left.setPadding(new Insets(2));
        left.setVgap(2);
        left.getStyleClass().add(STYLE_CLASS_LEFT);

        left.setBorder(
                new BorderWrapper()
                        .addRightBorder(1)
                        .setColor(Color.valueOf("#707070"))
                        .commit()
        );

        left.add(navigationMenuConfig(), 0, 0);
        left.add(getFinishableView(), 0, 1);

        return left;
    }

    @Override
    public void close() {
        GuiLogger.log.info("Thoth-core close");
        thoth.close();
        GuiLogger.log.info("Window close");
        Platform.exit();
    }

    @Override
    protected void initStyle() {
//        CompletableFuture.runAsync(() -> {
                    GuiLogger.log.info("Theme init");
                    theme.addListener((observableValue, colorTheme, t1) -> {
                        if (t1 != null) {
//                            Platform.runLater(() -> {
                                if (colorTheme != null) getStyleClass().remove(colorTheme.getName().toLowerCase());
                                getStyleClass().add(t1.getName().toLowerCase());
//                            });
                        }
                    });
                    theme.bind(Config.getInstance().getScene().getThemeProperty());
//                })
//                .thenAccept(unused -> {
//                    Platform.runLater(() -> {
                        for (Stylesheets s1 : Stylesheets.values()) {
                            getStylesheets().add(s1.getStylesheet());
                        }
//                    });
//                });
    }

    private void menuConfig() {

        GuiLogger.log.info("Create menu");

        MenuItem config = new MenuItem(Properties.getString(Scenes.SETTINGS.name(), TextCase.NORMAL));
        config.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN));
        config.setOnAction(event -> {
            works.setNewScene(new Settings());
        });

        MenuItem about = new MenuItem(Properties.getString("about", TextCase.NORMAL));
        about.setOnAction(event -> {
            Stage aboutStage = new Stage();
            aboutStage.initStyle(StageStyle.UNDECORATED);
            aboutStage.setScene(
                    new Scene(
                            new About(aboutStage, Properties.getString("about", TextCase.NORMAL))
                    )
            );

            aboutStage.setX(mainStage.getX() + (mainStage.getWidth() / 2));
            aboutStage.setY(mainStage.getY() + (mainStage.getHeight() / 2));

            aboutStage.initModality(Modality.APPLICATION_MODAL);
            aboutStage.initOwner(stage);
            aboutStage.show();
        });

        MenuItem exit = new MenuItem(Properties.getString("exit", TextCase.NORMAL));
        exit.setOnAction(event -> close());
        exit.setAccelerator(new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN));


        title.addContextMenu(config, about, new SeparatorMenuItem(), exit);
    }

    private Node navigationMenuConfig() {
        GuiLogger.log.info("Create navigation menu");
        double size = 17;
        double sizeViewbox = 17;

        List<controls.MenuButton> menuButtons = new LinkedList<>();
//        menuButtons.add(MenuButton.getInstance(
//                Scenes.HOME.name(), SvgWrapper.getInstance(Images.HOME(), size, size),
//                event -> works.setNewScene(Home.getInstance())
//        ));
        menuButtons.add(MenuButton.getInstance(
                Scenes.EXPENSES.name(), SvgWrapper.getInstance(Images.TRADINGDOWN(), size, size),
                event -> works.setNewScene(new FinancialOperations(AvaliableTables.EXPENSES))
        ));
        menuButtons.add(MenuButton.getInstance(
                Scenes.INCOMES.name(), SvgWrapper.getInstance(Images.TRADINGUP(), size, size),
                event -> works.setNewScene(new FinancialOperations(AvaliableTables.INCOMES))
        ));
        menuButtons.add(MenuButton.getInstance(
                Scenes.PURCHASABLE.name(), SvgWrapper.getInstance(Images.PURCHASE(), size, size),
                event -> works.setNewScene(IdentifiablesListView.getInstance(AvaliableTables.PURCHASABLE))
        ));
        menuButtons.add(MenuButton.getInstance(
                Scenes.STORAGABLES.name(), SvgWrapper.getInstance(Images.PRODUCT(), size, size),
                event -> works.setNewScene(IdentifiablesListView.getInstance(AvaliableTables.STORAGABLE))
        ));
        menuButtons.add(MenuButton.getInstance(
                Scenes.SYSTEM_TABLE.name(), SvgWrapper.getInstance(Images.LIST(), size, size),
                event -> works.setNewScene(ConfigDropdownList.getInstance())
        ));
        NavigationMenu menu = new NavigationMenu(menuButtons);
        menu.setPadding(new Insets(0));
        menu.setWidth(DEFAULT_SIZE.WIDTH_LEFT_BLOCK.getSize());

        System.out.println(menu.getStyleClass());
        return menu;
    }

    private void workspaceConfig() {
        GuiLogger.log.info("Create workspace");
        works = Workspace.getInstance();
        setCenter(works);
        works.setNewScene(Home.getInstance());
    }

    private FinishableView getFinishableView() {
        FinishableView res = new FinishableView();
        thoth.purchasesSubscribe(res);
        return res;
    }

}
