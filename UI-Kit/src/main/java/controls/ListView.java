package controls;

import javafx.collections.ObservableList;
import javafx.scene.layout.Background;

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

        getStylesheets().addAll(
                getClass().getResource("style/controls/list-view.css").toExternalForm()
        );

    }
}
