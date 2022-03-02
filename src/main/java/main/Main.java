package main;

import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_gui.GuiLogger;
import thoth_gui.config.Config;
import thoth_gui.thoth_lite.main_window.ThothLiteWindow;
import thoth_core.thoth_lite.ThothLite;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.simple.parser.ParseException;
import window.StageResizer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class Main extends Application{

    private Stage stage;
    private Config config;

    static {
        GuiLogger.log.trace( "java.runtime.version - " + System.getProperty("java.runtime.version") );
        GuiLogger.log.trace( "os.name - " + System.getProperty("os.name") );
        GuiLogger.log.trace( "java.vm.name - " + System.getProperty("java.vm.name") );
        GuiLogger.log.trace( "javafx.runtime.version - " + System.getProperty("javafx.runtime.version") );
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage)
            throws Exception {
        this.stage = stage;

        this.stage.initStyle(StageStyle.UNDECORATED);
        this.stage.show();
        CompletableFuture.runAsync(() -> {
            GuiLogger.log.info("Read config");
            try {
                config = Config.getInstance();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        CompletableFuture.runAsync(() -> {
            try {
                GuiLogger.log.info("Init thoth-core");
                ThothLite.getInstance();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NotContainsException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).thenAccept(unused -> {
            Platform.runLater(() -> {

                GuiLogger.log.info("Scene create");
                ThothLiteWindow thoth = ThothLiteWindow.getInstance(stage);

                Scene scene = new Scene(
                        thoth
                        , config.getWindow().getWidthPrimary()
                        , config.getWindow().getHeightPrimary()
                );

                //Установка начального положения
                this.stage.setX( config.getWindow().getxPrimary() );
                this.stage.setY( config.getWindow().getyPrimary() );
                //Установка минимальных размеров
                this.stage.setMinWidth( config.getWindow().getWidthPrimaryMin() );
                this.stage.setMinHeight( config.getWindow().getHeightPrimaryMin() );
                //Связываем свойства начальных положений
                config.getWindow().xPrimaryProperty().bind( this.stage.xProperty() );
                config.getWindow().yPrimaryProperty().bind( this.stage.yProperty() );
                //Связываем размеры окна
                config.getWindow().widthPrimaryProperty().bind( this.stage.widthProperty() );
                config.getWindow().heightPrimaryProperty().bind( this.stage.heightProperty() );

                GuiLogger.log.info("Show scene");
                stage.setScene( scene );

//                stage.show();

                new StageResizer(stage);

            });
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
