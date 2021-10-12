package ThothCore.ThothLite.Timer;

import ThothCore.ThothLite.Finishable;

@FunctionalInterface
public interface NotifySubscribers {

    void notifySubscribers(Finishable finishable);

}
