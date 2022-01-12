package layout.custompane;

import java.util.logging.*;
import controls.MenuButton;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import layout.basepane.ScrollPane;
import styleconstants.Stylesheets;
import styleconstants.Styleclasses;
import styleconstants.imagesvg.ThreeLineV;

import java.util.List;

/**
 * Навигационное меню.
 * */
public class NavigationMenu
        extends BorderPane {

    private final String STYLE_CLASS_NAV_MENU = "navigation-menu";
    private final String STYLE_CLASS_MINIFY_BTN = "minify-button";
    private final String STYLE_CLASS_TITLE = "title";
    private final String STYLE_CLASS_CONTENT = "content";

    private final double MAX_SIZE = 200;
    private final double MIN_SIZE = 40;

    private static Logger LOG;

    static {
        LOG = Logger.getLogger(NavigationMenu.class.getName());
    }

    /**
     * Флаг отслеживания отображения навигационного меню.
     * True - меню отображается в свернутом виде, false - в развернутом.
     * */
    private SimpleBooleanProperty isMinified;

    /**
     * Кнопка минификации навигационного меню
     * */
    private Button minifyButton;

    /**
     * Заголовок навигационного меню
     * */
    private BorderPane title;

    /**
     * Содержимое навигационного меню
     * */
    private VBox content;

    public NavigationMenu(MenuButton... menuButtons){
        super();
        init();
        content.getChildren().addAll(menuButtons);
    }

    public NavigationMenu( List<MenuButton> menuButtons ){
        super();
        init();

        for(MenuButton menuButton : menuButtons){
            content.getChildren().add(menuButton);
            menuButton.isMiniProperty().bind(isMinified);
        }
    }

    private void addDefaultStyle(){

    }

    /**
     * Общая инициализация контейнера.
     * */
    private void init() {
        title = new BorderPane();
        content = new VBox();
        ScrollPane scrollPane = new ScrollPane();
        isMinified = new SimpleBooleanProperty(false);

        scrollPane.setContent(content);
        setTop(this.title);
        setCenter(scrollPane);
        scrollPane.setFitToWidth(true);

        getStylesheets().add(NavigationMenu.class.getResource(Stylesheets.COLOR).toExternalForm());
        getStylesheets().add(NavigationMenu.class.getResource("/style/layout/panes/custom/navigation_menu.css").toExternalForm());
        getStyleClass().addAll(STYLE_CLASS_NAV_MENU, Styleclasses.DARK);
        content.getStyleClass().add(STYLE_CLASS_CONTENT);

        setWidth(MAX_SIZE);
    }

    public void setWidth(double width){
        title.setMaxWidth(width);
        setMinWidth(width);
        setPrefWidth(width);
        setMaxWidth(width);
    }

    /**
     * Обработчик нажатия на кнопку минификации навигационного меню
     * */
    private void minify(ActionEvent event) {
        LOG.log(Level.INFO, "minify Click");
        isMinified.set(!isMinified.get());
        if(isMinified.getValue()){
            setPrefWidth(MIN_SIZE);
        }else {
            setPrefWidth(MAX_SIZE);
        }
    }

    /**
     * Функция инициализирует кнопку минификации, добавляет на панель и прикрепляет слушателя.
     * */
    public void setMinifyButton(){
        minifyButton = new Button();
        minifyButton.setGraphic(ThreeLineV.getInstance());
        minifyButton.setOnAction(this::minify);
        minifyButton.getStyleClass().add(STYLE_CLASS_MINIFY_BTN);

        title.setLeft(minifyButton);
    }

}
