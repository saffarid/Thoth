package ThothCore.ThothLite.Config;

import ThothCore.ThothLite.PeriodAutoupdateDatabase;
import org.json.simple.JSONObject;

import java.io.File;

public class Config {

    /**
     * Ключи для доступа к разделам конфигурации в формате json
     */
    private enum Keys {
        DATABASE("database"),
        ;
        private String key;

        Keys(String key) {
            this.key = key;
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
    private final String fileConfigName = "thoth_core_config.json";

    private File configFile;

    /**
     * Конфигурация работы базы данных
     */
    private Database database;

    private Config() {
        configFile = new File(
                String.format(
                        "%1s%2s%3s", pathConfig, File.separator, fileConfigName
                )
        );

        if(!configFile.exists()){
            database = new Database();

        }else{
            importConfig();
        }
    }

    public Config getInstance() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    private void importConfig() {

    }

    /**
     * Конфигурация работы базы данных
     */
    class Database {

        /**
         * Ключ доступа к флагу автоматического перечитывания базы
         */
        private final String KEY_AUTOUPDATE = "autoupdate";
        /**
         * Ключ доступа к флагу автоматического перечитывания таблицы после выполнения транзакции
         */
        private final String KEY_UPDATE_AFTER_TRANS = "update_after_trans";
        /**
         * Ключ доступа к значению задержки между автоматическим считыванием базы
         */
        private final String KEY_DELAY_AUTOUPDATE = "delay_autoupdate";

        /**
         * Флаг автоматического считывания базы данных
         */
        private boolean isAutoupdate;

        /**
         * Флаг автоматического считывания после совершения транзакции
         */
        private boolean isUpdateAfterTrans;

        /**
         * Флаг автоматического периодического перечитывания БД
         */
        private PeriodAutoupdateDatabase period;

        public Database() {
            isAutoupdate = false;
            isUpdateAfterTrans = false;
            period = PeriodAutoupdateDatabase.NEVER;
        }

        public Database(JSONObject data) {

        }
    }

}
