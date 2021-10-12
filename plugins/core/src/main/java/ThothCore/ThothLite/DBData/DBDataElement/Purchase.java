package ThothCore.ThothLite.DBData.DBDataElement;

import ThothCore.ThothLite.DBData.Identifiable;
import ThothCore.ThothLite.Finishable;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Purchase
        implements Finishable, Identifiable {

    public static final int DELIVERED = 1;

    private String orderNumber;
    private Partner store;
    private Date deliveryDate;
    private boolean isDelivered;
    private List<StorageCell> purchasedProducts;

    public Purchase(String orderNumber, Partner store, Date deliveryDate, boolean isDelivered) {
        this.orderNumber = orderNumber;
        this.store = store;
        this.deliveryDate = deliveryDate;
        this.isDelivered = isDelivered;
        purchasedProducts = new LinkedList<>();
    }

    public void addProduct(Product product, Double count, ListElement countType) {
        StorageCell cell = new StorageCell(product, count, countType);
        purchasedProducts.add(cell);
    }

    public void removeProduct(Product product) {
        for (StorageCell cell : purchasedProducts){
            if ( cell.getProduct().equals(product) ){
                purchasedProducts.remove(cell);
            }
        }
    }

    @Override
    public String getId() {
        return orderNumber;
    }

    public List<StorageCell> getPurchasedProducts() {
        return purchasedProducts;
    }

    public Partner getStore() {
        return store;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setStore(Partner store) {
        this.store = store;
    }

    @Override
    public Date finishDate() {
        return deliveryDate;
    }

    @Override
    public void finish() {

    }

    @Override
    public String message() {
        return getId();
    }
}
