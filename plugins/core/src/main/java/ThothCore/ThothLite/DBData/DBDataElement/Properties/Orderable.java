package ThothCore.ThothLite.DBData.DBDataElement.Properties;

public interface Orderable
        extends Identifiable
        , HasPartner
        , Startable
        , Finishable {

    Projectable getProjectable();
    void setProjectable(Projectable projectable);
    Listed getStatus();
    void setStatus(Listed status);
}
