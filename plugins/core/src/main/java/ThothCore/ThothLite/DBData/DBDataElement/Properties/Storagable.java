package ThothCore.ThothLite.DBData.DBDataElement.Properties;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Parts.*;

/**
 * Объект, который возможно хранить, использовать в личных проектах.
 */
public interface Storagable
        extends Identifiable
        , Nameable
        , Typable
        , Countable
        , IdentifiableInTable
{

    void setAdress(Listed adress);
    Listed getAdress();

}
