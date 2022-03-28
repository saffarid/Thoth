package thoth_gui.config;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import thoth_core.thoth_lite.config.ConfigEnums;
import thoth_core.thoth_lite.config.Configuration;
import thoth_gui.GuiLogger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class Config
        implements Configuration {

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

    private final Font font = new Font();
    private final Scene scene = new Scene();
    private final Window window = new Window();
//    private final Notify notify = new Notify();

    private Config() {
        configFile = new File(
                pathConfig + File.separator + fileConfigName
        );

        if (!configFile.exists()) {
            exportConfig();
        } else {
            try {
                setConfig(importConfig());
            } catch (IOException | ParseException e) {
                GuiLogger.log.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public JSONObject getConfig() {
        JSONObject config = new JSONObject();
        config.put(Keys.Section.FONT.getKey(), font.getConfig());
        config.put(Keys.Section.SCENE.getKey(), scene.getConfig());
        config.put(Keys.Section.WINDOW.getKey(), window.getConfig());
//        config.put(Keys.Section.NOTIFY.getKey(), notify.getConfig());
        return config;
    }

    @Override
    public ConfigEnums[] getConfigEnums(String key) {
        if (key.equals(Keys.Scene.COLOR_THEME.getKey())) {
            return ColorTheme.values();
        }
        if (key.equals(Keys.Scene.LOCALE.getKey())) {
            return Locales.values();
        }
        return null;
    }

    public Font getFont() {
        return font;
    }

    public static Config getInstance() {
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

//    public Notify getNotify(){
//        return notify;
//    }

    public void exportConfig() {
        GuiLogger.log.info("Export gui-config");
        if (!configFile.getParentFile().exists()) {
            configFile.getParentFile().mkdir();
        }

        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write(getConfig().toJSONString());
        } catch (IOException e) {
            GuiLogger.log.error("Export gui-config error", e);
        }

    }

    private JSONObject importConfig()
            throws IOException, ParseException {
        GuiLogger.log.info("Read gui-config file");
        FileReader reader = new FileReader(configFile);
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(reader);
    }

    @Override
    public void setConfig(JSONObject json) {
        GuiLogger.log.info("Set new gui-config");
        font.setConfig((JSONObject) json.get(Keys.Section.FONT.getKey()));
        scene.setConfig((JSONObject) json.get(Keys.Section.SCENE.getKey()));
        window.setConfig((JSONObject) json.get(Keys.Section.WINDOW.getKey()));
//        notify.setConfig((JSONObject) json.get(Keys.Section.NOTIFY.getKey()));
    }

    public class Font
            implements Configuration {

        private final String KEY_SIZE = Keys.Font.SIZE.getKey();
        private final String KEY_FAMILY = Keys.Font.FAMILY.getKey();
        private final javafx.scene.text.Font defaultFont = new javafx.scene.text.Font("Arial", 12.);

        private SimpleObjectProperty<javafx.scene.text.Font> font;

        public Font() {
            font = new SimpleObjectProperty<>(defaultFont);
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
        public void setConfig(JSONObject json) {
            if (json == null) return;
            javafx.scene.text.Font f = new javafx.scene.text.Font(
                    (String) json.get(KEY_FAMILY)
                    , (double) json.get(KEY_SIZE)
            );
            font.setValue(f);
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
        private final String KEY_COLOR_THEME = Keys.Scene.COLOR_THEME.getKey();
        private final String KEY_LOCALE = Keys.Scene.LOCALE.getKey();

        private final ColorTheme defaultTheme = ColorTheme.DARK;
        private final Locale defaultLocale = Locales.ENG.getValue();

        private SimpleObjectProperty<ColorTheme> theme;
        private Locale locale;

        public Scene() {
            theme = new SimpleObjectProperty<>(defaultTheme);
            locale = defaultLocale;
        }

        /* --- Getter --- */

        @Override
        public JSONObject getConfig() {
            JSONObject res = new JSONObject();
            res.put(KEY_COLOR_THEME, theme.getValue().getName());
            res.put(KEY_LOCALE, locale.toLanguageTag());
            return res;
        }

        @Override
        public ConfigEnums[] getConfigEnums(String key) {
            return null;
        }

        public SimpleObjectProperty<ColorTheme> getThemeProperty() {
            return theme;
        }

        public ColorTheme getTheme() {
            return theme.get();
        }

        public Locale getLocale() {
            return locale;
        }

        /* --- Setter --- */

        @Override
        public void setConfig(JSONObject json) {
            if (json == null) return;
            theme.setValue(ColorTheme.valueOf((String) json.get(KEY_COLOR_THEME)));
            locale = new Locale(String.valueOf(json.get(KEY_LOCALE)));
        }
    }

    /**
     * Конфигурация положения и отображения окна
     */
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

        private final double defaultxPrimary = 0;
        private final double defaultyPrimary = 0;
        private final double widthPrimaryMin = 1006;
        private final double heightPrimaryMin = 606;
        private final double defaultWidthPrimary = 1006;
        private final double defaultHeightPrimary = 806;
        private final boolean defaultIsMax = false;

        private final double defaultWidthSecondary = 806;
        private final double defaultHeightSecondary = 606;
        private final double widthSecondaryMin = 1006;
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
            xPrimary = new SimpleDoubleProperty(defaultxPrimary);
            yPrimary = new SimpleDoubleProperty(defaultyPrimary);
            widthPrimary = new SimpleDoubleProperty(defaultWidthPrimary);
            heightPrimary = new SimpleDoubleProperty(defaultHeightPrimary);
            isMax = new SimpleBooleanProperty(defaultIsMax);


            widthSecondary = new SimpleDoubleProperty(defaultWidthSecondary);
            heightSecondary = new SimpleDoubleProperty(defaultHeightSecondary);
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
        public void setConfig(JSONObject json) {
            if (json == null) return;
            xPrimary = new SimpleDoubleProperty((double) json.get(KEY_X_PRIMARY));
            yPrimary = new SimpleDoubleProperty((double) json.get(KEY_Y_PRIMARY));
            widthPrimary = new SimpleDoubleProperty((double) json.get(KEY_WIDTH_PRIMARY));
            heightPrimary = new SimpleDoubleProperty((double) json.get(KEY_HEIGHT_PRIMARY));
            isMax = new SimpleBooleanProperty((boolean) json.get(KEY_ISMAX));

            widthSecondary = new SimpleDoubleProperty((double) json.get(KEY_WIDTH_SECONDARY));
            heightSecondary = new SimpleDoubleProperty((double) json.get(KEY_HEIGHT_SECONDARY));
            xSecondary = new SimpleDoubleProperty((double) json.get(KEY_X_SECONDARY));
            ySecondary = new SimpleDoubleProperty((double) json.get(KEY_Y_SECONDARY));
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

        public boolean isDefaultIsMax() {
            return defaultIsMax;
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

    public class Notify
            implements Configuration{

        private final String KEY_NOTIFY_TO_OS = Keys.Notify.NOTIFY_TO_OS.getKey();

        private final boolean defaultNotifyToOs = true;
        private boolean notifyToOs;

        public Notify() {
            notifyToOs = defaultNotifyToOs;
        }

        @Override
        public JSONObject getConfig() {
            JSONObject res = new JSONObject();
            res.put(KEY_NOTIFY_TO_OS, notifyToOs);
            return res;
        }

        @Override
        public ConfigEnums[] getConfigEnums(String key) {
            return null;
        }

        @Override
        public void setConfig(JSONObject json) {
            if(json == null) return;
            notifyToOs = ((boolean) json.get(KEY_NOTIFY_TO_OS));
        }
    }

}
