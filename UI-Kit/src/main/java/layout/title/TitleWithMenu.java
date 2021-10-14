package layout.title;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TitleWithMenu extends Title{

    /**
     * Кнопка развернуть на весь экран
     * */
    private Button fullSize;

    /**
     * Кнопка свернуть окно
     * */
    private Button minify;


    public TitleWithMenu(Stage stage) {
        super();
        init(stage);
    }

    private void init(Stage stage){
        fullSize = addButton(URL_MINIFY);
        minify = addButton(URL_MINIFY_TO_TASK_BAR);

        ((HBox)getRight()).getChildren().add(0,fullSize);
        ((HBox)getRight()).getChildren().add(0,minify);

        minify.setOnAction(event -> {
            stage.setIconified(true);
        });

        fullSize.setOnAction(event -> {
            boolean maximized = stage.isMaximized();
            if(maximized){
                stage.setMaximized(false);
            }else{
                stage.setMaximized(true);
            }
        });
    }

}
