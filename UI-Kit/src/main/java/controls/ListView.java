package controls;

import javafx.collections.ObservableList;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import layout.BackgroundWrapper;

public class ListView<T>
        extends javafx.scene.control.ListView<T> {

    public ListView() {
        super();
        init();
    }

    public ListView(ObservableList<T> observableList) {
        super(observableList);
        init();
    }

    private void init(){
        getStylesheets().add(
                getClass().getResource("/style/controls/list-view.css").toExternalForm()
        );

        setBackground(
                new BackgroundWrapper()
                        .setColor(Color.TRANSPARENT)
                        .commit()
        );

    }
}
