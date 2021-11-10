package ThothCore.ThothLite.DBData.DBDataElement.Properties;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Parts.Nameable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Parts.Pricing;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Parts.Typable;

/**
 * Объект, который возможно хранить, использовать в личных проектах.
 */
public interface Storagable
        extends Identifiable
        , Nameable
        , Typable
        , Pricing {
}
