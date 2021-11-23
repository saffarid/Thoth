package ThothGUI.ThothLite.Components.DBElementsView.ListView;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.*;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothCore.ThothLite.Exceptions.NotContainsException;
import ThothCore.ThothLite.ThothLite;
import ThothGUI.OpenSubwindow;
import ThothGUI.ThothLite.Components.DBElementsView.IdentifiableCard.CompositeListView;
import ThothGUI.ThothLite.Components.DBElementsView.ListCell.IdentifiableListCell;
import ThothGUI.ThothLite.Subwindows.IdentifiableCardWindow;
import ThothGUI.ThothLite.ThothLiteWindow;
import ThothGUI.thoth_styleconstants.Stylesheets;
import controls.Button;
import controls.Label;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import layout.basepane.HBox;
import layout.basepane.VBox;
import ThothGUI.thoth_styleconstants.Image;

import java.sql.SQLException;
import java.util.List;

public abstract class IdentifiablesListView<T extends Identifiable>
        extends BorderPane {

    protected enum Ids {
        IDENTIFIABLE_ADD("add"),
        SORTED_BOX("sort_box");
        private String id;
        Ids(String id) {
            this.id = id;
        }
        public String toString(){
            return id;
        }
    }

    private static final String STYLESHEET_PATH = Stylesheets.IDENTIFIABLE_LIST;
    protected AvaliableTables table;

    protected BorderPane pallete;
    protected HBox sortedPane;
    protected List<T> datas;
    protected ListView<T> identifiableElementList;

    protected IdentifiablesListView(List<T> datas) {
        super();
        this.datas = datas;
        setCenter(createContent());
        setTop(createPallete());

        getStylesheets().add(getClass().getResource(STYLESHEET_PATH).toExternalForm());
    }

    protected Node createPallete(){

        pallete = new BorderPane();

        pallete.setLeft(createSortedPane());
        pallete.setRight(getButton(Ids.IDENTIFIABLE_ADD, this::openCreateNewIdentifiable ));

        return pallete;
    }

    protected Node createSortedPane(){
        sortedPane = new HBox();

        sortedPane.setPadding(new Insets(5));
        sortedPane.setSpacing(2);

        Label sortLabel = new Label("Сортировка:");
        ComboBox sortBox = new ComboBox<>();
        sortBox.setId(Ids.SORTED_BOX.id);

        sortedPane.getChildren().addAll(
                sortLabel
                , sortBox
        );

        return sortedPane;
    }

    protected VBox createContent(){
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
        identifiableElementList.setCellFactory(tListView -> new IdentifiableListCell(table));
        return identifiableElementList;
    }

    private Button getButton(
            Ids id
            , EventHandler<ActionEvent> event
    ){
        Button res;
        String url;
        switch (id){
            case IDENTIFIABLE_ADD:{
                url = Image.PLUS;
                break;
            }
            default: url = null;
        }

        if(url == null) {
            res = new Button(id.id);
        }else{
            res = new Button(
                    new ImageView(
                            new javafx.scene.image.Image(getClass().getResource(url).toExternalForm(), 30, 30, true, true)
                    )
            );
        }

        res.setId(id.id);
        res.setOnAction(event);

        return res;
    }

    protected abstract T getIdentifiableInstance();

    public static IdentifiablesListView getInstance(
            AvaliableTables type
    ) {
        try {

            List<? extends Identifiable> dataFromTable = ThothLite.getInstance().getDataFromTable(type);
            switch (type) {
                case ORDERABLE: {
                    return new OrderableListView((List<Orderable>) dataFromTable);
                }
                case STORAGABLE: {
                    return new StoragableListView((List<Storagable>) dataFromTable);
                }
                case PROJECTABLE: {
                    return new ProjectableListView((List<Projectable>) dataFromTable);
                }
                case PURCHASABLE: {
                    return new PurchasableListView((List<Purchasable>) dataFromTable);
                }
                case STORING: {
                    return new StoringListView((List<Storing>) dataFromTable);
                }
                default:
                    return new ListedListView( (List<Listed>) dataFromTable, type);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NotContainsException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void openCreateNewIdentifiable(ActionEvent event){
//        ( (OpenSubwindow) ThothLiteWindow.getInstance() ).openSubwindow( new IdentifiableCardWindow("Карточка", table, getIdentifiableInstance()) );
        ( (OpenSubwindow) ThothLiteWindow.getInstance() ).openSubwindow( new IdentifiableCardWindow("Карточка", table, null) );
    }

}
