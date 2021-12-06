package layout.custompane;

import controls.Button;
import controls.Label;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import layout.basepane.BorderPane;
import layout.basepane.HBox;
import styleconstants.Images;

public class Title
        extends BorderPane {

    private enum STYLE_CLASS{
        TITLE("title"),
        CLOSE("close")
        ;

        private String styleClass;
        STYLE_CLASS(String styleClass) {
            this.styleClass = styleClass;
        }
        public String getStyleClass() {
            return styleClass;
        }
    }

    protected double switchSceneX;
    protected double switchSceneY;

    private Button close;
    private Button minify;
    private Button iconify;

    private HBox controls;

    private Label title;

    private MenuBar contextMenu;
    private MenuBar menu;
    private Menu iconMenu;

    public Title() {
        super();
        controls = new HBox();
        contextMenu = new MenuBar();
        menu = new MenuBar();

        title = new Label();

        setRight(controls);
        init();
    }

    public Title addClose(EventHandler<ActionEvent> event){
        close = getButton(Images.URL_CLOSE.getUrl());
        close.setOnAction(event);
        close.getStyleClass().add(STYLE_CLASS.CLOSE.getStyleClass());
        controls.getChildren().add(close);
        return this;
    }

    /**
     * Функция добавляет простое меню в виде иконки с тремя точками.
     * @param es пункты меню.
     * */
    public Title addContextMenu(MenuItem... es){
        iconMenu = new Menu();
        iconMenu.setGraphic(
                new ImageView(
                        new Image(
                                getClass().getResource(Images.THREE_POINT_H.getUrl()).toExternalForm(),
                                15, 15,
                                true, true
                        )
                )
        );
        iconMenu.getItems().setAll(es);
        contextMenu.getMenus().add(iconMenu);

        setLeft(contextMenu);
        return this;
    }

    public Title addIconify(EventHandler<ActionEvent> event){
        iconify = getButton(Images.URL_MINIFY_TO_TASK_BAR.getUrl());
        iconify.setOnAction(event);
        controls.getChildren().add(iconify);
        return this;
    }

    /**
     * Функция добавляет полосное меню в Title
     * @param menus разделы меню для добавления.
     * */
    public Title addMenu(Menu... menus){
        menu.getMenus().setAll(menus);
        setCenter(menu);
        return this;
    }

    public Title addMinify(EventHandler<ActionEvent> event){
        minify = getButton(Images.URL_SQUARE.getUrl());
        minify.setOnAction(event);
        controls.getChildren().add(minify);
        return this;
    }

    public Title addText(String text){
        title.setText(text);
        setCenter(title);
        return this;
    }

    public Button getButton(String url){
        return new Button(
                new ImageView(
                        new Image(
                                getClass().getResource(url).toExternalForm(),
                                25, 25,
                                true, true
                        )
                )
        );
    }

    public double getSwitchSceneX() {
        return switchSceneX;
    }

    public double getSwitchSceneY() {
        return switchSceneY;
    }

    public void init(){
        getStyleClass().add(STYLE_CLASS.TITLE.getStyleClass());

        getStylesheets().add(getClass().getResource("/style/layout/title/title.css").toExternalForm());
    }

    public void setSwitchSceneX(double switchSceneX) {
        this.switchSceneX = switchSceneX;
    }

    public void setSwitchSceneY(double switchSceneY) {
        this.switchSceneY = switchSceneY;
    }
}
