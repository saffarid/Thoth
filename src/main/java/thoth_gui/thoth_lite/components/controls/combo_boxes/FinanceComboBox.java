package thoth_gui.thoth_lite.components.controls.combo_boxes;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;
import thoth_core.thoth_lite.ThothLite;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Finance;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_gui.GuiLogger;
import thoth_gui.thoth_lite.components.controls.Label;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class FinanceComboBox {

    public static controls.ComboBox<Finance> getInstance() {
        controls.ComboBox<Finance> res = ComboBox.getInstance();

        res.setChangeListener((observableValue, t0, t1) -> {
            CompletableFuture.supplyAsync(() -> {
                return res.getDataProperty().getValue()
                        .stream()
                        .filter(finance -> finance.getCourse() > 0)
                        .collect(Collectors.toList());

            }).thenApply(finances -> {
                Platform.runLater(() -> {
                    res.setItems(
                            FXCollections.observableList(finances)
                    );
                });
                return finances;
            }).thenApply(finances -> {
                Optional<Finance> first = finances.stream()
                        .filter(finance ->
                                finance.getCurrency().getCurrencyCode().equals(Currency.getInstance(Locale.getDefault()).getCurrencyCode())
                        )
                        .findFirst();
                return first;
            }).thenAccept(finance -> {
                Platform.runLater(() -> {
                    if (finance.isPresent()) {
                        res.setValue(finance.get());
                    } else {
                        if (!res.getItems().isEmpty()) {
                            res.setValue(res.getItems().get(0));
                        }
                    }
                });
            });
        });

        try {
            ThothLite.getInstance().subscribeOnTable(AvaliableTables.CURRENCIES, res);
        } catch (NotContainsException e) {
            GuiLogger.log.error(e.getMessage(), e);
        }

        res.setCellFactory(listedListView -> new CurrencyCell());
        res.setButtonCell(new CurrencyCell());

        return res;
    }

    private static class CurrencyCell
            extends ListCell<Finance> {

        private final static String template = "%1s-%2s";

        @Override
        protected void updateItem(Finance currency, boolean b) {
            if (currency != null) {
                super.updateItem(currency, b);
                setGraphic(Label.getInstanse(
                        String.format(
                                template,
                                currency.getCurrency().getCurrencyCode(),
                                currency.getCurrency().getDisplayName()
                        )
                ));
            }
        }
    }

}
