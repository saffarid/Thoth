package ThothCore.ThothLite.DBData.DBDataElement.Implements;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.*;

import java.time.LocalDate;

public class Order
        implements Orderable{

    private String id;
    private String orderNumber;
    private Partnership customer;
    private Projectable project;
    private boolean isMonthly;
    private LocalDate startDate;
    private LocalDate finishDate;
    private Listed status;
    private boolean autofinish;

    public Order(String id
            , String orderNumber
            , Partnership customer
            , Projectable project
            , boolean isMonthly
            , LocalDate startDate
            , LocalDate finishDate
            , Listed status
            , boolean autofinish) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.customer = customer;
        this.project = project;
        this.isMonthly = isMonthly;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.status = status;
        this.autofinish = autofinish;
    }

    @Override
    public LocalDate finishDate() {
        return finishDate;
    }

    @Override
    public void setFinishDate(LocalDate finishDate) {
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
    public boolean isFinish() {
        return false;
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
    public String orderNumber() {
        return orderNumber;
    }

    @Override
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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

}
