package thoth_gui.config;

public class Keys {

    /**
     * Ключи для доступа к разделам конфигурации
     * */
    public enum Section {
        /**
         * Ключ раздела конфигурации шрифта
         * */
        FONT("font"),
        /**
         * Ключ раздела конфигурации сцены
         * */
        SCENE("scene"),
        /**
         * Ключ раздела конфигурации окна
         * */
        WINDOW("window"),
        ;
        private String key;
        Section(String key) {
            this.key = key;
        }
        public String getKey() {
            return key;
        }
    }

    /**
     * Ключи конфигурации раздела шрифта
     * */
    public enum Font{
        /**
         * Ключ доступа к размеру шрифта
         * */
        SIZE("size"),
        /**
         * Ключ доступа к семейству шрифта
         * */
        FAMILY("family");
        private String key;
        Font(String key) {
            this.key = key;
        }
        public String getKey(){
            return key;
        }
    }

    /**
     * Ключи конфигурации раздела сцены
     * */
    public enum Scene{
        /**
         * Ключ доступа к цветовой схеме
         * */
        COLOR_THEME("color-theme"),
        ;
        private String key;
        Scene(String key) {
            this.key = key;
        }
        public String getKey(){
            return key;
        }
    }

}
