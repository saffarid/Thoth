package ThothGUI.Config;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.text.Font;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {

    private enum KEYS {
        FONT("font"),
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
    private Window window;

    private Config() throws IOException, ParseException {

        configFile = new File(
                pathConfig + File.separator + fileConfigName
        );

        if (!configFile.exists()) {
            font = new Font();
            window = new Window();

            exportConfig();
        } else {
            JSONObject parse = importConfig();

            font = new Font((JSONObject) parse.get(KEYS.FONT.getKey()));
            window = new Window((JSONObject) parse.get(KEYS.WINDOW.getKey()));
        }

    }

    private void exportConfig() {

        if (!configFile.getParentFile().exists()) {
            configFile.getParentFile().mkdir();
        }
        JSONObject config = new JSONObject();

        config.put(KEYS.FONT.getKey(), font.exportJSON());
        config.put(KEYS.WINDOW.getKey(), window.exportJSON());

        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write(config.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Config getInstance() throws IOException, ParseException {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    private JSONObject importConfig()
            throws IOException, ParseException {
        FileReader reader = new FileReader(configFile);
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(reader);
    }

    public Font getFont() {
        return font;
    }

    public Window getWindow() {
        return window;
    }

    public class Font {

        private final String KEY_SIZE = "size";
        private final String KEY_FAMILY = "family";

        private SimpleObjectProperty<javafx.scene.text.Font> font;

        public Font() {
            javafx.scene.text.Font f = new javafx.scene.text.Font(12);
            font = new SimpleObjectProperty<>(f);
        }

        public Font(JSONObject data) {
            javafx.scene.text.Font f = new javafx.scene.text.Font(
                    (String) data.get(KEY_FAMILY)
                    , (double) data.get(KEY_SIZE)
            );
            font = new SimpleObjectProperty<>(f);
        }

        public JSONObject exportJSON() {
            JSONObject res = new JSONObject();

            res.put(KEY_SIZE, font.getValue().getSize());
            res.put(KEY_FAMILY, font.getValue().getFamily());

            return res;
        }

        public SimpleObjectProperty<javafx.scene.text.Font> fontProperty() {
            return font;
        }

        public javafx.scene.text.Font getFont() {
            return font.get();
        }

        public void setFont(javafx.scene.text.Font font) {
            this.font.set(font);
        }
    }

    public class Window {

        private final String KEY_START_X = "startX";
        private final String KEY_START_Y = "startY";
        private final String KEY_WIDTH = "width";
        private final String KEY_HEIGHT = "height";
        private final String KEY_ISMAX = "isMax";

        private final double startXDefault = 0;
        private final double startYDefault = 0;
        private final double widthDefault = 1006;
        private final double heightDefault = 606;
        private final boolean isMaxDefault = false;

        private Double startX;
        private Double startY;
        private Double width;
        private Double height;
        private Boolean isMax;

        public Window() {
            startX = startXDefault;
            startY = startYDefault;
            width = widthDefault;
            height = heightDefault;
            isMax = isMaxDefault;
        }

        public Window(JSONObject data) {
            startX = (double) data.get(KEY_START_X);
            startY = (double) data.get(KEY_START_Y);
            width  = (double) data.get(KEY_WIDTH);
            height = (double) data.get(KEY_HEIGHT);
            isMax  = (boolean) data.get(KEY_ISMAX);
        }

        public JSONObject exportJSON() {
            JSONObject res = new JSONObject();

            res.put(KEY_START_X, startX);
            res.put(KEY_START_Y, startY);
            res.put(KEY_WIDTH, width);
            res.put(KEY_HEIGHT, height);
            res.put(KEY_ISMAX, isMax);

            return res;
        }

        public Double getStartX() {
            return startX;
        }

        public void setStartX(Double startX) {
            this.startX = startX;
        }

        public Double getStartY() {
            return startY;
        }

        public void setStartY(Double startY) {
            this.startY = startY;
        }

        public Double getWidth() {
            return width;
        }

        public void setWidth(Double width) {
            this.width = width;
        }

        public Double getHeight() {
            return height;
        }

        public void setHeight(Double height) {
            this.height = height;
        }

        public Boolean getMax() {
            return isMax;
        }

        public void setMax(Boolean max) {
            isMax = max;
        }
    }

}
