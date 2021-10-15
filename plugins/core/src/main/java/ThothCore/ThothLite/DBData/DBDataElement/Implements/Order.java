package ThothCore.ThothLite.DBData.DBDataElement.Implements;

import ThothCore.ThothLite.DBData.DBDataElement.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Finishable;
import ThothCore.ThothLite.DBData.DBDataElement.Orderable;
import ThothCore.ThothLite.DBData.DBDataElement.Projectable;

import java.util.Date;

public class Order
        implements Orderable{

    private String id;
    private String customer;
    private Projectable project;
    private boolean isMonthly;
    private Date startDate;
    private Date finishDate;
    private ListElement status;
    private boolean autofinish;

    public Order(String id
            , Identifiable customer
            , Project project
            , boolean isMonthly
            , Date startDate
            , Date finishDate
            , ListElement status
            , boolean autofinish) {
        this.id = id;
        this.customer = customer.getId();
        this.project = project;
        this.isMonthly = isMonthly;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.status = status;
        this.autofinish = autofinish;
    }

    @Override
    public Date finishDate() {
        return finishDate;
    }

    @Override
    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
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
    public Date startDate() {
        return startDate;
    }

    @Override
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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
