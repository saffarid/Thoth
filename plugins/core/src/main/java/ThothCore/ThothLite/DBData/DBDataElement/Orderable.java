package ThothCore.ThothLite.DBData.DBDataElement;

import ThothCore.ThothLite.DBData.DBDataElement.Purchasable;
import ThothCore.ThothLite.DBData.DBDataElement.Startable;

public interface Orderable
        extends Identifiable
        , HasPartner
        , Startable
        , Finishable {

    Projectable getProjectable();
    void setProjectable(Projectable projectable);

}
