package ThothCore.ThothLite.DBData.DBDataElement.Implements;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Projectable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storing;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Project
        implements Projectable {

    private String id;
    private String name;
    private List<Storing> usedProducts;
    private LocalDate createdDate;

    public Project(String id, String name) {
        this.id = id;
        this.name = name;
        this.usedProducts = new LinkedList<>();
        createdDate = LocalDate.now();
    }

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

    @Override
    public LocalDate startDate() {
        return createdDate;
    }

    @Override
    public void setStartDate(String startDate) {
        createdDate = LocalDate.parse(startDate);
    }

    @Override
    public void setIdInTable(Object idInTable) {

    }

    @Override
    public Object getIdInTable() {
        return null;
    }
}
