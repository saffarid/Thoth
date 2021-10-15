package ThothCore.ThothLite.DBData.DBDataElement.Implements;

import ThothCore.ThothLite.DBData.DBDataElement.Projectable;
import ThothCore.ThothLite.DBData.DBDataElement.Storing;

import java.util.List;

public class Project
        implements Projectable {

    private String id;
    private String name;
    private List<Storing> usedProducts;

    @Override
    public List<Storing> getComposition() {
        return usedProducts;
    }

    @Override
    public void addStoring(Storing storing) {
        usedProducts.add(storing);
    }

    @Override
    public boolean containsStoring(Storing storing) {
        return usedProducts.contains(storing);
    }

    @Override
    public Storing getStoringByStoragableId(String id) {
        for(Storing storing : usedProducts){
            if(storing.getStoragable().getId().equals(id))
                return storing;
        }
        return null;
    }

    @Override
    public Storing getStoringByStoringId(String id) {
        for(Storing storing : usedProducts){
            if(storing.getId().equals(id))
                return storing;
        }
        return null;
    }

    @Override
    public boolean removeStoring(Storing storing) {
        return usedProducts.remove(storing);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
