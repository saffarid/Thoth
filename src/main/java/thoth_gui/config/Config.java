package thoth_gui.config;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import thoth_core.thoth_lite.config.ConfigEnums;
import thoth_core.thoth_lite.config.Configuration;
import thoth_gui.thoth_styleconstants.color.ColorTheme;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Config
        implements Configuration {

    public enum KEYS {
        FONT("font"),
        SCENE("scene"),
        WINDOW("window"),
        ;
        private String key;

        KEYS(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    private static Config config;

    /**
     * Путь сохранения конфигурации
     */
    private final String pathConfig = "config";
    /**
     * Наименование файла конфигурации
     */
    private final String fileConfigName = "thoth_gui_config.json";

    private File configFile;

    private Font font;
    private Scene scene;
    private Window window;

    private Config()
            throws IOException, ParseException {
        configFile = new File(
                pathConfig + File.separator + fileConfigName
        );

        font = new Font();
        scene = new Scene();
        window = new Window();
        if (!configFile.exists()) {
            exportConfig();
        } else {
            setConfig( importConfig() );
        }
    }

    @Override
    public JSONObject getConfig() {
        JSONObject config = new JSONObject();
        config.put(KEYS.FONT.getKey(), font.getConfig());
        config.put(KEYS.SCENE.getKey(), scene.getConfig());
        config.put(KEYS.WINDOW.getKey(), window.getConfig());
        return config;
    }

    @Override
    public ConfigEnums[] getConfigEnums(String key) {
        return null;
    }

    public Font getFont() {
        return font;
    }

    public static Config getInstance()
            throws IOException, ParseException {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    public Scene getScene() {
        return scene;
    }

    public Window getWindow() {
        return window;
    }

    public void exportConfig() {

        if (!configFile.getParentFile().exists()) {
            configFile.getParentFile().mkdir();
        }

        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write(((JSONObject) getConfig()).toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private JSONObject importConfig()
            throws IOException, ParseException {
        FileReader reader = new FileReader(configFile);
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(reader);
    }

    @Override
    public void setConfig(JSONObject json) {
        font  .setConfig((JSONObject) json.get(KEYS.FONT.getKey()));
        scene .setConfig((JSONObject) json.get(KEYS.SCENE.getKey()));
        window.setConfig((JSONObject) json.get(KEYS.WINDOW.getKey()));
    }

    public class Font
            implements Configuration {

        private final String KEY_SIZE = "size";
        private final String KEY_FAMILY = "family";

        private SimpleObjectProperty<javafx.scene.text.Font> font;

        public Font() {
            javafx.scene.text.Font f = new javafx.scene.text.Font(12);
            font = new SimpleObjectProperty<>(f);
        }

        /* --- Getter --- */

        @Override
        public JSONObject getConfig() {
            JSONObject res = new JSONObject();

            res.put(KEY_SIZE, font.getValue().getSize());
            res.put(KEY_FAMILY, font.getValue().getFamily());

            return res;
        }

        @Override
        public ConfigEnums[] getConfigEnums(String key) {
            return null;
        }

        public javafx.scene.text.Font getFont() {
            return font.get();
        }

        public SimpleObjectProperty<javafx.scene.text.Font> fontProperty() {
            return font;
        }

        /* --- Setter --- */

        @Override
        public void setConfig(JSONObject data) {
            javafx.scene.text.Font f = new javafx.scene.text.Font(
                    (String) data.get(KEY_FAMILY)
                    , (double) data.get(KEY_SIZE)
            );
            font = new SimpleObjectProperty<>(f);
        }

        public void setFont(javafx.scene.text.Font font) {
            this.font.set(font);
        }

    }

    /**
     * Класс конфигурация отображения сцен
     */
    public class Scene
            implements Configuration {
        private final String KEY_COLOR_THEME = "color_theme";

        private final ColorTheme themeDefault = ColorTheme.DARK;

        private ColorTheme theme;

        public Scene() {
            theme = themeDefault;
        }

        /* --- Getter --- */

        @Override
        public JSONObject getConfig() {
            JSONObject res = new JSONObject();
            res.put(KEY_COLOR_THEME, theme.toString());
            return res;
        }

        @Override
        public ConfigEnums[] getConfigEnums(String key) {
            return null;
        }

        public ColorTheme getTheme() {
            return theme;
        }

        /* --- Setter --- */

        @Override
        public void setConfig(JSONObject importJson) {
            theme = ColorTheme.valueOf((String) importJson.get(KEY_COLOR_THEME));
        }
    }

    /**
     * Конфигурация положения и отображения окна
     * */
    public class Window
            implements Configuration {

        private final String KEY_X_PRIMARY = "x_primary";
        private final String KEY_Y_PRIMARY = "y_primary";
        private final String KEY_WIDTH_PRIMARY = "width_primary";
        private final String KEY_HEIGHT_PRIMARY = "height_primary";
        private final String KEY_ISMAX = "isMax";

        private final String KEY_X_SECONDARY = "x_secondary";
        private final String KEY_Y_SECONDARY = "y_secondary";
        private final String KEY_WIDTH_SECONDARY = "width_secondary";
        private final String KEY_HEIGHT_SECONDARY = "height_secondary";

        private final double xPrimaryDefault = 0;
        private final double yPrimaryDefault = 0;
        private final double widthPrimaryMin = 806;
        private final double heightPrimaryMin = 406;
        private final double widthPrimaryDefault = 1006;
        private final double heightPrimaryDefault = 606;
        private final boolean isMaxDefault = false;

        private final double widthSecondaryDefault = 806;
        private final double heightSecondaryDefault = 606;
        private final double widthSecondaryMin = 806;
        private final double heightSecondaryMin = 406;

        private SimpleDoubleProperty xPrimary;
        private SimpleDoubleProperty yPrimary;
        private SimpleDoubleProperty widthPrimary;
        private SimpleDoubleProperty heightPrimary;
        private SimpleBooleanProperty isMax;

        private SimpleDoubleProperty xSecondary;
        private SimpleDoubleProperty ySecondary;
        private SimpleDoubleProperty widthSecondary;
        private SimpleDoubleProperty heightSecondary;

        public Window() {
            xPrimary = new SimpleDoubleProperty(xPrimaryDefault);
            yPrimary = new SimpleDoubleProperty(yPrimaryDefault);
            widthPrimary = new SimpleDoubleProperty(widthPrimaryDefault);
            heightPrimary = new SimpleDoubleProperty(heightPrimaryDefault);
            isMax = new SimpleBooleanProperty(isMaxDefault);


            widthSecondary = new SimpleDoubleProperty(widthSecondaryDefault);
            heightSecondary = new SimpleDoubleProperty(heightSecondaryDefault);
            xSecondary = new SimpleDoubleProperty(xPrimary.get() + ((widthPrimary.get() - widthSecondary.get()) / 2));
            ySecondary = new SimpleDoubleProperty(yPrimary.get() + ((heightPrimary.get() - heightSecondary.get()) / 2));
        }

        @Override
        public JSONObject getConfig() {
            JSONObject res = new JSONObject();

            res.put(KEY_X_PRIMARY, xPrimary.doubleValue());
            res.put(KEY_Y_PRIMARY, yPrimary.doubleValue());
            res.put(KEY_WIDTH_PRIMARY, widthPrimary.doubleValue());
            res.put(KEY_HEIGHT_PRIMARY, heightPrimary.doubleValue());
            res.put(KEY_ISMAX, isMax.get());

            res.put(KEY_WIDTH_SECONDARY, widthSecondary.doubleValue());
            res.put(KEY_HEIGHT_SECONDARY, heightSecondary.doubleValue());
            res.put(KEY_X_SECONDARY, xSecondary.doubleValue());
            res.put(KEY_Y_SECONDARY, ySecondary.doubleValue());

            return res;
        }

        @Override
        public ConfigEnums[] getConfigEnums(String key) {
            return null;
        }

        @Override
        public void setConfig(JSONObject data) {
            xPrimary = new SimpleDoubleProperty((double) data.get(KEY_X_PRIMARY));
            yPrimary = new SimpleDoubleProperty((double) data.get(KEY_Y_PRIMARY));
            widthPrimary = new SimpleDoubleProperty((double) data.get(KEY_WIDTH_PRIMARY));
            heightPrimary = new SimpleDoubleProperty((double) data.get(KEY_HEIGHT_PRIMARY));
            isMax = new SimpleBooleanProperty((boolean) data.get(KEY_ISMAX));

            widthSecondary = new SimpleDoubleProperty((double) data.get(KEY_WIDTH_SECONDARY));
            heightSecondary = new SimpleDoubleProperty((double) data.get(KEY_HEIGHT_SECONDARY));
            xSecondary = new SimpleDoubleProperty((double) data.get(KEY_X_SECONDARY));
            ySecondary = new SimpleDoubleProperty((double) data.get(KEY_Y_SECONDARY));
        }

        /*
         * Функции для работы с первичным окном
         * */
        public double getHeightPrimary() {
            return heightPrimary.get();
        }

        public double getWidthPrimary() {
            return widthPrimary.get();
        }

        public double getHeightPrimaryMin() {
            return heightPrimaryMin;
        }

        public double getWidthPrimaryMin() {
            return widthPrimaryMin;
        }

        public double getxPrimary() {
            return xPrimary.get();
        }

        public double getyPrimary() {
            return yPrimary.get();
        }

        public boolean isIsMax() {
            return isMax.get();
        }

        public boolean isMaxDefault() {
            return isMaxDefault;
        }

        public SimpleBooleanProperty isMaxProperty() {
            return isMax;
        }

        public SimpleDoubleProperty widthPrimaryProperty() {
            return widthPrimary;
        }

        public SimpleDoubleProperty heightPrimaryProperty() {
            return heightPrimary;
        }

        public SimpleDoubleProperty xPrimaryProperty() {
            return xPrimary;
        }

        public SimpleDoubleProperty yPrimaryProperty() {
            return yPrimary;
        }

        /*
         * Функции для работы с вторичным окном
         * */
        public double getHeightSecondary() {
            return heightSecondary.get();
        }

        public double getWidthSecondary() {
            return widthSecondary.get();
        }

        public double getHeightSecondaryMin() {
            return heightSecondaryMin;
        }

        public double getWidthSecondaryMin() {
            return widthSecondaryMin;
        }

        public SimpleDoubleProperty heightSecondaryProperty() {
            return heightSecondary;
        }

        public SimpleDoubleProperty widthSecondaryProperty() {
            return widthSecondary;
        }

        public SimpleDoubleProperty xSecondaryProperty() {
            return xSecondary;
        }

        public SimpleDoubleProperty ySecondaryProperty() {
            return ySecondary;
        }

        public double getxSecondary() {
            return xSecondary.get();
        }

        public double getySecondary() {
            return ySecondary.get();
        }
    }

}
