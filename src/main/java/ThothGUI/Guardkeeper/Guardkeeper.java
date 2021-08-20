package ThothGUI.Guardkeeper;

import javafx.scene.layout.BorderPane;
import layout.pane.NavigationMenu;
import layout.title.TitleWithoutMenu;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

public class Guardkeeper extends BorderPane {


    /**
     *
     * */
    private TitleWithoutMenu title;

    /**
     * Навигационное меню. В него записываются пользовательские БД
     * */
    private NavigationMenu navigationMenu;

    private ThothCore.Guardkeeper.Guardkeeper guardkeeper;


    public Guardkeeper() throws SQLException, ClassNotFoundException {
        title = new TitleWithoutMenu("Thoth");
        navigationMenu = new NavigationMenu("Доступные БД", false, new LinkedList<>());

//        CompletableFuture.

        guardkeeper = new ThothCore.Guardkeeper.Guardkeeper();

        setTop(title);
        setLeft(navigationMenu);
    }
}
