package ThothCore.ThothLite.DBData;

import ThothCore.ThothLite.Storagable;

/**
 * Объект для хранения какой-либо хранимой вещи
 * */
public interface Storing
        extends Countable{

    Storagable getStoragable();
    void setStorageable(Storagable storageable);

}
