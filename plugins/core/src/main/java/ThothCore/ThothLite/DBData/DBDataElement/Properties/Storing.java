package ThothCore.ThothLite.DBData.DBDataElement.Properties;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Parts.Countable;

/**
 * Объект реализует условную ячейку в которой хранится некоторое кол-во хранимого объекта.
 * */
public interface Storing
        extends Countable, Identifiable {

    Storagable getStoragable();
    void setStorageable(Storagable storageable);

}
