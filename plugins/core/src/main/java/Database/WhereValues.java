package Database;

import java.util.HashMap;
import java.util.Iterator;

public class WhereValues extends HashMap<String, String> {
//    StringBuilder result = new StringBuilder("");
//    keySet().stream().forEach(key -> {
//        result.append(String.format(TEMPLATE, key, get(key)));
//    });
//        result.deleteCharAt(result.lastIndexOf(","))
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");
        Iterator<String> keys = this.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            if (keys.hasNext()) {
                result.append(String.format("`%1s`=\'%2s\'%3s", key, this.get(key), " and "));
            } else {
                String value = this.get(key);
                String format = String.format("`%1s`=\'%2s\'", key, value);
                if(value.length() == 1){
                    format = format.replace(" ", "");
                }
                result.append(format);
            }
        }
        return result.toString();
    }
}
