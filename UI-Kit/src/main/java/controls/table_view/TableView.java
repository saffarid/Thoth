package controls.table_view;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.skin.NestedTableColumnHeader;
import javafx.scene.control.skin.TableColumnHeader;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import tools.BackgroundWrapper;
import tools.BorderWrapper;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TableView<S>
        extends javafx.scene.control.TableView<S> {

    public TableView() {
        super();
        init();
    }

    public TableView(ObservableList observableList) {
        super(observableList);
        init();
    }

    private void init() {
        initStyle();
    }

    private void initStyle() {
    }

}
