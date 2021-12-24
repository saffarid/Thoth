package thoth_core.thoth_lite.db_data.db_data_element.properties;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Parts.*;
import thoth_core.thoth_lite.db_data.db_data_element.properties.parts.Countable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.parts.Nameable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.parts.Typable;

/**
 * Объект, который возможно хранить, использовать в личных проектах.
 */
public interface Storagable
        extends Identifiable
        , Nameable
        , Typable
        , Countable
{

    void setAdress(Listed adress);
    Listed getAdress();

    void setNote(String note);
    String getNote();

}
