package thoth_gui.thoth_lite.components.scenes;

import controls.Toggle;
import controls.Twin;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.scene.layout.Priority;
import layout.basepane.BorderPane;
import layout.basepane.GridPane;
import layout.basepane.VBox;
import main.Main;
import org.json.simple.parser.ParseException;
import thoth_core.thoth_lite.config.ConfigEnums;
import thoth_core.thoth_lite.config.Configuration;
import thoth_gui.GuiLogger;
import thoth_gui.config.Config;
import thoth_gui.config.Keys;
import thoth_gui.thoth_lite.components.controls.Button;
import thoth_gui.thoth_lite.components.controls.Label;
import thoth_gui.thoth_lite.components.controls.Tooltip;
import thoth_gui.thoth_lite.tools.Properties;
import thoth_gui.thoth_lite.tools.TextCase;
import thoth_gui.thoth_lite.components.controls.ToolsPane;
import thoth_gui.thoth_lite.components.controls.combo_boxes.ComboBox;
import thoth_gui.thoth_lite.components.controls.combo_boxes.ConfigEnumsComboBox;
import thoth_gui.thoth_styleconstants.svg.Images;
import org.json.simple.JSONObject;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_core.thoth_lite.ThothLite;
import javafx.geometry.Insets;
import javafx.scene.Node;
import layout.basepane.HBox;
import thoth_gui.Apply;
import thoth_gui.Cancel;
import tools.SvgWrapper;
import window.Closeable;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Settings
        extends ThothSceneImpl
        implements Apply
        , Cancel {

    private final String TITLE_TEXT = "settings";

    private final Insets headlineInsetsMargin = new Insets(0, 2, 0, 2);
    private final Insets headlineInsetsPadding = new Insets(0, 0, 0, 5);
    private final Insets configRowInsetsMargin = new Insets(0, 2, 0, 2);
    private final Insets configRowInsetsPadding = new Insets(0, 0, 0, 10);

    private static Settings instance;

    private Closeable closeable;

    private List<Configuration> configs;

    /**
     * Новая конфигурация. Объединяет все системы в одном json.
     */
    private JSONObject newConfigJson;


    public Settings() {
        super();
        this.id = Scenes.SETTINGS.name();
        //Запрос конфигураций
        GuiLogger.log.info("Get config-json");
        newConfigJson = new JSONObject();

        configs = new LinkedList<>();
        configs.add(Config.getInstance());
        configs.add(ThothLite.getInstance().getConfig());

        content = new SimpleObjectProperty<>(createContentNode());
        tools = new SimpleObjectProperty<>(createToolsNode());

    }

    @Override
    public void apply() {
        GuiLogger.log.info("Save config");
        for (Configuration config : configs) {
            config.setConfig(newConfigJson);
        }
        cancel();
    }

    @Override
    public void cancel() {
        closeable.close();
    }

    @Override
    public void close() {

    }

    @Override
    protected Node createToolsNode() {

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(0, 0, 0, 5));
        hBox.getChildren().addAll(
                Button.getInstance(
                        SvgWrapper.getInstance(Images.CHECKMARK(), svgWidthTool, svgHeightTool, svgViewBoxWidthTool, svgViewBoxHeightTool),
                        event -> apply()
                ).setTool(Tooltip.getInstance("apply")),
                Button.getInstance(
                        SvgWrapper.getInstance(Images.CLOSE(), svgWidthTool, svgHeightTool, svgViewBoxWidthTool, svgViewBoxHeightTool),
                        event -> cancel()
                ).setTool(Tooltip.getInstance("cancel"))
        );

        toolsNode =
                new ToolsPane(TITLE_TEXT)
                        .addAdditional(hBox)
        ;

        return toolsNode;
    }

    @Override
    protected Node createContentNode() {
        contentNode = new BorderPane();
        contentNode.setPadding(new Insets(5, 0, 0, 0));
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        //Проходим по все jsonам и строим на основе их контент
        GuiLogger.log.info("Create config-view");
        for (Configuration json : configs) {
            parseJson(json, json.getConfig(), vBox, newConfigJson);
        }
        GuiLogger.log.info("Create config-view is done");
        contentNode.setCenter(vBox);
        return contentNode;
    }

    private void parseJson(
            Configuration config,
            JSONObject json,
            VBox vBox,
            JSONObject newJson
    ) {
        for (Object k : json.keySet()) {
            String key = String.valueOf(k);
            //Игнорируем конфигурацию окна
            if (key.equals(Keys.Section.WINDOW.getKey())) continue;

            Object value = json.get(k);
            if (value instanceof JSONObject) {
                //Если value = json-объект, то добавляем компонент headline и парсим json дальше на предмет выявления компонентов
                vBox.getChildren().add(getHeadline(key));
                JSONObject value1 = new JSONObject();
                parseJson(config, (JSONObject) value, vBox, value1);
                newConfigJson.put(key, value1);
            } else {

                //Создаем компоненты
                Twin twin = getConfigRow();
                twin.setFirstNode(Label.getInstanse(Properties.getString(key, TextCase.NORMAL)));

                newJson.put(key, value);

                if (value instanceof String) {
                    ConfigEnums[] configEnums = config.getConfigEnums(key);
                    if (configEnums == null) {
                        //Проверяем на FontFamily
                        if (key.equals(Keys.Font.FAMILY.getKey())) {
                            //Добавляем комбобокс с family шрифтами
                            twin.setSecondNode(createFamilyComboBox(String.valueOf(value), (observableValue, s, t1) -> newJson.put(key, t1)));
                        } else {
                            //Добавляем текстовое поле
                        }
                    } else {
                        //Добавляем ComboBox
                        twin.setSecondNode(ConfigEnumsComboBox.getInstance(configEnums, value, (observableValue, configEnums1, t1) -> newJson.put(key, t1.getName())));
                    }
                } else if (value instanceof Boolean) {
                    Toggle right = new Toggle((boolean) value);
                    right.isTrueProperty().addListener((observableValue, aBoolean, t1) -> newJson.put(key, t1));
                    twin.setSecondNode(right);
                } else {
                    if (key.equals(Keys.Font.SIZE.getKey())) {
                        //Комбобокс с вариантами размера шрифта
                        twin.setSecondNode(createSizeComboBox((Number) value, (observableValue, number, t1) -> newJson.put(key, t1)));
                    }
                }
                vBox.getChildren().add(twin);
            }
        }
    }

    private Node getHeadline(String text) {
        GridPane pane = new GridPane();
        pane
                .addRow(Priority.NEVER)
                .addColumn(Priority.ALWAYS);
        VBox.setMargin(pane, headlineInsetsMargin);
        pane.add(Label.getInstanse(text, TextCase.UPPER), 0, 0);
        pane.setPadding(headlineInsetsPadding);
        return pane;
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

    private controls.ComboBox<Number> createSizeComboBox(Number value, ChangeListener<Number> valueListener) {
        controls.ComboBox<Number> res = ComboBox.getInstance();
        for (double i = 10.; i < 22.; i += 2.) {
            res.getItems().add(i);
        }
        res.setValue(value);
        res.valueProperty().addListener(valueListener);
        return res;
    }

    private controls.ComboBox createFamilyComboBox(String value, ChangeListener<String> valueListener) {
        controls.ComboBox<String> res = ComboBox.getInstance();

        CompletableFuture.supplyAsync(() -> {
            return Arrays
                    .stream(GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts())
                    .map(font -> font.getFamily())
                    .distinct()
                    .collect(Collectors.toList())
                    ;
        }).thenAccept(strings -> {
            Platform.runLater(() -> res.setItems(FXCollections.observableList(strings)));
        });
        res.setValue(value);
        res.valueProperty().addListener(valueListener);

        return res;
    }

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    @Override
    public void setCloseable(Closeable closeable) {
        this.closeable = closeable;
    }

    @Override
    public void open() {

    }
}
