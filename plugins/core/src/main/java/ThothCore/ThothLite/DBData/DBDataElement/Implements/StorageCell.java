package ThothCore.ThothLite.DBData.DBDataElement.Implements;

import ThothCore.ThothLite.DBData.DBDataElement.Listed;
import ThothCore.ThothLite.DBData.DBDataElement.Storing;
import ThothCore.ThothLite.DBData.DBDataElement.Storagable;

public class StorageCell
        implements Storing {

    private String adress;
    private Storagable product;
    private Double count;
    private Listed countType;

    public StorageCell(
            Storagable product
            , Double count
            , Listed countType) {
        this.adress = null;
        this.product = product;
        this.count = count;
        this.countType = countType;
    }

    public StorageCell(
            String id
            , Storagable product
            , Double count
            , Listed countType) {
        this.adress = id;
        this.product = product;
        this.count = count;
        this.countType = countType;
    }

    @Override
    public Double getCount() {
        return count;
    }

    @Override
    public String getId() {
        return adress;
    }

    @Override
    public String getCountType() {
        return countType.getValue();
    }

    @Override
    public Storagable getStoragable() {
        return product;
    }

    @Override
    public void setId(String id) {
        this.adress = id;
    }

    @Override
    public void setCount(Double count) {

    }

    @Override
    public void setCountType(String countType) {

    }

    @Override
    public void setStorageable(Storagable storageable) {
        this.product = storageable;
    }
}
