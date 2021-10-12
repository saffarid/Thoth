package ThothCore.ThothLite.Timer;

import ThothCore.ThothLite.Finishable;

import java.util.List;
import java.util.concurrent.Flow;

public interface Traceable
        extends Flow.Publisher, Closeable{

    void setTraceableObjects(List<Finishable> finishables);

}
