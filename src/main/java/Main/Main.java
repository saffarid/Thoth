package Main;

import ThothGUI.Config.Config;
import ThothGUI.ThothLite.ThothLiteWindow;
import ThothCore.ThothLite.ThothLite;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import window.StageResizer;

import java.io.File;
import java.util.Currency;
import java.util.Locale;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
        config = Config.getInstance();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        ThothLiteWindow thoth = ThothLiteWindow.getInstance(stage, ThothLite.getInstance());
//        thoth.setPrefSize(ThothLiteWindow.DEFAULT_SIZE.WIDTH.getSize(), ThothLiteWindow.DEFAULT_SIZE.HEIGHT.getSize());
        thoth.setPrefSize(config.getWindow().getWidth(), config.getWindow().getHeight());
        stage.setScene(new Scene(thoth));

        stage.initStyle(StageStyle.UNDECORATED);

        stage.show();
        new StageResizer(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(-1);
    }
}
