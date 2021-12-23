package ThothGUI.thoth_lite.main_window;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Finishable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import javafx.application.Platform;
import javafx.scene.control.ListView;
import layout.basepane.BorderPane;

import java.util.concurrent.Flow;

public class FinishableView
        extends BorderPane
        implements
        Flow.Subscriber<Finishable> {

    private Flow.Subscription subscription;

    private ListView list;

    public FinishableView() {
        list = new ListView();
        setCenter(list);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(Finishable item) {
        //Обработка
        Platform.runLater(() -> {
            list.getItems().add( ((Identifiable)item).getId() );
            list.refresh();
            this.subscription.request(1);
        });

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
