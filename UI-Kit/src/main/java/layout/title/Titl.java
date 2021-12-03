package layout.title;

import controls.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import layout.basepane.BorderPane;
import layout.basepane.HBox;
import styleconstants.Images;

public class Titl
        extends BorderPane {

    private final String STYLE_CLASS_TITLE = "title";
    private final String STYLE_CLASS_CLOSE = "close";

    private Button close;
    private Button minify;
    private Button iconify;

    private HBox controls;

    private MenuBar contextMenu;
    private MenuBar menu;

    private Menu iconMenu;

    public Titl() {
        super();
        controls = new HBox();
        contextMenu = new MenuBar();
        menu = new MenuBar();

        setRight(controls);
    }

    /**
     * Функция добавляет простое меню в виде иконки с тремя точками.
     * @param es пункты меню.
     * */
    public Titl addContextMenu(MenuItem... es){
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

    public Titl addClose(EventHandler<ActionEvent> event){
        close = new Button(
                new ImageView(
                        new Image(
                                getClass().getResource(Images.URL_CLOSE.getUrl()).toExternalForm(),
                                15, 15,
                                true, true
                        )
                )
        );
        close.setOnAction(event);
        controls.getChildren().add(close);
        return this;
    }

    /**
     * Функция добавляет полосное меню в Title
     * @param menus разделы меню для добавления.
     * */
    public Titl addMenu(Menu... menus){
        menu.getMenus().setAll(menus);
        setCenter(menu);
        return this;
    }

    public Titl abbMinify(EventHandler<ActionEvent> event){
        minify = new Button(
                new ImageView(
                        new Image(
                                getClass().getResource(Images.URL_CLOSE.getUrl()).toExternalForm(),
                                15, 15,
                                true, true
                        )
                )
        );
        minify.setOnAction(event);
        controls.getChildren().add(minify);
        return this;
    }

    public Titl abbIconify(EventHandler<ActionEvent> event){
        iconify = new Button(
                new ImageView(
                        new Image(
                                getClass().getResource(Images.URL_CLOSE.getUrl()).toExternalForm(),
                                15, 15,
                                true, true
                        )
                )
        );
        iconify.setOnAction(event);
        controls.getChildren().add(iconify);
        return this;
    }
}
