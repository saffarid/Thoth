package thoth_core.thoth_lite.config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import thoth_core.thoth_lite.ThothLite;

import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;

public class Config {

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

    /**
     * Конфигурация работы системы оповещения
     * */
    private Delivered delivered;

    private Config()
            throws IOException, ParseException {

        configFile = new File(
                pathConfig + File.separator + fileConfigName
        );

        if(!configFile.exists()){
            database = new Database();
            delivered = new Delivered();

            exportConfig();
        }else{
            JSONObject parse = importConfig();
            database  = new Database ( (JSONObject) parse.get( Keys.Section.DATABASE.getKey() ) );
            delivered = new Delivered( (JSONObject) parse.get( Keys.Section.DELIVERY.getKey() ) );
        }
    }

    public JSONObject getConfig(){
        JSONObject config = new JSONObject();

        config.put( Keys.Section.DATABASE.getKey(), database.exportJSON() );
        config.put( Keys.Section.DELIVERY.getKey(), delivered.exportJSON() );

        return config;
    }

    public void exportConfig(){

        if(!configFile.getParentFile().exists()){
            configFile.getParentFile().mkdir();
        }
        try (FileWriter writer = new FileWriter(configFile)){
            writer.write( getConfig().toJSONString() );
        } catch (IOException e) {
            ThothLite.LOG.log(Level.INFO, "Fail export thoth config");
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

    public Delivered getDelivered() {
        return delivered;
    }

    public void setNewConfig(HashMap<String, Object> data){
        database .setNewConfig( (JSONObject) data.get(Keys.Section.DATABASE.getKey()) );
        delivered.setNewConfig( (JSONObject) data.get(Keys.Section.DELIVERY.getKey()) );
    }

    public ConfigEnums[] getConfigEnums(String key){

        if(key.equals(Keys.Database.DELAY_AUTOUPDATE.getKey())){
            return PeriodAutoupdateDatabase.values();
        } else if(key.equals(Keys.Delivery.DAY_BEFORE_DELIVERY.getKey())){
            return DayBeforeDelivery.values();
        }

        return null;
    }

    /**
     * Конфигурация работы базы данных
     */
    public class Database {

        /* --- Ключи конфигурации --- */

        /**
         * Ключ доступа к флагу автоматического перечитывания базы
         */
        private final String KEY_AUTOUPDATE = Keys.Database.AUTOUPDATE.getKey();
        /**
         * Ключ доступа к флагу автоматического перечитывания таблицы после выполнения транзакции
         */
        private final String KEY_UPDATE_AFTER_TRANS = Keys.Database.UPDATE_AFTER_TRANS.getKey();
        /**
         * Ключ доступа к значению задержки между автоматическим считыванием базы
         */
        private final String KEY_DELAY_AUTOUPDATE = Keys.Database.DELAY_AUTOUPDATE.getKey();

        /* --- Параметры конфигурации --- */

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
            setNewConfig(data);
        }

        public JSONObject exportJSON(){
            JSONObject res = new JSONObject();

            res.put(KEY_AUTOUPDATE, isAutoupdate);
            res.put(KEY_UPDATE_AFTER_TRANS, isUpdateAfterTrans);
            res.put(KEY_DELAY_AUTOUPDATE, period.toString());

            return res;
        }
        public void setNewConfig(JSONObject data){
            isAutoupdate = (boolean) data.get(KEY_AUTOUPDATE);
            isUpdateAfterTrans = (boolean) data.get(KEY_UPDATE_AFTER_TRANS);
            period = PeriodAutoupdateDatabase.valueOf((String) data.get(KEY_DELAY_AUTOUPDATE));
        }

        /* --- Getter --- */

        public boolean isAutoupdate() {
            return isAutoupdate;
        }
        public boolean isUpdateAfterTrans() {
            return isUpdateAfterTrans;
        }
        public PeriodAutoupdateDatabase getPeriod() {
            return period;
        }
    }

    /**
     * Конфигурация работы с объектами доставки
     * */
    public class Delivered{

        /* --- Ключи --- */

        private final String KEY_IS_CHECKDAY_BEFORE_DELIVERY = Keys.Delivery.IS_CHECKDAY_BEFORE_DELIVERY.getKey();
        private final String KEY_DAY_BEFORE_DELIVERY = Keys.Delivery.DAY_BEFORE_DELIVERY.getKey();

        /* --- Параметры конфигурации --- */

        private boolean checkDayBeforeDelivery;
        private final boolean checkDayBeforeDeliveryDefault = true;
        /**
         * За сколько дней перед доставкой система должна начать оповещять пользователя
         * */
        private DayBeforeDelivery dayBeforeDelivery;
        private final DayBeforeDelivery dayBeforeDeliveryDefault = DayBeforeDelivery.FIVE;

        public Delivered(){
            checkDayBeforeDelivery = checkDayBeforeDeliveryDefault;
            dayBeforeDelivery = dayBeforeDeliveryDefault;
        }
        public Delivered(JSONObject data){
            setNewConfig(data);
        }

        public JSONObject exportJSON(){
            JSONObject res = new JSONObject();

            res.put(KEY_IS_CHECKDAY_BEFORE_DELIVERY, checkDayBeforeDelivery);
            res.put(KEY_DAY_BEFORE_DELIVERY, dayBeforeDelivery.toString());

            return res;
        }
        public void setNewConfig(JSONObject data){
            checkDayBeforeDelivery = (boolean) data.get(KEY_IS_CHECKDAY_BEFORE_DELIVERY);
            dayBeforeDelivery = DayBeforeDelivery.valueOf( (String) data.get(KEY_DAY_BEFORE_DELIVERY) );
        }

        /* --- Getter --- */

        public boolean isCheckDayBeforeDelivery() {
            return checkDayBeforeDelivery;
        }
        public DayBeforeDelivery getDayBeforeDelivery() {
            return dayBeforeDelivery;
        }

        /* --- Setter --- */

        public void setCheckDayBeforeDelivery(boolean checkDayBeforeDelivery) {
            this.checkDayBeforeDelivery = checkDayBeforeDelivery;
        }
        public void setDayBeforeDelivery(DayBeforeDelivery dayBeforeDelivery) {
            this.dayBeforeDelivery = dayBeforeDelivery;
        }
    }
}
