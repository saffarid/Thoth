package ThothCore.ThothLite.DBData.DBDataElement;

import ThothCore.ThothLite.DBData.Identifiable;

import java.util.Observable;
import java.util.concurrent.Flow;

public class Partner implements Identifiable {

    private String id;
    private String name;
    private String phone;
    private String web;

    public Partner(
            String id,
            String name,
            String phone,
            String web) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.web = web;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getWeb() {
        return web;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setWeb(String web) {
        this.web = web;
    }
}
