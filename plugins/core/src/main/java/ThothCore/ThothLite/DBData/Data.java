package ThothCore.ThothLite.DBData;

import java.util.LinkedList;
import java.util.List;

public abstract class Data<T> {

    private List<T> datas;

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

    public List<T> getDatas(){
        return datas;
    }

    public void removeData(T data){
        if(contains(data))datas.remove(data);
    }


}
