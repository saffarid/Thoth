package ThothCore.ThothLite.DBData.DBDataElement.Implements;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storing;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;

public class StorageCell
        implements Storing

{

    private String id;
    private Storagable product;
    private Double count;
    private Listed countType;
    private Double price;
    private Currency currency;

    public StorageCell(
            String id
            , Storagable product
            , Double count
            , Listed countType
            , Double price
            , Currency currency
    ) {
        this.id = id;
        this.product = product;
        this.count = count;
        this.countType = countType;
        this.price = price;
        this.currency = currency;
    }

    @Override
    public Double getCount() {
        return count;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Listed getCountType() {
        return countType;
    }

    @Override
    public Storagable getStoragable() {
        return product;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setCount(Double count) {
        this.count = count;
    }

    @Override
    public void setCountType(Listed countType) {
        this.countType = countType;
    }

    @Override
    public void setStorageable(Storagable storageable) {
        this.product = storageable;
    }

    @Override
    public Double getPrice() {
        return price;
    }

    @Override
    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
