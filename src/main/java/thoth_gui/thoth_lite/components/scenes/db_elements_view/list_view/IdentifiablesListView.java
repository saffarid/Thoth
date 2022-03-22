package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_view;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;

import javafx.geometry.Pos;
import thoth_gui.GuiLogger;
import thoth_gui.thoth_lite.components.controls.ListView;
import thoth_gui.thoth_lite.components.controls.ToolsPane;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortBy;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortPane;
import thoth_gui.thoth_lite.components.scenes.ThothSceneImpl;
import layout.basepane.BorderPane;
import thoth_core.thoth_lite.db_data.db_data_element.properties.*;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_core.thoth_lite.ThothLite;

import thoth_gui.thoth_lite.components.scenes.db_elements_view.identifiable_card.IdentifiableCard;
import thoth_gui.thoth_lite.components.scenes.db_elements_view.list_cell.IdentifiableListCell;
import thoth_gui.thoth_lite.main_window.Workspace;

import javafx.application.Platform;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;

import javafx.scene.Node;

import thoth_gui.thoth_styleconstants.svg.Images;
import tools.SvgWrapper;
import window.Closeable;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class IdentifiablesListView<T extends Identifiable>
        extends ThothSceneImpl
        implements Flow.Subscriber<List<T>> {

    protected enum Ids {
        IDENTIFIABLE_ADD("add"),
        SORTED_BOX("sort_box");

        private String id;

        Ids(String id) {
            this.id = id;
        }

        public String toString() {
            return id;
        }
    }

    protected static Logger LOG;
    private IdentifiableListCell identifiableListCell;
    protected AvaliableTables table;

    /**
     * Объект подписки
     */
    private Flow.Subscription subscription;

    protected SimpleListProperty<T> datas;

    protected SortPane sortPane;
    protected controls.ListView<T> identifiableElementList = ListView.getInstance();

    static {
        LOG = Logger.getLogger(IdentifiablesListView.class.getName());
    }

    protected IdentifiablesListView(
            List<T> datas
            , AvaliableTables table
    ) {
        super();
        this.table = table;
        this.datas = new SimpleListProperty<T>();
        GuiLogger.log.info("Create list-view");
        content = new SimpleObjectProperty<>(createContentNode());
        GuiLogger.log.info("Add list-data");
        this.datas.addListener((ListChangeListener<? super T>) change -> {
            //Для обновления отображения списка создаём задачу и выполняем её в потоке JavaFX
            Platform.runLater(() -> {
                identifiableElementList.getItems().setAll(this.datas.getValue());
            });
        });
        setNewList(datas);
        GuiLogger.log.info("Create tools-view");
        tools = new SimpleObjectProperty<>(createToolsNode());

    }

    @Override
    protected Node createContentNode() {
        identifiableElementList.setCellFactory(tListView -> new IdentifiableListCell(this.table));
        contentNode = new BorderPane(identifiableElementList);
        return contentNode;
    }



    @Override
    protected Node createToolsNode() {
        toolsNode = new ToolsPane(table.name())
                .addSortPane(getSortPane())
                .addNewButton(
                        SvgWrapper.getInstance(Images.PLUS(), svgWidthTool, svgHeightTool, svgViewBoxWidthTool, svgViewBoxHeightTool),
                        this::openCreateNewIdentifiable
                );
        return toolsNode;
    }

    protected abstract SortPane getSortPane();

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
                case PARTNERS: {
                    return new PartnerListView((List<Partnership>) dataFromTable);
                }
                case CURRENCIES: {
                    return new FinanceListView((List<Finance>) dataFromTable);
                }
                default:
                    return new ListedListView((List<Typable>) dataFromTable, type);
            }
        } catch (NotContainsException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void open() {
        try {
            ThothLite.getInstance().subscribeOnTable(this.table, this);
        } catch (NotContainsException e) {
            e.printStackTrace();
        }
    }

    protected void openCreateNewIdentifiable(ActionEvent event) {
        Workspace.getInstance().setNewScene(IdentifiableCard.getInstance(table, null));
    }

    private void setNewList(List<T> items) {
        CompletableFuture.supplyAsync(() -> {
            if (this.table == AvaliableTables.EXPENSES_TYPES ||
                this.table == AvaliableTables.INCOMES_TYPES) {
                try {
                    List<Typable> productTypes = (List<Typable>) ThothLite.getInstance().getDataFromTable(AvaliableTables.PRODUCT_TYPES);
                    for (Typable typable : productTypes){
                        for(Typable t : (List<Typable>) items){
                            if(t.getValue().equals(typable.getValue())){
                                items.remove(t);
                            }
                        }
                    }
                }
                catch (NotContainsException e) {
                    GuiLogger.log.error(e.getMessage(), e);
                }
            }
            return items;
        })
                .thenAccept(item -> {
            this.datas.setValue(FXCollections.observableList(item));
        });
    }

    protected abstract void sort(ObservableValue<? extends SortBy> observableValue, SortBy sortBy, SortBy sortBy1);

    @Override
    public void setCloseable(Closeable closeable) {
    }

    /**
     * Функция завершает все текущие процессы
     */
    @Override
    public void close() {
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
        setNewList(item);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

}
