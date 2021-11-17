package ThothCore.ThothLite.DBData.DBDataElement.Properties;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Parts.Countable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Parts.IdentifiableInTable;

/**
 * Объект реализует условную ячейку в которой хранится некоторое кол-во хранимого объекта.
 * */
public interface Storing
        extends Countable
        , Identifiable
        , IdentifiableInTable
{

    Storagable getStoragable();
    void setStorageable(Storagable storageable);

}
