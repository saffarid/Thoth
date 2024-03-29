package layout.title;

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


    public TitleWithMenu() {
        super();
        init();
    }

    private void init(){
        fullSize = addButton(URL_MINIFY);
        minify = addButton(URL_MINIFY_TO_TASK_BAR);

        ((HBox)getRight()).getChildren().add(0,fullSize);
        ((HBox)getRight()).getChildren().add(0,minify);
    }

}
