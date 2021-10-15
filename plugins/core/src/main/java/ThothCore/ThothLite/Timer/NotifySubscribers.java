package ThothCore.ThothLite.Timer;

import ThothCore.ThothLite.DBData.DBDataElement.Finishable;

@FunctionalInterface
public interface NotifySubscribers {

    void notifySubscribers(Finishable finishable);

}
