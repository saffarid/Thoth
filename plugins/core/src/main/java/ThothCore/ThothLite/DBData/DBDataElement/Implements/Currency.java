package ThothCore.ThothLite.DBData.DBDataElement.Implements;

import ThothCore.ThothLite.DBData.DBDataElement.Identifiable;

public class Currency implements Identifiable {

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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getCourse() {
        return course;
    }

    public void setCourse(Double course) {
        this.course = course;
    }
}
