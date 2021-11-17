package ThothCore.ThothLite.DBData.DBDataElement.Properties;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Parts.Composite;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Parts.IdentifiableInTable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Parts.Nameable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Parts.Startable;

public interface Projectable
        extends Nameable
        , IdentifiableInTable
        , Composite
        , Identifiable
        , Startable
{

}
