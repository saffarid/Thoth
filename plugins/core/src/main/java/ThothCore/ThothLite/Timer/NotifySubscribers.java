package ThothCore.ThothLite.Timer;

import ThothCore.ThothLite.DBData.Finishable;

@FunctionalInterface
public interface NotifySubscribers {

    void notifySubscribers(Finishable finishable);

}
