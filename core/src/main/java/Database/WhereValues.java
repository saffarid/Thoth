package Database;

import java.util.HashMap;
import java.util.Iterator;

public class WhereValues extends HashMap<TableColumn, String> {
//    StringBuilder result = new StringBuilder("");
//    keySet().stream().forEach(key -> {
//        result.append(String.format(TEMPLATE, key, get(key)));
//    });
//        result.deleteCharAt(result.lastIndexOf(","))
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");

        Iterator<TableColumn> keys = this.keySet().iterator();
        while (keys.hasNext()) {
            TableColumn key = keys.next();
            if (keys.hasNext()) {
                result.append(String.format("`%1s`=\'%2s\'%3s", key.getName(), this.get(key), " and "));
            } else {
                String value = this.get(key);
                String format = String.format("`%1s`=\'%2s\'", key.getName(), value);
                if(value.length() == 1){
                    format = format.replace(" ", "");
                }
                result.append(format);
            }
        }
        return result.toString();
    }
}
