package ThothCore.ThothLite.Timer;

import ThothCore.ThothLite.DBData.DBDataElement.Finishable;

public interface Closeable {

    void close(Finishable finishable);

}
