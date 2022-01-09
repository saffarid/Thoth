package thoth_core.thoth_lite.db_data.tables;

import thoth_core.thoth_lite.db_data.DBData;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Identifiable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.parts.Nameable;
import thoth_core.thoth_lite.exceptions.NotContainsException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public abstract class Data<T extends Identifiable>
        implements Nameable
        , TableReadable
        , Flow.Publisher
{

    protected String name;
    protected List<T> datas;
    protected SubmissionPublisher<List<T>> publisher;

    public Data() {
        datas = new LinkedList<>();
        publisher = new SubmissionPublisher<>();
    }

    public void addData(T data) {
        if (!contains(data)) datas.add(data);
    }

    public boolean contains(T data) {
        return datas
                .stream()
                .filter(t -> t.equals(data))
                .findFirst()
                .isPresent();
    }

    public abstract HashMap< String, List< HashMap<String, Object> > > convertToMap(List<? extends Identifiable> list);

    public T getById(String id) throws NotContainsException {
        Optional<T> element = datas.stream().filter(t -> t.getId().equals(id)).findFirst();
        if(!element.isPresent()) throw new NotContainsException();
        return element.get();
    }

    public List<T> getDatas() {
        return datas;
    }

    public Identifiable getFromTableById(String tableName, String id) throws NotContainsException {
        return DBData.getInstance().getTable(tableName).getById(id);
    }

    public String getName() {
        return name;
    }

    public void removeData(T data) {
        if (contains(data)) datas.remove(data);
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public void subscribe(Flow.Subscriber subscriber) {
        System.out.println(publisher.getSubscribers().size());
        if(!publisher.isSubscribed(subscriber)) {
            publisher.subscribe(subscriber);
        }
    }
}
