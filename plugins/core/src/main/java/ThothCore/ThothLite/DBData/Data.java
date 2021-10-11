package ThothCore.ThothLite.DBData;

import ThothCore.ThothLite.TableReadable;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class Data<T extends Identifiable> implements TableReadable {

    protected String name;

    protected List<T> datas;

    public Data() {
        datas = new LinkedList<>();
    }

    public void addData(T data){
        if(!contains(data)) datas.add(data);
    };

    public boolean contains(T data){
        return datas
                .stream()
                .filter(t -> t.equals(data))
                .findFirst()
                .isPresent();
    }

    public T getById(String id){
        Optional<T> element = datas.stream().filter(t -> t.getId().equals(id)).findFirst();
        return (element.isPresent())?
                (element.get()):
                (null);
    }

    public List<T> getDatas(){
        return datas;
    }

    public String getName() {
        return name;
    }

    public void removeData(T data){
        if(contains(data))datas.remove(data);
    }

}
