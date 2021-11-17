package ThothCore.ThothLite.DBData.DBDataElement.Implements;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Partnership;

public class Partner
        implements Partnership
{

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

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getWeb() {
        return web;
    }

    @Override
    public void setWeb(String web) {
        this.web = web;
    }

    @Override
    public void setIdInTable(Object idInTable) {
        setId((String) idInTable);
    }

    @Override
    public Object getIdInTable() {
        return getId();
    }
}
