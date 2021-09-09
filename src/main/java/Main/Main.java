package Main;

import ThothCore.Thoth.Thoth;
import ThothGUI.Guardkeeper.Guardkeeper;
import ThothGUI.Main.MainWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import window.StageResizer;

import java.io.File;
import java.sql.SQLException;
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
    public void changeScreen(File db) {
        try {
            MainWindow mainWindow = new MainWindow(stage, new Thoth(db));
            stage.setScene(new Scene(mainWindow, 800, 600));
            new StageResizer(stage);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
