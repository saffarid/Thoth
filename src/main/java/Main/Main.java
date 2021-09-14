package Main;

import ThothGUI.Guardkeeper.Guardkeeper;
import ThothGUI.Thoth.ThothWindow;
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
            ThothWindow thoth = new ThothWindow(stage, new ThothCore.Thoth.Thoth(db));
            stage.setScene(new Scene(thoth, 800, 600));
            new StageResizer(stage);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
