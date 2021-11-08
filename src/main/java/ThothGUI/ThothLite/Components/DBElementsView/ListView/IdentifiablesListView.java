package ThothGUI.ThothLite.Components.DBElementsView.ListView;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.*;
import ThothCore.ThothLite.DataType;
import ThothGUI.ThothLite.Components.DBElementsView.ListCell.IdentifiableListCell;
import controls.Label;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

import javafx.scene.layout.BorderPane;
import layout.basepane.HBox;
import layout.basepane.VBox;

import java.util.List;

public abstract class IdentifiablesListView<T extends Identifiable>
        extends BorderPane {

    private static final String STYLESHEET_PATH = "/style/identifiable-list.css";

    protected HBox sortedPane;
    protected List<T> datas;
    protected ListView<T> identifiableElementList;

    protected IdentifiablesListView(List<T> datas) {
        super();
        this.datas = datas;
        setTop(createSortedPane());
        setCenter(createContent());

        getStylesheets().add(getClass().getResource(STYLESHEET_PATH).toExternalForm());
    }

    private HBox createSortedPane(){
        sortedPane = new HBox();

        sortedPane.setPadding(new Insets(5));
        sortedPane.setSpacing(2);

        Label sortLabel = new Label("Сортировка:");
        ComboBox<String> sortBox = new ComboBox<>();

        sortedPane.getChildren().addAll(
                sortLabel
                , sortBox
        );

        return sortedPane;
    }

    private VBox createContent(){
        VBox vBox = new VBox();

        vBox.setPadding(new Insets(5));

        Label haventItemsLabel = new Label("В списке нет элементов");
        haventItemsLabel.setAlignment(Pos.CENTER);
        vBox.setFillWidth(true);
        vBox.setAlignment(Pos.CENTER);

        vBox.getChildren().setAll(
                haventItemsLabel
                , createListView()
        );

        haventItemsLabel.visibleProperty().bind(new SimpleBooleanProperty(identifiableElementList.getItems().isEmpty()));
        identifiableElementList.visibleProperty().bind(new SimpleBooleanProperty(!identifiableElementList.getItems().isEmpty()));

        return vBox;
    }

    protected ListView<T> createListView(){
        identifiableElementList = new ListView<>();
        identifiableElementList.setPadding(new Insets(2));
        identifiableElementList.getItems().setAll(datas);
        identifiableElementList.setCellFactory(tListView -> new IdentifiableListCell());
        return identifiableElementList;
    }

    public static IdentifiablesListView getInstance(
            DataType type
            , List<? extends Identifiable> datas
    ){
        switch (type){
            case ORDER:{
                return new OrderableListView((List<Orderable>) datas);
            }
            case PRODUCT:{
                return new StoragableListView((List<Storagable>) datas);
            }
            case PROJECT:{
                return new ProjectableListView((List<Projectable>) datas);
            }
            case PURCHASE:{
                return new PurchasableListView((List<Purchasable>) datas);
            }
            case STORING:{
                return new StoringListView((List<Storing>) datas);
            }
            default: return null;
        }
    }
}
