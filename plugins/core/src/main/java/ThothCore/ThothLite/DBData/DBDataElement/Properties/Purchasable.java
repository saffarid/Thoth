package ThothCore.ThothLite.DBData.DBDataElement.Properties;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Composite;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Finishable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.HasPartner;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;

public interface Purchasable
    extends Identifiable
        , Composite
        , HasPartner
        , Finishable {

}
