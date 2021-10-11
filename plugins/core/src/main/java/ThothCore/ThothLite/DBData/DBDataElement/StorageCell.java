package ThothCore.ThothLite.DBData.DBDataElement;

public class StorageCell {

    private Product product;

    private Double count;

    private String countType;

    public StorageCell(Product product, Double count, String countType) {
        this.product = product;
        this.count = count;
        this.countType = countType;
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

    public String getCountType() {
        return countType;
    }

    public void setCountType(String countType) {
        this.countType = countType;
    }

}
