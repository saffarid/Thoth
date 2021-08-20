package layout.title;

import controls.Button;
import controls.Label;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class TitleWithoutMenu extends Title{

    private Label title;

    public TitleWithoutMenu(String title) {
        super();

        init(title);
    }


    protected void init(String title){
        super.init();
        if(title != null) {
            this.title = new Label(title);
            setLeft(this.title);
        }
    }


}
