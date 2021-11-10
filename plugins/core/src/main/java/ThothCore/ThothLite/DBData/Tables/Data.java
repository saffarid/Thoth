package ThothCore.ThothLite.DBData.Tables;

import ThothCore.ThothLite.DBData.DBData;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Parts.Nameable;
import ThothCore.ThothLite.Exceptions.NotContainsException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class Data<T extends Identifiable>
        implements Nameable
        , TableReadable
{

    protected String name;

    protected List<T> datas;

    public Data() {
        datas = new LinkedList<>();
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

    public abstract List<HashMap<String, Object>> convertToMap(List<? extends Identifiable> list);

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

}
