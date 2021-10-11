package ThothCore.ThothLite.DBData.DBDataElement;

import java.util.Observable;
import java.util.concurrent.Flow;

public class Partner {

    private String name;
    private String phone;
    private String web;

    public Partner(String name, String phone, String web) {
        this.name = name;
        this.phone = phone;
        this.web = web;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }
}
