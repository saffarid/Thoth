package ThothCore.ThothLite.Timer;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Finishable;

public class ScheduledTask implements Runnable{

    private NotifySubscribers notify;
    private Finishable finishable;

    public ScheduledTask(
            NotifySubscribers notify,
            Finishable finishable) {
        this.notify = notify;
        this.finishable = finishable;
    }

    @Override
    public void run() {
        notify.notifySubscribers(finishable);
    }

}
