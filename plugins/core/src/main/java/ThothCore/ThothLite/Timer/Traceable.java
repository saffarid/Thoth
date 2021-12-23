package ThothCore.ThothLite.Timer;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Finishable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.Tables.Data;

import java.util.List;
import java.util.concurrent.Flow;

public interface Traceable
        extends
        Flow.Publisher<Finishable>
{
    void notificationPlanning(List<Finishable> finishables);
}
