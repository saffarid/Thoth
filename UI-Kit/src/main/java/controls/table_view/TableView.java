package controls.table_view;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.util.concurrent.CompletableFuture;

public class TableView extends javafx.scene.control.TableView {

    private enum Styleclass {
        COLUMN_RESIZE_LINE("column-resize-line"),
        COLUMN_OVERLAY("column-overlay"),
        PLACEHOLDER("placeholder"),
        COLUMN_HEADER_BACKGROUND("column-header-background"),
        NESTED_COLUMN_HEADER("nested-column-header"),
        FILLER("filler"),
        SHOW_HIDE_COLUMNS_BUTTON("show-hide-columns-button"),
        COLUMN_DRAG_HEADER("column-drag-header")
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
    private StackPane showHideColumnsButton;
    private StackPane columnDragHeader;

    public TableView() {
        super();
        init();
    }

    public TableView(ObservableList observableList) {
        super(observableList);
        init();
    }

    private void init(){
        getChildrenUnmodifiable().addListener((ListChangeListener<? super Node>) change -> {
            CompletableFuture.runAsync(() -> {
                for(Node node : getChildrenUnmodifiable()){

                    if(node.getStyleClass().contains(Styleclass.COLUMN_RESIZE_LINE.clazz)){
                        columnResizeLine = (Region) node;
                    } else if(node.getStyleClass().contains(Styleclass.COLUMN_OVERLAY.clazz)){
                        columnOverlay = (Region) node;
                    } else if(node.getStyleClass().contains(Styleclass.PLACEHOLDER.clazz)){
                        placeholder = (StackPane) node;
                    } else if(node.getStyleClass().contains(Styleclass.COLUMN_HEADER_BACKGROUND.clazz)){
                        columnHeaderBackground = (StackPane) node;
                        for(Node node1 : columnHeaderBackground.getChildrenUnmodifiable()){
                            if(node.getStyleClass().contains(Styleclass.SHOW_HIDE_COLUMNS_BUTTON.clazz)){
                                showHideColumnsButton = (StackPane) node1;
                            } else if(node.getStyleClass().contains(Styleclass.COLUMN_DRAG_HEADER.clazz)){
                                columnDragHeader = (StackPane) node1;
                            }
                        }
                    }

                }
            });
        });
    }

    private void initStyle(){
    }
}
