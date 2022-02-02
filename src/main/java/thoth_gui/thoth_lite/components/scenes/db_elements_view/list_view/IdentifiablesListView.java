package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_view;

import controls.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortBy;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortPane;
import thoth_gui.thoth_lite.components.scenes.ThothSceneImpl;
import tools.BackgroundWrapper;
import layout.basepane.BorderPane;
import thoth_core.thoth_lite.db_data.db_data_element.properties.*;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_core.thoth_lite.ThothLite;
import thoth_gui.thoth_lite.components.scenes.ThothScene;
import thoth_gui.thoth_lite.components.scenes.db_elements_view.identifiable_card.IdentifiableCard;
import thoth_gui.thoth_lite.components.scenes.db_elements_view.list_cell.IdentifiableListCell;
import thoth_gui.thoth_lite.main_window.Workspace;

import javafx.application.Platform;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;

import layout.basepane.HBox;
import thoth_gui.thoth_styleconstants.svg.Images;
import tools.SvgWrapper;
import window.Closeable;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class IdentifiablesListView<T extends Identifiable>
    extends ThothSceneImpl
        implements Flow.Subscriber<List<T>>
{

    private IdentifiableListCell identifiableListCell;

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

    protected static Logger LOG;

    protected AvaliableTables table;

    /**
     * Объект подписки
     * */
    private Flow.Subscription subscription;

    protected SimpleListProperty<T> datas;

    protected SortPane sortPane;
    protected ListView<T> identifiableElementList;

    static {
        LOG = Logger.getLogger(IdentifiablesListView.class.getName());
    }

    protected IdentifiablesListView(
            List<T> datas
            , AvaliableTables table
    ) {
        super();
        this.table = table;
        this.datas = new SimpleListProperty<T>(  );
        content = new SimpleObjectProperty<>(createListView());

        this.datas.addListener((ListChangeListener<? super T>) change -> {
            //Для обновления отображения списка создаём задачу и выполняем её в потоке JavaFX
            Platform.runLater(() -> {
                identifiableElementList.setCellFactory(tListView -> null);
                identifiableElementList.getItems().setAll(this.datas.getValue());
                identifiableElementList.setCellFactory(tListView -> new IdentifiableListCell(this.table));
            });
        });
        this.datas.setValue(FXCollections.observableList(datas));
        tools = new SimpleObjectProperty<>(getToolsPanel());

        try {
            ThothLite.getInstance().subscribeOnTable(this.table, this);
        } catch (NotContainsException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected Node getToolsPanel(){

        toolsNode = new BorderPane();

        toolsNode.setLeft(getSortPane());
        toolsNode.setRight( new BorderPane(getButton(Ids.IDENTIFIABLE_ADD, this::openCreateNewIdentifiable )) );

        return toolsNode;
    }

    protected abstract SortPane getSortPane();

    protected ListView<T> createListView(){
        identifiableElementList = thoth_gui.thoth_lite.components.controls.ListView.getInstance();
        identifiableElementList.setPadding(new Insets(2));

        identifiableElementList.setCellFactory(tListView -> new IdentifiableListCell(this.table));

        contentNode.setMargin(identifiableElementList, new Insets(5));

        Label haventItemsLabel = new Label("В списке нет элементов");
        haventItemsLabel.setAlignment(Pos.TOP_CENTER);
        identifiableElementList.setPlaceholder(haventItemsLabel);
        return identifiableElementList;
    }

    protected abstract void sort(ObservableValue<? extends SortBy> observableValue, SortBy sortBy, SortBy sortBy1);

    private Button getButton(
            Ids id
            , EventHandler<ActionEvent> event
    ){
        Button res;
        Node img;
        switch (id){
            case IDENTIFIABLE_ADD:{
                img = SvgWrapper.getInstance(Images.PLUS(), svgWidthTool, svgHeightTool, svgViewBoxWidthTool, svgViewBoxHeightTool);
                break;
            }
            default: img = null;
        }

        if(img == null) {
            res = thoth_gui.thoth_lite.components.controls.Button.getInstance(id.id, event);
        }else{
            res = thoth_gui.thoth_lite.components.controls.Button.getInstance(
                    img,
                    event
            );
        }

        res.setId(id.id);

        return res;
    }

    public static IdentifiablesListView getInstance(
            AvaliableTables type
    )   {
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
//                case STORING: {
//                    return new StoringListView((List<Storing>) dataFromTable);
//                }
                case CURRENCIES:{
                    return new FinanceListView((List<Finance>) dataFromTable);
                }
                default:
                    return new ListedListView( (List<Typable>) dataFromTable, type);
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

    /**
     * Функция завершает все текущие процессы
     * */
    @Override
    public void close(){
        this.subscription.cancel();
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onNext(List<T> item) {
        this.subscription.request(1);
        LOG.log(Level.INFO, "Обновление datas");
        this.datas.setValue( FXCollections.observableList(item) );
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    protected void openCreateNewIdentifiable(ActionEvent event){
        Workspace.getInstance().setNewScene(IdentifiableCard.getInstance(table, null));
    }

    @Override
    public void setCloseable(Closeable closeable) {

    }
}
