package thoth_gui.thoth_lite.components.db_elements_view.list_view;

import thoth_core.thoth_lite.db_data.db_data_element.properties.*;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_core.thoth_lite.ThothLite;
import thoth_gui.OpenSubwindow;
import thoth_gui.thoth_lite.components.db_elements_view.list_cell.IdentifiableListCell;
import thoth_gui.thoth_lite.subwindows.IdentifiableCardWindow;
import thoth_gui.thoth_lite.main_window.ThothLiteWindow;
import thoth_gui.thoth_styleconstants.Stylesheets;
import controls.Button;
import controls.ComboBox;
import controls.Label;

import javafx.application.Platform;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ListView;

import javafx.scene.layout.BorderPane;
import layout.basepane.HBox;
import thoth_gui.thoth_styleconstants.Image;
import window.Closeable;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class IdentifiablesListView<T extends Identifiable>
        extends BorderPane
        implements Closeable,
        Flow.Subscriber<List<T>>
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

    private static final String STYLESHEET_PATH = Stylesheets.IDENTIFIABLE_LIST;
    protected AvaliableTables table;

    /**
     * Объект подписки
     * */
    private Flow.Subscription subscription;

    protected BorderPane pallete;
    protected HBox sortedPane;
    protected ListView<T> identifiableElementList;
//    protected List<T> datas;
    protected SimpleListProperty<T> datas;

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
        setCenter(createListView());
        setTop(createPallete());

        this.datas.addListener((ListChangeListener<? super T>) change -> {
            //Для обновления отображения списка создаём задачу и выполняем её в потоке JavaFX
            Platform.runLater(() -> {
                identifiableElementList.setCellFactory(tListView -> null);
                identifiableElementList.getItems().setAll(this.datas.getValue());
                identifiableElementList.setCellFactory(tListView -> new IdentifiableListCell(this.table));
            });
        });

        this.datas.setValue(FXCollections.observableList(datas));

        try {
            ThothLite.getInstance().subscribeOnTable(this.table, this);
        } catch (NotContainsException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

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

        sortedPane.setPadding(new Insets(2, 2, 2, 5));
        sortedPane.setSpacing(2);
        sortedPane.setAlignment(Pos.CENTER_LEFT);

        Label sortLabel = new Label("Сортировка:");
        ComboBox sortBox = new ComboBox<>();
        sortBox.setId(Ids.SORTED_BOX.id);

        sortedPane.getChildren().addAll(
                sortLabel
                , sortBox
        );

        return sortedPane;
    }

    protected ListView<T> createListView(){
        identifiableElementList = new ListView<>();
        identifiableElementList.setPadding(new Insets(2));
//        identifiableElementList.itemsProperty().bind( datas );

        identifiableElementList.setCellFactory(tListView -> new IdentifiableListCell(this.table));

        setMargin(identifiableElementList, new Insets(5));

        Label haventItemsLabel = new Label("В списке нет элементов");
        haventItemsLabel.setAlignment(Pos.TOP_CENTER);
        identifiableElementList.setPlaceholder(haventItemsLabel);
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
            res = thoth_gui.thoth_lite.components.controls.Button.getInstance(id.id, event);
        }else{
            res = thoth_gui.thoth_lite.components.controls.Button.getInstance(
                    thoth_gui.thoth_lite.components.controls.ImageView.getInstance( url, 30, 30 ),
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
//        ( (OpenSubwindow) ThothLiteWindow.getInstance() ).openSubwindow( new IdentifiableCardWindow("Карточка", table, getIdentifiableInstance()) );
        ( (OpenSubwindow) ThothLiteWindow.getInstance() ).openSubwindow( new IdentifiableCardWindow("Карточка", table, null) );
    }

}
