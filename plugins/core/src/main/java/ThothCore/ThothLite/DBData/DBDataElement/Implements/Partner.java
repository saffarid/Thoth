package ThothCore.ThothLite.DBData.DBDataElement.Implements;

import ThothCore.ThothLite.DBData.DBDataElement.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Nameable;

public class Partner
        implements Identifiable
        , Nameable {

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

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
