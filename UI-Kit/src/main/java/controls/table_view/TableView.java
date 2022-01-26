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

    private enum Styleclass {
        COLUMN_RESIZE_LINE("column-resize-line"),
        COLUMN_OVERLAY("column-overlay"),
        PLACEHOLDER("placeholder"),
        COLUMN_HEADER_BACKGROUND("column-header-background"),
        NESTED_COLUMN_HEADER("nested-column-header"),
        FILLER("filler"),
        SHOW_HIDE_COLUMNS_BUTTON("show-hide-columns-button"),
        COLUMN_DRAG_HEADER("column-drag-header"),
        COLUMN_HEADER("column_header")
        ;
        private String clazz;

        Styleclass(String clazz) {
            this.clazz = clazz;
        }
    }

    private Region columnResizeLine;
    private Region columnOverlay;
    private StackPane placeholder;
    private StackPane columnHeaderBackground;
    private Region filler;
    private StackPane showHideColumnsButton;
    private StackPane columnDragHeader;
    private NestedTableColumnHeader nestedTableColumnHeader;
    private TableColumnHeader columnHeader;

    public TableView() {
        super();
        init();
    }

    public TableView(ObservableList observableList) {
        super(observableList);
        init();
    }

    private void init() {
        getChildrenUnmodifiable().addListener((ListChangeListener<? super Node>) change -> {
            CompletableFuture.runAsync(() -> {
                for (Node node : getChildrenUnmodifiable()) {

                    if (node.getStyleClass().contains(Styleclass.COLUMN_RESIZE_LINE.clazz)) {
                        columnResizeLine = (Region) node;
                    } else if (node.getStyleClass().contains(Styleclass.COLUMN_OVERLAY.clazz)) {
                        columnOverlay = (Region) node;
                    } else if (node.getStyleClass().contains(Styleclass.PLACEHOLDER.clazz)) {
                        placeholder = (StackPane) node;
                    } else if (node.getStyleClass().contains(Styleclass.COLUMN_HEADER_BACKGROUND.clazz)) {
                        columnHeaderBackground = (StackPane) node;
                        for (Node node1 : columnHeaderBackground.getChildrenUnmodifiable()) {
                            if (node1.getStyleClass().contains(Styleclass.SHOW_HIDE_COLUMNS_BUTTON.clazz)) {
                                showHideColumnsButton = (StackPane) node1;
                            } else if (node1.getStyleClass().contains(Styleclass.COLUMN_DRAG_HEADER.clazz)) {
                                columnDragHeader = (StackPane) node1;
                            } else if (node1.getStyleClass().contains(Styleclass.FILLER.clazz)) {
                                filler = (Region) node1;
                            } else if (node1.getStyleClass().contains(Styleclass.NESTED_COLUMN_HEADER.clazz)) {
                                nestedTableColumnHeader = (NestedTableColumnHeader) node1;
                                for(TableColumnHeader header : nestedTableColumnHeader.getColumnHeaders()){
                                    if(header.getStyleClass().contains(Styleclass.COLUMN_HEADER.clazz)){
                                        columnHeader = header;
                                    }
                                }
                            }
                        }
                    }

                }
            }).thenAccept(unused -> {
                Platform.runLater(() -> {
                });
            });
        });
        initStyle();
    }

    private void initStyle() {
        setBackground(
                new BackgroundWrapper()
                        .setColor(Color.TRANSPARENT)
                        .commit()
        );
    }
}
