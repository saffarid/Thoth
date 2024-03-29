package layout.custompane;

import controls.Label;
import controls.ListCell;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import layout.basepane.BorderPane;
import styleconstants.Styleclasses;

import java.util.List;

public class ListPane<T> extends BorderPane {

    private final String STYLE_CLASS_LPANE  = "list-pane";
    private final String STYLE_CLASS_TITLE  = "title";


    private Label title;

    private ListView<T> list;

    public ListPane(String textTitle, List<T> items) {
        super();

        titleConfig(textTitle);
        listConfig(items);

        setTop(title);
        setCenter(list);

        getStylesheets().add(getClass().getResource("/style/layout/panes/custom/list_view.css").toExternalForm());
        getStyleClass().add(STYLE_CLASS_LPANE);

    }

    private void titleConfig(String textTitle){
        title = new Label(textTitle);
        title.setMaxWidth(Double.MAX_VALUE);
        title.getStyleClass().add(STYLE_CLASS_TITLE);
    }

    private void listConfig(List<T> items){
        list = new ListView<T>();
        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        list.getStyleClass().addAll(Styleclasses.BORDER, Styleclasses.DARK);
    }

    public ListView<T> getList(){
        return list;
    }
}
