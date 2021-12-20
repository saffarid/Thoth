package ThothCore.ThothLite.Config;

import ThothCore.ThothLite.PeriodAutoupdateDatabase;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

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

    private Config()
            throws IOException, ParseException {

        configFile = new File(
                pathConfig + File.separator + fileConfigName
        );

        if(!configFile.exists()){
            database = new Database();

            exportConfig();
        }else{
            JSONObject parse = importConfig();
            database = new Database( (JSONObject)parse.get(Keys.DATABASE.key) );
        }
    }

    public void exportConfig(){

        if(!configFile.getParentFile().exists()){
            configFile.getParentFile().mkdir();
        }
        JSONObject config = new JSONObject();

        config.put(Keys.DATABASE.key, database.exportJSON());

        try (FileWriter writer = new FileWriter(configFile)){
            writer.write(config.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Config getInstance()
            throws IOException, ParseException {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    private JSONObject importConfig()
            throws IOException, ParseException {
        FileReader reader = new FileReader(configFile);
        JSONParser parser = new JSONParser();
        return (JSONObject)parser.parse(reader);
    }

    public Database getDatabase() {
        return database;
    }

    /**
     * Конфигурация работы базы данных
     */
    public class Database {

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
            isAutoupdate = (boolean) data.get(KEY_AUTOUPDATE);
            isUpdateAfterTrans = (boolean) data.get(KEY_UPDATE_AFTER_TRANS);
            period = PeriodAutoupdateDatabase.valueOf((String) data.get(KEY_DELAY_AUTOUPDATE));
        }

        public JSONObject exportJSON(){
            JSONObject res = new JSONObject();

            res.put(KEY_AUTOUPDATE, isAutoupdate);
            res.put(KEY_UPDATE_AFTER_TRANS, isUpdateAfterTrans);
            res.put(KEY_DELAY_AUTOUPDATE, period.toString());

            return res;
        }

        public boolean isAutoupdate() {
            return isAutoupdate;
        }

        public void setAutoupdate(boolean autoupdate) {
            isAutoupdate = autoupdate;
        }

        public boolean isUpdateAfterTrans() {
            return isUpdateAfterTrans;
        }

        public void setUpdateAfterTrans(boolean updateAfterTrans) {
            isUpdateAfterTrans = updateAfterTrans;
        }

        public PeriodAutoupdateDatabase getPeriod() {
            return period;
        }

        public void setPeriod(PeriodAutoupdateDatabase period) {
            this.period = period;
        }
    }

}
