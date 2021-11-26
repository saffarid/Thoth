package ThothCore.ThothLite.DBData.DBDataElement.Implements;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Finance;

public class Currency implements Finance {

    private String id;
    private String currency;
    private Double course;

    public Currency(
            String id,
            String currency,
            Double course) {
        this.id = id;
        this.currency = currency;
        this.course = course;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) { }
    @Override
    public String getCurrency() {
        return currency;
    }
    @Override
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    @Override
    public Double getCourse() {
        return course;
    }
    @Override
    public void setCourse(Double course) {
        this.course = course;
    }
}
