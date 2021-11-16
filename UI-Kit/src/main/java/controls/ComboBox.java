package controls;

import javafx.collections.ObservableList;

public class ComboBox<T> extends javafx.scene.control.ComboBox<T> {

    private final static String STYLESHEET_COMBO_BOX = "/style/controls/combo-box.css";
    private final static String STYLESHEET_LIST_VIEW = "/style/layout/panes/custom/list_view.css";

    public ComboBox() {
        super();
        init();
    }

    public ComboBox(ObservableList<T> observableList) {
        super(observableList);
        init();
    }

    private void init(){
        getStylesheets().addAll(
                getClass().getResource(STYLESHEET_COMBO_BOX).toExternalForm()
                , getClass().getResource(STYLESHEET_LIST_VIEW).toExternalForm()
        );
    }
}
