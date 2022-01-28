package thoth_gui.thoth_lite.components.controls.combo_boxes;

import controls.ComboBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;
import thoth_core.thoth_lite.ThothLite;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Finance;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_core.thoth_lite.exceptions.NotContainsException;

import java.sql.SQLException;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class FinanceComboBox {

    public static ComboBox<Finance> getInstance(){

        ComboBox<Finance> res = thoth_gui.thoth_lite.components.controls.combo_boxes.ComboBox.getInstance();

        try {
            List<Finance> finances = (List<Finance>) ThothLite.getInstance().getDataFromTable(AvaliableTables.CURRENCIES);
            res.setItems(
                    FXCollections.observableList( finances )
            );
            CompletableFuture.supplyAsync(() -> {
                Optional<Finance> first = finances.stream()
                        .filter( finance ->
                                finance.getCurrency().getCurrencyCode().equals( Currency.getInstance(Locale.getDefault()).getCurrencyCode() )
                        )
                        .findFirst();
                return first;
            }).thenAccept(finance -> {
                Platform.runLater(() -> {
                    if(finance.isPresent()){
                        res.setValue(finance.get());
                    }else{
                        if (!res.getItems().isEmpty()) {
                            res.setValue(res.getItems().get(0));
                        }
                    }
                });
            });
        } catch (NotContainsException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
                setText(
                        String.format(
                                template,
                                currency.getCurrency().getCurrencyCode(),
                                currency.getCurrency().getDisplayName()
                        )
                );
            }
        }
    }

}
