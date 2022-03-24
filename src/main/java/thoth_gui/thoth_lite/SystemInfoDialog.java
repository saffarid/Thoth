package thoth_gui.thoth_lite;

import controls.ListCell;
import controls.Twin;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Dialog;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import layout.basepane.BorderPane;
import layout.basepane.HBox;
import layout.basepane.VBox;
import layout.custompane.Title;
import thoth_core.thoth_lite.info.SystemInfoKeys;
import thoth_gui.GuiLogger;
import thoth_gui.config.ColorTheme;
import thoth_gui.config.Config;
import thoth_gui.thoth_lite.components.controls.Button;
import thoth_gui.thoth_lite.components.controls.Label;
import thoth_gui.thoth_lite.components.controls.combo_boxes.ComboBox;
import thoth_gui.thoth_lite.tools.Properties;
import thoth_gui.thoth_lite.tools.TextCase;
import thoth_gui.thoth_styleconstants.Stylesheets;
import tools.BorderWrapper;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class SystemInfoDialog extends Dialog<HashMap<SystemInfoKeys, Object>> {

    public SystemInfoDialog(Stage stage, List<SystemInfoKeys> missingData) {
        initStyle(StageStyle.UNDECORATED);
        initModality(Modality.APPLICATION_MODAL);
        initOwner(stage);
        SystemInfoView node = new SystemInfoView(this, missingData);
        node.heightProperty().addListener((observableValue, node1, t1) -> {
            setHeight(node.getHeight());
        });
        getDialogPane().setContent(node);
    }

    private class SystemInfoView extends BorderPane {

        private final String TITLE_TEXT = Properties.getString("one-time-config", TextCase.NORMAL);

        private Dialog dialog;

        private final SimpleObjectProperty<ColorTheme> theme = new SimpleObjectProperty<>();
        private controls.ComboBox<Currency> currencyComboBox = ComboBox.getInstance();

        public SystemInfoView(Dialog dialog, List<SystemInfoKeys> missingData) {
            super();
            this.dialog = dialog;

            initStyle();

            setPadding(new Insets(0));
            BorderPane.setMargin(this, new Insets(0));

            setTop(createTop());
            setCenter(createCenter());
            setBottom(createBottom());
        }

        private Node createBottom() {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5));
            hBox.getChildren().add(Button.getInstance(Properties.getString("Apply", TextCase.NORMAL), event -> {
                HashMap<SystemInfoKeys, Object> resData = new HashMap<>();
                resData.put(SystemInfoKeys.SYSTEM_CURRENCY_CODE, currencyComboBox.getValue());
                setResult(resData);
            }));
            BorderPane.setAlignment(hBox, Pos.CENTER_RIGHT);
            return hBox;
        }

        private Node createCenter() {
            VBox vBox = new VBox();

            vBox.setBorder(
                    new BorderWrapper()
                            .addBottomBorder(1)
                            .addTopBorder(1)
                            .setStyle(BorderStrokeStyle.SOLID)
                            .setColor(Color.GREY)
                            .commit()
            );
            vBox.setPadding(new Insets(5));

            Twin currency = getConfigRow();
            currencyComboBox.setItems(
                    FXCollections.observableList(
                            Currency.getAvailableCurrencies()
                                    .stream()
                                    .collect(Collectors.toList())
                                    .stream()
                                    .sorted((o1, o2) -> o1.getCurrencyCode().compareTo(o2.getCurrencyCode()))
                                    .collect(Collectors.toList())
                    )
            );
            currencyComboBox.setButtonCell(new CurrencyCell());
            currencyComboBox.setCellFactory(cell -> new CurrencyCell());
            currencyComboBox.setValue(Currency.getInstance(Locale.getDefault()));
            currency.setFirstNode( Label.getInstanse( Properties.getString("locale_currency", TextCase.NORMAL) ) );
            currency.setSecondNode( currencyComboBox );

            vBox.getChildren().addAll(
                    currency
            );

            return vBox;
        }

        private Node createTop() {
            Title title = new Title()
                    .addText(TITLE_TEXT)
                    .addClose(event -> Platform.exit());

            title.setOnMousePressed(event -> {
                switch (event.getButton()) {
                    case PRIMARY: {
                        title.setSwitchSceneX( event.getX() );
                        title.setSwitchSceneY( event.getY() );
                        break;
                    }
                }
            });
            title.setOnMouseDragged(event -> {
                switch (event.getButton()) {
                    case PRIMARY: {
                        dialog.setX(event.getScreenX() - title.getSwitchSceneX());
                        dialog.setY(event.getScreenY() - title.getSwitchSceneY());
                    }
                }
            });

            title.setBorder(
                    new BorderWrapper()
                            .addBorder(0)
                            .commit()
            );

            return title;
        }

        private Twin getConfigRow() {
            Twin twin = new Twin()
                    .setPrefLeftWidth(250)
                    .setPriorityLeft(Priority.NEVER)
                    .setPrefRightWidth(250)
                    .setPriorityRight(Priority.NEVER);
            return twin;
        }

        protected void initStyle() {
            CompletableFuture.runAsync(() -> {
                        GuiLogger.log.info("Theme init");
                        theme.addListener((observableValue, colorTheme, t1) -> {
                            if (t1 == null) return;
                            Platform.runLater(() -> {
                                if (colorTheme != null) getStyleClass().remove(colorTheme.getName().toLowerCase());
                                getStyleClass().addAll(t1.getName().toLowerCase(), "window");
                            });
                        });
                        theme.bind(Config.getInstance().getScene().getThemeProperty());
                    })
                    .thenAccept(unused -> {
                        Platform.runLater(() -> {
                            for (Stylesheets s1 : Stylesheets.values()) {
                                getStylesheets().add(s1.getStylesheet());
                            }
                        });
                    });
        }

        /**
         * Ячейка отображения валюты
         */
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

}
