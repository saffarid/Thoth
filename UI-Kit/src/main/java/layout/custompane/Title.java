package layout.custompane;

import controls.Button;
import controls.Label;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.paint.Color;
import tools.BackgroundWrapper;
import tools.BorderWrapper;
import layout.basepane.BorderPane;
import layout.basepane.HBox;
import styleconstants.Stylesheets;
import styleconstants.imagesvg.*;
import tools.SvgWrapper;

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

    private enum ButtonSize{
        HEIGHT(20),
        WIDTH(20),
        VIEWBOX_HEIGHT(25),
        VIEWBOX_WIDTH(25)
        ;
        private double size;
        ButtonSize(double size) {
            this.size = size;
        }
    }

    protected double switchSceneX;
    protected double switchSceneY;
    private SimpleBooleanProperty isMinify;

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
        close = getButton( SvgWrapper.getInstance(Images.CLOSE(), ButtonSize.WIDTH.size, ButtonSize.HEIGHT.size, ButtonSize.VIEWBOX_WIDTH.size, ButtonSize.VIEWBOX_HEIGHT.size) );
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

        iconMenu.setGraphic( SvgWrapper.getInstance(Images.THREEPOINTH(), ButtonSize.WIDTH.size, ButtonSize.HEIGHT.size, ButtonSize.VIEWBOX_WIDTH.size, ButtonSize.VIEWBOX_HEIGHT.size) );

        iconMenu.getItems().setAll(es);
        contextMenu.getMenus().add(iconMenu);

        setAlignment(contextMenu, Pos.CENTER);
        setLeft(contextMenu);

        return this;
    }

    public Title addIconify(EventHandler<ActionEvent> event){
        iconify = getButton( SvgWrapper.getInstance(Images.ICONIFY(), ButtonSize.WIDTH.size, ButtonSize.HEIGHT.size, ButtonSize.VIEWBOX_WIDTH.size, ButtonSize.VIEWBOX_HEIGHT.size) );
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

    public Title addMinify(
            EventHandler<ActionEvent> event
            , ReadOnlyBooleanProperty isMinify
    ){
        Node maximizeSvg = SvgWrapper.getInstance(Images.MAXIMIZE(), ButtonSize.WIDTH.size, ButtonSize.HEIGHT.size, ButtonSize.VIEWBOX_WIDTH.size, ButtonSize.VIEWBOX_HEIGHT.size);
        Node minifySvg = SvgWrapper.getInstance(Images.MINIFY(), ButtonSize.WIDTH.size, ButtonSize.HEIGHT.size, ButtonSize.VIEWBOX_WIDTH.size, ButtonSize.VIEWBOX_HEIGHT.size);
        this.minify = getButton(maximizeSvg);
        this.minify.setOnAction(event);
        controls.getChildren().add(minify);
        this.isMinify = new SimpleBooleanProperty(true);
        this.isMinify.bind(isMinify);

        this.isMinify.addListener((observableValue, aBoolean, t1) -> {
            if(t1){
                this.minify.setGraphic(minifySvg);
            }else{
                this.minify.setGraphic(maximizeSvg);
            }
        });

        return this;
    }

    public Title addText(String text){
        setAlignment(title, Pos.CENTER_LEFT);
        title.setText(text);
        setCenter(title);
        return this;
    }

    public Button getButton(Node node){
        return new Button(node);
    }

    public Button getButton(String url){
        return new Button(
                getImageView(url)
        );
    }

    protected ImageView getImageView(String url){
        return new ImageView(
                new Image(
                        getClass().getResource(url).toExternalForm(),
                        25, 25,
                        true, true
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
    }

    public void setSwitchSceneX(double switchSceneX) {
        this.switchSceneX = switchSceneX;
    }

    public void setSwitchSceneY(double switchSceneY) {
        this.switchSceneY = switchSceneY;
    }

    public void bindMinify(SimpleBooleanProperty isMinify){
        this.isMinify.bind(isMinify);
    }

}
