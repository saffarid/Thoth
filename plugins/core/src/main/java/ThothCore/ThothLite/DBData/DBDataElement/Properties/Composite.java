package ThothCore.ThothLite.DBData.DBDataElement.Properties;

import ThothCore.ThothLite.DBData.DBDataElement.Storing;

import java.util.List;

public interface Composite {

    List<Storing> getComposition();

    void addStoring(Storing storing);
    boolean containsStoring(Storing storing);
    Storing getStoringByStoragableId(String id);
    Storing getStoringByStoringId(String id);
    boolean removeStoring(Storing storing);

}
