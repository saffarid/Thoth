package layout.title;

import controls.Label;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TitleSubwindow extends Title {

    /**
     * Кнопка развернуть на весь экран
     * */
    private Button fullSize;

    /**
     * Кнопка свернуть окно
     * */
    private Button minify;

    private Label title;

    public TitleSubwindow(String title) {
        super();
        init(title);
    }

    private void init(String title){
        if(title != null) {
            this.title = new Label(title);
            setLeft(this.title);
        }

        fullSize = addButton(URL_MINIFY);
        minify = addButton(URL_MINIFY_TO_TASK_BAR);

        ((HBox)getRight()).getChildren().add(0,fullSize);
        ((HBox)getRight()).getChildren().add(0,minify);
    }



}
