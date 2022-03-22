package main;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import thoth_core.thoth_lite.exceptions.DontSetSystemInfoException;
import thoth_core.thoth_lite.info.SystemInfoKeys;
import thoth_gui.GuiLogger;
import thoth_gui.config.Config;
import thoth_gui.thoth_lite.SystemInfoDialog;
import thoth_gui.thoth_lite.main_window.ThothLiteWindow;
import thoth_core.thoth_lite.ThothLite;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import thoth_gui.thoth_lite.tools.Properties;
import window.StageResizer;

import java.util.Currency;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class Main extends Application {

    private final Image image = new Image(Main.class.getResource("/img/splash.png").toExternalForm(), 640, 360, false, false);

    private final Scene splashScene = new Scene(
            new Pane(
                    new ImageView(
                            image
                    )
            )
    );

    private final SimpleObjectProperty<ThothLite> thothProperty = new SimpleObjectProperty<>();

    private Stage stage;
    private Config config;

    static {
        GuiLogger.log.trace("java.runtime.version - " + System.getProperty("java.runtime.version"));
        GuiLogger.log.trace("os.name - " + System.getProperty("os.name"));
        GuiLogger.log.trace("java.vm.name - " + System.getProperty("java.vm.name"));
        GuiLogger.log.trace("javafx.runtime.version - " + System.getProperty("javafx.runtime.version"));
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage)
            throws Exception {
        this.stage = stage;
        this.stage.setScene(splashScene);

        this.stage.initStyle(StageStyle.UNDECORATED);

        this.stage.show();
        CompletableFuture.runAsync(() -> {
            GuiLogger.log.info("Read config");
            config = Config.getInstance();
            Properties.loadProperties(config.getScene().getLocale());
        });
        thothProperty.addListener((observableValue, thothLite, t1) -> {
            if (t1 == null) return;
            Platform.runLater(() -> {
                GuiLogger.log.info("Scene create");
                ThothLiteWindow thoth = ThothLiteWindow.getInstance(stage);

                Scene scene = new Scene(
                        thoth
                        , config.getWindow().getWidthPrimary()
                        , config.getWindow().getHeightPrimary()
                );

                stage.sceneProperty().addListener((observableValue1, scene1, t11) -> {
                    //                                Установка начального положения
                    this.stage.setX(config.getWindow().getxPrimary());
                    this.stage.setY(config.getWindow().getyPrimary());
                    //                Установка минимальных размеров
                    this.stage.setMinWidth(config.getWindow().getWidthPrimaryMin());
                    this.stage.setMinHeight(config.getWindow().getHeightPrimaryMin());
                    //                Связываем свойства начальных положений
                    config.getWindow().xPrimaryProperty().bind(this.stage.xProperty());
                    config.getWindow().yPrimaryProperty().bind(this.stage.yProperty());
//                Связываем размеры окна
                    config.getWindow().widthPrimaryProperty().bind(this.stage.widthProperty());
                    config.getWindow().heightPrimaryProperty().bind(this.stage.heightProperty());
                });

                GuiLogger.log.info("Show scene");
                stage.setScene(scene);

                new StageResizer(stage);

            });
        });
        CompletableFuture.runAsync(() -> {
            try {
                this.thothProperty.setValue(ThothLite.getInstance());
            } catch (DontSetSystemInfoException e) {
                Platform.runLater(() -> {
                    Optional<HashMap<SystemInfoKeys, Object>> initialData = new SystemInfoDialog(stage, e.getMissingConfig()).showAndWait();
                    if (initialData.isPresent())
                        this.thothProperty.setValue(ThothLite.getInstance(initialData.get()));
                    else
                        Platform.exit();
                });
            }
        });


    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop()
            throws Exception {
        super.stop();
        GuiLogger.log.info("Export config");
        config.exportConfig();
        GuiLogger.log.info("Good bye my friend");
        System.exit(-1);
    }
}
