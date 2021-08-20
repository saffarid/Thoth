package layout.title;

import controls.Button;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

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

    public TitleWithMenu(Node node) {
        super(node);

        init();
    }

    public TitleWithMenu(Node node, Node node1, Node node2, Node node3, Node node4) {
        super(node, node1, node2, node3, node4);

        init();
    }

    @Override
    protected void init(){
        super.init();
        fullSize = addButton(URL_MINIFY);
        minify = addButton(URL_MINIFY_TO_TASK_BAR);

        ((HBox)getRight()).getChildren().add(0,fullSize);
        ((HBox)getRight()).getChildren().add(0,minify);
    }



}
