package Main;

import ThothGUI.Guardkeeper.Guardkeeper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import window.StageResizer;

import java.util.logging.Logger;

public class Main extends Application {

    public static Logger LOG;

    static {
        LOG = Logger.getLogger("Thoth");
    }

    @Override
    public void start(Stage stage) throws Exception {
        Guardkeeper root = new Guardkeeper(stage);

        Scene scene = new Scene(root, 700, 500);
        stage.setScene(scene);

        stage.initStyle(StageStyle.UNDECORATED);

        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }


}
