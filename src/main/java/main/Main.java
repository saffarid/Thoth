package main;

import thoth_core.thoth_lite.exceptions.NotContainsException;
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
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application{

    public static Logger LOG;
    private Stage stage;

    private Config config;

    static {
        LOG = Logger.getLogger("Thoth");
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        this.stage.initStyle(StageStyle.UNDECORATED);
        this.stage.show();
        CompletableFuture.runAsync(() -> {
            try {
                ThothLite.getInstance();
                config = Config.getInstance();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NotContainsException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }).thenAccept(unused -> {

            Platform.runLater(() -> {

                LOG.log(Level.INFO, "start");
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
    public void stop() throws Exception {
        super.stop();
        config.exportConfig();
        System.exit(-1);
    }
}
