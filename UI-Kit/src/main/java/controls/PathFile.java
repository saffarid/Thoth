package controls;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import layout.basepane.StackPane;

public class PathFile extends StackPane {

    private final String STYLE_CLASS_PATHFILE = "path-file";

    private Label path;

    private Button btn;

    public PathFile() {
        super();
        path = new Label();
        btn = new Button("...");

        getChildren().addAll(path, btn);

        getStylesheets().add(getClass().getResource("/style/controls/path_file.css").toExternalForm());
        getStyleClass().add(STYLE_CLASS_PATHFILE);

        StackPane.setAlignment(btn, Pos.CENTER_RIGHT);
    }

    public void setOnAction(EventHandler<ActionEvent> event){
        btn.setOnAction(event);
    }

    public StringProperty textProperty(){
        return path.textProperty();
    }
}
