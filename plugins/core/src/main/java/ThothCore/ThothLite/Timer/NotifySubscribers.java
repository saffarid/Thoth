package ThothCore.ThothLite.Timer;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Finishable;

@FunctionalInterface
public interface NotifySubscribers {

    void notifySubscribers(Finishable finishable);

}
