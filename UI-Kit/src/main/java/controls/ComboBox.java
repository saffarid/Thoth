package controls;

import javafx.collections.ObservableList;

public class ComboBox<T> extends javafx.scene.control.ComboBox<T> {


    public ComboBox() {
        super();
        init();
    }

    public ComboBox(ObservableList<T> observableList) {
        super(observableList);
        init();
    }

    private void init(){
    }
}
