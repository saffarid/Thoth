package layout.custompane;

import controls.Button;
import controls.Label;
import controls.MenuButton;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import layout.basepane.BorderPane;
import layout.basepane.ScrollPane;
import layout.basepane.VBox;

import java.util.List;

/**
 * Навигационное меню.
 * */
public class NavigationMenu extends BorderPane {

    private final String STYLE_CLASS_NAV_MENU = "navigation-menu";
    private final String STYLE_CLASS_MINIFY_BTN = "minify-button";
    private final String STYLE_CLASS_TITLE = "title";
    private final String STYLE_CLASS_CONTENT = "content";

    private final double MAX_SIZE = 270;
    private final double MIN_SIZE = 50;


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

    public NavigationMenu(String title, boolean hasMinifyButton, List<MenuButton> menuButtons){
        super();
        init();

        setTitleText(title);
        if(hasMinifyButton) setMinifyButton();
        content.getChildren().setAll(menuButtons);
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

        getStylesheets().add(getClass().getResource("/style/layout/panes/custom/navigation_menu.css").toExternalForm());
        getStyleClass().add(STYLE_CLASS_NAV_MENU);
        content.getStyleClass().add(STYLE_CLASS_CONTENT);
        title.getStyleClass().add(STYLE_CLASS_TITLE);

    }

    /**
     * Обработчик нажатия на кнопку минификации навигационного меню
     * */
    private void minify(ActionEvent event) {
        isMinified.set(!isMinified.get());
    }

    /**
     * Функция инициализирует кнопку минификации, добавляет на панель и прикрепляет слушателя.
     * */
    private void setMinifyButton(){
        ImageView imageView = new ImageView(
                new Image(getClass().getResourceAsStream("/image/icons/menu.png"), 40, 40, false, false)
        );
        minifyButton = new Button(imageView);
        title.setLeft(minifyButton);
        minifyButton.setOnAction(this::minify);
        minifyButton.getStyleClass().add(STYLE_CLASS_MINIFY_BTN);
    }

    private void setTitleText(String title){
        this.title.setCenter(new Label(title));
    }

}
