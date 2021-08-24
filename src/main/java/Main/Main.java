package Main;

import ThothGUI.Guardkeeper.Guardkeeper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import window.StageResizer;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Guardkeeper root = new Guardkeeper(stage);

        stage.initStyle(StageStyle.UNDECORATED);

        Scene scene = new Scene(root, 700, 500);
        stage.setScene(scene);
        stage.show();

        new StageResizer(stage);
    }
    public static void main(String[] args) {
        launch(args);
    }


}
