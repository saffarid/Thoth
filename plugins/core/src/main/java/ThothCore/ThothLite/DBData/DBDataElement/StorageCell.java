package ThothCore.ThothLite.DBData.DBDataElement;

import ThothCore.ThothLite.DBData.Identifiable;

public class StorageCell
        implements Identifiable {

    private String id;
    private Product product;
    private Double count;
    private ListElement countType;

    public StorageCell(Product product, Double count, ListElement countType) {
        this.product = product;
        this.count = count;
        this.countType = countType;
    }

    @Override
    public String getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public ListElement getCountType() {
        return countType;
    }

    public void setCountType(ListElement countType) {
        this.countType = countType;
    }
}
