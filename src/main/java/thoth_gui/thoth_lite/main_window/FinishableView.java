package thoth_gui.thoth_lite.main_window;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_core.thoth_lite.timer.WhatDo;
import thoth_gui.thoth_lite.components.controls.Label;
import thoth_gui.thoth_lite.components.controls.ListView;
import thoth_gui.thoth_lite.components.scenes.db_elements_view.identifiable_card.IdentifiableCard;
import thoth_gui.thoth_lite.tools.TextCase;
import tools.BorderWrapper;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Finishable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Identifiable;
import javafx.application.Platform;

import layout.basepane.BorderPane;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;
import java.util.stream.Collectors;

public class FinishableView
        extends BorderPane
        implements
        Flow.Subscriber<HashMap<WhatDo, Finishable>> {
    private static final String NEAR_DELIVERED = "near_delivered";

    private final double WIDTH_SIZE = 200;

    private Flow.Subscription subscription;

    private final SimpleListProperty<Finishable> finishables = new SimpleListProperty<>();
    private final controls.ListView<Finishable> list = ListView.getInstance();

    public FinishableView() {
        super();

        list.setBorder(
                new BorderWrapper()
                        .addTopBorder(1)
                        .setStyle(BorderStrokeStyle.SOLID)
                        .setColor(Color.valueOf("#23272b"))
                        .commit()
        );
        list.setPadding(new Insets(2));

        finishables.setValue(FXCollections.observableList(new LinkedList<>()));

        finishables.addListener((ListChangeListener<? super Finishable>) change -> {
            Platform.runLater(() -> {
                List<Finishable> datas = change.getList()
                        .stream()
                        .sorted((o1, o2) -> {
                            int compare = o1.finishDate().compareTo(o2.finishDate());
                            if (compare == 0) {
                                return ((Identifiable) o1).getId().compareTo(((Identifiable) o2).getId());
                            } else {
                                return compare;
                            }
                        })
                        .collect(Collectors.toList());
                this.list.setCellFactory(finishableListView -> null);
                this.list.getItems().setAll(datas);
                this.list.setCellFactory(finishableListView -> new FinishableCell());
            });
        });

        Pane pane = new Pane(
                Label.getInstanse(NEAR_DELIVERED, TextCase.NORMAL)
                        .setPadding(4, 2, 4, 2)
        );
        pane.setBorder(
                new BorderWrapper()
                        .addTopBorder(1)
                        .setStyle(BorderStrokeStyle.SOLID)
                        .setColor(Color.valueOf("grey"))
                        .commit()
        );

        setTop(
                pane
        );
        setCenter(list);
        setWidth(WIDTH_SIZE);
    }

    /**
     * Функция добавляет Finishable-элемент в список при условии что он ещё не содержится в списке
     */
    private void addItem(Finishable item) {
        Identifiable item1 = (Identifiable) item;
        CompletableFuture.runAsync(() -> {
            synchronized (finishables) {
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
        });
    }

    /**
     * Функция удаляет Finishable-элемент из списка оповещения
     */
    private void removeItem(Finishable finishable) {
        finishables.getValue().remove(finishable);
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
    public void onNext(HashMap<WhatDo, Finishable> item) {
        //Обработка
        this.subscription.request(1);
        if (item.containsKey(WhatDo.PLANNING)) {
            addItem(item.get(WhatDo.PLANNING));
            return;
        }
        if (item.containsKey(WhatDo.CANCEL)) {
            removeItem(item.get(WhatDo.CANCEL));
            return;
        }
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

        private Identifiable finishable;

        @Override
        protected void updateItem(Finishable finishable, boolean b) {
            if (finishable != null) {
                super.updateItem(finishable, b);
                this.finishable = (Identifiable) finishable;
                this.setOnMouseClicked(this::cellClick);
                setGraphic(
                        Label.getInstanse(
                                this.finishable.getId() + "-" + finishable.finishDate().format(DateTimeFormatter.ISO_DATE)
                        )
                );
            }
        }

        private void cellClick(MouseEvent mouseEvent) {
            switch (mouseEvent.getButton()) {
                case PRIMARY: {
                    Workspace.getInstance().setNewScene(
                            IdentifiableCard.getInstance(AvaliableTables.PURCHASABLE, this.finishable)
                    );
                    break;
                }
                case SECONDARY: {

                }
            }
        }

    }

}
