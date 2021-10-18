package ThothCore.ThothLite.DBData.DBDataElement.Implements;

import ThothCore.ThothLite.DBData.DBDataElement.Listed;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Orderable;
import ThothCore.ThothLite.DBData.DBDataElement.Projectable;

import java.time.LocalDate;
import java.util.Date;

public class Order
        implements Orderable{

    private String id;
    private String customer;
    private Projectable project;
    private boolean isMonthly;
    private LocalDate startDate;
    private LocalDate finishDate;
    private Listed status;
    private boolean autofinish;

    public Order(String id
            , Identifiable customer
            , Projectable project
            , boolean isMonthly
            , String startDate
            , String finishDate
            , Listed status
            , boolean autofinish) {
        this.id = id;
        this.customer = customer.getId();
        this.project = project;
        this.isMonthly = isMonthly;
        this.startDate = LocalDate.parse(startDate);
        this.finishDate = LocalDate.parse(finishDate);
        this.status = status;
        this.autofinish = autofinish;
    }

    @Override
    public LocalDate finishDate() {
        return finishDate;
    }

    @Override
    public void setFinishDate(String finishDate) {
        this.finishDate = LocalDate.parse(finishDate);
    }

    @Override
    public void finish() {

    }

    @Override
    public String message() {
        return id;
    }

    @Override
    public String getPartnerId() {
        return customer;
    }

    @Override
    public void setPartner(Identifiable partner) {
        this.customer = partner.getId();
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
    public LocalDate startDate() {
        return startDate;
    }

    @Override
    public void setStartDate(String startDate) {
        this.startDate = LocalDate.parse(startDate);
    }

    @Override
    public Projectable getProjectable() {
        return project;
    }

    @Override
    public void setProjectable(Projectable projectable) {
        this.project = projectable;
    }

}
