package controls;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.concurrent.Flow;

public class ComboBox<T>
        extends javafx.scene.control.ComboBox<T>
        implements Flow.Subscriber<List<T>> {


    private Flow.Subscription subscription;

    public ComboBox() {
        super();
        init();
    }

    public ComboBox(ObservableList<T> observableList) {
        super(observableList);
        init();
    }

    private void init() {
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(List<T> item) {
        Platform.runLater(() -> {
            setItems( FXCollections.observableList(item) );
        });
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
    }

    @Override
    public void onComplete() {
    }
}
