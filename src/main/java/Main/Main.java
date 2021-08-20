package Main;

import ThothGUI.Guardkeeper.Guardkeeper;
import controls.Label;
import controls.MenuButton;
import controls.TextField;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import layout.pane.NavigationMenu;
import layout.title.TitleWithMenu;
import layout.title.TitleWithoutMenu;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Guardkeeper root = new Guardkeeper();



        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }


}
