package controls;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.concurrent.Flow;

public class ComboBox<T>
        extends javafx.scene.control.ComboBox<T>
        implements Flow.Subscriber<List<T>> {

    private Flow.Subscription subscription;
    private final SimpleListProperty<T> datas = new SimpleListProperty<>();
    private final ChangeListener<ObservableList<T>> defaultListener = (observableValue, ts, t1) -> { getItems().setAll(datas.getValue()); };
    private ChangeListener<ObservableList<T>> currentListener ;

    public ComboBox() {
        super();
        init();
    }

    public ComboBox(ObservableList<T> observableList) {
        this();
        datas.setValue( observableList );
    }

    public void close(){
        this.subscription.cancel();
    }

    public SimpleListProperty<T> getDataProperty(){
        return datas;
    }

    private void init() {
        setDefaultChangeListener();
    }

    public ComboBox<T> setData(List<T> data){
        datas.setValue( FXCollections.observableList(data) );
        return this;
    }

    public ComboBox<T> setDefaultChangeListener(){
        setChangeListener(defaultListener);
        return this;
    }

    public ComboBox<T> setChangeListener(ChangeListener<ObservableList<T>> data){
        if(currentListener != null) datas.removeListener(currentListener);
        currentListener = data;
        datas.addListener(data);
        return this;
    }

    /* --- Работа с подпиской --- */
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(List<T> item) {
        setData(item);
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
    }

    @Override
    public void onComplete() {
    }
}
