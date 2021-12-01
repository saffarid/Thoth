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

import java.util.List;

/**
 * Навигационное меню.
 * */
public class NavigationMenu extends BorderPane {

    private final String STYLE_CLASS_NAV_MENU = "navigation-menu";
    private final String STYLE_CLASS_MINIFY_BTN = "minify-button";
    private final String STYLE_CLASS_TITLE = "title";
    private final String STYLE_CLASS_CONTENT = "content";

    private final double MAX_SIZE = 200;
    private final double MIN_SIZE = 40;

    private static Logger LOG;

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

    static {
        LOG = Logger.getLogger(NavigationMenu.class.getName());
    }

    public NavigationMenu( List<MenuButton> menuButtons ){
        super();
        init();

        setMinifyButton();
        for(MenuButton menuButton : menuButtons){
            content.getChildren().add(menuButton);
            menuButton.isMiniProperty().bind(isMinified);
        }
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

        title.setMaxWidth(MAX_SIZE);

        getStylesheets().add(getClass().getResource(Stylesheets.COLOR).toExternalForm());
        getStylesheets().add(getClass().getResource("/style/layout/panes/custom/navigation_menu.css").toExternalForm());
        getStyleClass().addAll(STYLE_CLASS_NAV_MENU, Styleclasses.DARK);
        content.getStyleClass().add(STYLE_CLASS_CONTENT);

        setMinWidth(MIN_SIZE);
        setPrefWidth(MAX_SIZE);
        setMaxWidth(MAX_SIZE);

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
    private void setMinifyButton(){
        ImageView imageView = new ImageView(
                new Image(getClass().getResourceAsStream("/image/icons/menu.png"), 30, 30, false, false)
        );
        minifyButton = new Button();
        minifyButton.setGraphic(imageView);
        minifyButton.setOnAction(this::minify);
        minifyButton.getStyleClass().add(STYLE_CLASS_MINIFY_BTN);

        title.setLeft(minifyButton);
    }


}
