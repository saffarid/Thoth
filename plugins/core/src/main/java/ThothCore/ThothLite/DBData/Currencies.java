package ThothCore.ThothLite.DBData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Currencies {

    private HashMap<String, Double> currency;

    public Currencies() {
        this.currency = new HashMap<>();
    }

    public void put(String currency, Double course){
        this.currency.put(currency, course);
    }

    public Double getCourse(String currency){
        return this.currency.get(currency);
    }

    public List<String> getCurrencies(){
        return new LinkedList<>(this.currency.keySet());
    }

}
