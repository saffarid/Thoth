package ThothCore.ThothLite.Timer;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Finishable;

public interface Closeable {
    void close(Finishable finishable);
}
