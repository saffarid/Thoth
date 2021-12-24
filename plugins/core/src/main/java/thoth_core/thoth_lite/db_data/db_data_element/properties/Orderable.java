package thoth_core.thoth_lite.db_data.db_data_element.properties;

import thoth_core.thoth_lite.db_data.db_data_element.properties.parts.HasPartner;
import thoth_core.thoth_lite.db_data.db_data_element.properties.parts.Startable;

public interface Orderable
        extends Identifiable
        , HasPartner
        , Startable
        , Finishable {

    String orderNumber();
    void setOrderNumber(String orderNumber);
    Projectable getProjectable();
    void setProjectable(Projectable projectable);
    Listed getStatus();
    void setStatus(Listed status);
}
