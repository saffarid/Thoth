package ThothCore.ThothLite.DBData.DBDataElement;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Countable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;

/**
 * Объект реализует условную ячейку в которой хранится некоторое кол-во хранимого объекта.
 * */
public interface Storing
        extends Countable, Identifiable {

    Storagable getStoragable();
    void setStorageable(Storagable storageable);



}
