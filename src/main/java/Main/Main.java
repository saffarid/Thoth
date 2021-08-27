package Main;

import ThothGUI.Guardkeeper.Guardkeeper;
import ThothGUI.Main.MainWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import window.StageResizer;

import java.util.logging.Logger;

public class Main extends Application implements ChangeScreen{

    public static Logger LOG;

    private Stage stage;

    static {
        LOG = Logger.getLogger("Thoth");
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        Guardkeeper root = new Guardkeeper(stage, this::changeScreen);

        Scene scene = new Scene(root, 700, 500);
        stage.setScene(scene);

        stage.initStyle(StageStyle.UNDECORATED);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void changeScreen() {
        MainWindow mainWindow = new MainWindow(stage);
        stage.setScene(new Scene(mainWindow, 800, 600));
        new StageResizer(stage);
    }
}
