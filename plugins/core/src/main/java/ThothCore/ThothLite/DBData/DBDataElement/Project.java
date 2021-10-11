package ThothCore.ThothLite.DBData.DBDataElement;

import ThothCore.ThothLite.DBData.Identifiable;

import java.util.HashMap;
import java.util.List;

public class Project
        implements Identifiable {

    private String id;
    private String name;
    private List<StorageCell> usedProducts;

    @Override
    public String getId() {
        return id;
    }
}
