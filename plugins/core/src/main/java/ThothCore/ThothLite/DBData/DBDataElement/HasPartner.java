package ThothCore.ThothLite.DBData.DBDataElement;

import ThothCore.ThothLite.DBData.DBDataElement.Implements.Partner;

public interface HasPartner {

    String getPartnerId();
    void setPartner(Identifiable partner);

}
