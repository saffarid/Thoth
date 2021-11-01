package ThothCore.ThothLite.DBData.DBDataElement.Implements;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Partnership;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Purchasable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storing;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Purchase
        implements Purchasable {

    public static final int DELIVERED = 1;

    private String orderNumber;
    private Partnership store;
    private LocalDate deliveryDate;
    private boolean isDelivered;
    private List<Storing> purchasedProducts;

    public Purchase(String orderNumber, Partnership store, String deliveryDate, boolean isDelivered) {
        this.orderNumber = orderNumber;
        this.store = store;
        this.deliveryDate = LocalDate.parse(deliveryDate);
        this.isDelivered = isDelivered;
        purchasedProducts = new LinkedList<>();
    }

    @Override
    public String getId() {
        return orderNumber;
    }

    @Override
    public void finish() {
        isDelivered = true;
    }

    @Override
    public LocalDate finishDate() {
        return deliveryDate;
    }

    @Override
    public String message() {
        return null;
    }

    @Override
    public void setFinishDate(String finishDate) {
        this.deliveryDate = LocalDate.parse(finishDate);
    }

    @Override
    public void setId(String id) {
        this.orderNumber = id;
    }

    @Override
    public List<Storing> getComposition() {
        return purchasedProducts;
    }

    @Override
    public void addStoring(Storing storing) {
        purchasedProducts.add(storing);
    }

    @Override
    public boolean containsStoring(Storing storing) {
        return purchasedProducts.contains(storing);
    }

    @Override
    public Storing getStoringByStoragableId(String id) {
        for(Storing storing : purchasedProducts){
            if(storing.getStoragable().getId().equals(id))
                return storing;
        }
        return null;
    }

    @Override
    public Storing getStoringByStoringId(String id) {
        for(Storing storing : purchasedProducts){
            if(storing.getId().equals(id))
                return storing;
        }
        return null;
    }

    @Override
    public boolean removeStoring(Storing storing) {
        return purchasedProducts.remove(storing);
    }

    @Override
    public Partnership getPartner() {
        return store;
    }

    @Override
    public void setPartner(Partnership partner) {
        this.store = partner;
    }

    @Override
    public boolean isDelivered() {
        return isDelivered;
    }
}
