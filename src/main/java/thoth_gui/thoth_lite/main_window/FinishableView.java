package thoth_gui.thoth_lite.main_window;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.paint.Color;
import tools.BorderWrapper;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Finishable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Identifiable;
import javafx.application.Platform;

import layout.basepane.BorderPane;

import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.concurrent.Flow;

public class FinishableView
        extends BorderPane
        implements
        Flow.Subscriber<Finishable> {

    private final String NO_ELEMENTS_LABEL = "no_elements";
    private final double WIDTH_SIZE = 200;

    private Flow.Subscription subscription;

    private SimpleListProperty<Finishable> finishables;

    private controls.ListView<Finishable> list;

    public FinishableView() {
        super();

        list = thoth_gui.thoth_lite.components.controls.ListView.getInstance();
        list.setBorder(
                new BorderWrapper()
                        .addTopBorder(1)
                        .setStyle(BorderStrokeStyle.SOLID)
                        .setColor(Color.valueOf("#23272b"))
                        .commit()
        );

        finishables = new SimpleListProperty<>();
        finishables.setValue(FXCollections.observableList(new LinkedList<>()));

        finishables.addListener((ListChangeListener<? super Finishable>) change -> {
            Platform.runLater(() -> {
                synchronized (list) {
                    list.setCellFactory(finishableListView -> null);
                    synchronized (change) {
                        list.getItems().setAll(change.getList());
                    }
                    list.setCellFactory(finishableListView -> new FinishableCell());
                }
            });
        });

        setCenter(list);

        setWidth(WIDTH_SIZE);
    }

    /**
     * Функция добавляет Finishable-элемент в список при условии что он ещё не содержится в списке
     */
    private void addItem(Finishable item) {
        Identifiable item1 = (Identifiable) item;

        if (!finishables.getValue().isEmpty()) {
            for (Finishable finishable : finishables.getValue()) {
                Identifiable item2 = (Identifiable) finishable;
                if (!item1.getId().equals(item2.getId())) {
                    finishables.getValue().add(item);
                    break;
                }
            }
        } else {
            finishables.getValue().add(item);
        }

    }

    public void setWidth(double width) {
        setMinWidth(width);
        setPrefWidth(width);
        setMaxWidth(width);
    }

    /* --- Функции работы с подпиской. --- */

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(Finishable item) {
        //Обработка
        addItem(item);
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }

    /* --- Работа с ячейкой списка --- */
    private class FinishableCell
            extends ListCell<Finishable> {
        @Override
        protected void updateItem(Finishable finishable, boolean b) {
            if (finishable != null) {
                super.updateItem(finishable, b);
                Identifiable finishable1 = (Identifiable) finishable;
                setText(finishable1.getId() + "-" + finishable.finishDate().format(DateTimeFormatter.ISO_DATE));
            }
        }
    }

}
