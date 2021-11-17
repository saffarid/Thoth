package ThothCore.ThothLite.DBData.DBDataElement.Properties;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Parts.IdentifiableInTable;

public interface Listed
    extends Identifiable,
        IdentifiableInTable
{

    String getValue();
    void setValue(String value);

}
