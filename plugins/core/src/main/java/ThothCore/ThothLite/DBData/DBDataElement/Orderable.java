package ThothCore.ThothLite.DBData.DBDataElement;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Finishable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.HasPartner;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Startable;

public interface Orderable
        extends Identifiable
        , HasPartner
        , Startable
        , Finishable {

    Projectable getProjectable();
    void setProjectable(Projectable projectable);

}
