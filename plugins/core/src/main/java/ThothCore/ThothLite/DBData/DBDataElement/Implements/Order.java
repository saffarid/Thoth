package ThothCore.ThothLite.DBData.DBDataElement.Implements;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.*;

import java.time.LocalDate;

public class Order
        implements Orderable{

    private String id;
    private Partnership customer;
    private Projectable project;
    private boolean isMonthly;
    private LocalDate startDate;
    private LocalDate finishDate;
    private Listed status;
    private boolean autofinish;

    public Order(String id
            , Partnership customer
            , Projectable project
            , boolean isMonthly
            , String startDate
            , String finishDate
            , Listed status
            , boolean autofinish) {
        this.id = id;
        this.customer = customer;
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
    public Partnership getPartner() {
        return customer;
    }

    @Override
    public void setPartner(Partnership partner) {
        this.customer = partner;
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

    @Override
    public Listed getStatus() {
        return status;
    }

    @Override
    public void setStatus(Listed status) {
        this.status = status;
    }

    @Override
    public void setIdInTable(Object idInTable) {

    }

    @Override
    public Object getIdInTable() {
        return null;
    }
}
