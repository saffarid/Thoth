package thoth_gui.thoth_lite;

import controls.ListCell;
import controls.Twin;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Priority;
import layout.basepane.BorderPane;
import layout.basepane.VBox;
import thoth_gui.GuiLogger;
import thoth_gui.config.Config;
import thoth_gui.thoth_lite.components.controls.Button;
import thoth_gui.thoth_lite.components.controls.Label;
import thoth_gui.thoth_lite.components.controls.combo_boxes.ComboBox;
import thoth_gui.thoth_styleconstants.Stylesheets;


import java.util.Currency;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class SystemInfoConfig extends Dialog<Currency> {

    private final Insets headlineInsetsMargin = new Insets(0, 2, 0, 2);
    private final Insets headlineInsetsPadding = new Insets(0, 0, 0, 5);
    private final Insets configRowInsetsMargin = new Insets(0, 2, 0, 2);
    private final Insets configRowInsetsPadding = new Insets(0, 0, 0, 10);

    private controls.ComboBox<Currency> currencyComboBox = ComboBox.getInstance();

    private BorderPane content = new BorderPane();

    public SystemInfoConfig() {
        System.out.println("SystemInfoConfig");
        getDialogPane().setContent(createCenter());
        ButtonType yes = ButtonType.YES;
        getDialogPane().getButtonTypes().add(yes);
        setResultConverter(buttonType -> {
            if(buttonType == yes){
                return currencyComboBox.getValue();
            }
            return null;
        });
    }

    private Node createCenter() {
        initStyle();
        VBox vBox = new VBox();

        Twin currency = getConfigRow();
        currencyComboBox.setItems(
                FXCollections.observableList(
                        Currency.getAvailableCurrencies()
                                .stream()
                                .collect(Collectors.toList())
                )
        );
        currencyComboBox.setButtonCell(new CurrencyCell());
        currencyComboBox.setCellFactory(cell -> new CurrencyCell());
        currency.setFirstNode(Label.getInstanse("System Currency"));
        currency.setSecondNode(currencyComboBox);

        vBox.getChildren().addAll(
                currency
        );

        content.setCenter(vBox);
        return content;
    }

    private Twin getConfigRow() {
        Twin twin = new Twin()
                .setMinLeftWidth(250)
                .setMaxLeftWidth(250)
                .setPriorityLeft(Priority.NEVER)
                .setMinRightWidth(200)
                .setMaxRightWidth(450);
        twin.setPadding(configRowInsetsPadding);
        VBox.setMargin(twin, configRowInsetsMargin);
        return twin;
    }


    protected void initStyle() {
        CompletableFuture.runAsync(() -> {
                    GuiLogger.log.info("Theme init");
                    Config.getInstance().getScene().getThemeProperty().addListener((observableValue, colorTheme, t1) -> {
                        if (t1 != null) {
                            Platform.runLater(() -> {
                                if (colorTheme != null) content.getStyleClass().remove(colorTheme.getName().toLowerCase());
                                content.getStyleClass().add(t1.getName().toLowerCase());
                            });
                        }
                    });
                    Config.getInstance().getScene().getThemeProperty().bind(Config.getInstance().getScene().getThemeProperty());
                })
                .thenAccept(unused -> {
                    Platform.runLater(() -> {
                        for (Stylesheets s1 : Stylesheets.values()) {
                            content.getStylesheets().add(s1.getStylesheet());
                        }
                    });
                });
    }

    private class CurrencyCell extends ListCell<Currency> {

        private final static String template = "%1s-%2s";

        @Override
        protected void updateItem(Currency currency, boolean b) {
            if (currency == null) return;
            super.updateItem(currency, b);
            setGraphic(Label.getInstanse(
                    String.format(
                            template,
                            currency.getCurrencyCode(),
                            currency.getDisplayName()
                    )
            ));
        }
    }
}
