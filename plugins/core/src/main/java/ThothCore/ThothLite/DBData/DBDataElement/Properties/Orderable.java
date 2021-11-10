package ThothCore.ThothLite.DBData.DBDataElement.Properties;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Parts.HasPartner;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Parts.Startable;

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
