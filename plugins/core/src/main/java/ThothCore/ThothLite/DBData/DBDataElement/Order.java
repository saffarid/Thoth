package ThothCore.ThothLite.DBData.DBDataElement;

import ThothCore.ThothLite.Finishable;

import java.util.Date;

public class Order implements Finishable {

    private String id;
    private Partner customer;
    private Project project;
    private boolean isMonthly;
    private Date startDate;
    private Date finishDate;
    private String status;
    private boolean autofinish;

    public Order(String id, Partner customer, Project project, boolean isMonthly, Date startDate, Date finishDate, String status, boolean autofinish) {
        this.id = id;
        this.customer = customer;
        this.project = project;
        this.isMonthly = isMonthly;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.status = status;
        this.autofinish = autofinish;
    }

    public Partner getCustomer() {
        return customer;
    }

    public String getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getStatus() {
        return status;
    }

    public boolean isAutofinish() {
        return autofinish;
    }

    public boolean isMonthly() {
        return isMonthly;
    }

    public void setAutofinish(boolean autofinish) {
        this.autofinish = autofinish;
    }

    public void setCustomer(Partner customer) {
        this.customer = customer;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMonthly(boolean monthly) {
        isMonthly = monthly;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Date finishDate() {
        return finishDate;
    }

    @Override
    public void finish() {

    }
}
