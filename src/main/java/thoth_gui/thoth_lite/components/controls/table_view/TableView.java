package thoth_gui.thoth_lite.components.controls.table_view;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.util.concurrent.CompletableFuture;

public class TableView<S>
        extends javafx.scene.control.TableView<S> {

    private final static String columnResizeLineKey = "column-resize-line";
    private final static String columnOverlayKey = "column-overlay";
    private final static String placeholderKey = "placeholder";
    private final static String columnHeaderBackgroundKey = "column-header-background";
    private final static String nestedColumnHeaderKey = "nested-column-header";
    private final static String fillerKey = "filler";
    private final static String showHideColumnsButtonKey = "show-hide-columns-button";
    private final static String showHideColumnsImageKey = "show-hide-columns-image";
    private final static String columnDragHeaderKey = "column-drag-header";

    private Region columnResizeLine;
    private Region columnOverlay;
    private StackPane placeholder;
    private StackPane columnHeaderBackground;
    private Node nestedColumnHeader;
    private Region filler;
    private StackPane showHideColumnsButton;
    private StackPane showHideColumnsImage;
    private StackPane columnDragHeader;

    public TableView() {
        super();
        init();
    }

    public TableView(ObservableList<S> observableList) {
        super(observableList);
        init();
    }

    private void init(){
        initStyle();
    }

    private void initStyle(){
        getChildrenUnmodifiable().addListener((ListChangeListener<? super Node>) change -> {
            CompletableFuture.runAsync(() -> {
                for(Node node : getChildrenUnmodifiable()){
                    if(node.getStyleClass().contains(columnResizeLineKey)){
                        columnResizeLine = (Region) node;
                    } else if(node.getStyleClass().contains(columnOverlayKey)){
                        columnOverlay = (Region) node;
                    } else if(node.getStyleClass().contains(placeholderKey)){
                        placeholder = (StackPane) node;
                    } else if(node.getStyleClass().contains(columnHeaderBackgroundKey)){
                        columnHeaderBackground = (StackPane) node;
                        for(Node node1 : columnHeaderBackground.getChildrenUnmodifiable()){
                            if(node1.getStyleClass().contains(nestedColumnHeaderKey)){
                                nestedColumnHeader = node;
                            } else if(node1.getStyleClass().contains(fillerKey)){
                                filler = (Region) node;
                            } else if(node1.getStyleClass().contains(showHideColumnsButtonKey)){
                                showHideColumnsButton = (StackPane) node;
                            } else if(node1.getStyleClass().contains(columnDragHeaderKey)){
                                columnDragHeader = (StackPane) node;
                            }
                        }
                    }
                }
                System.out.println("hello");
            });
        });
    }
}
