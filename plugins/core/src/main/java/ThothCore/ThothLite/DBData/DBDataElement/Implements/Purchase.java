package ThothCore.ThothLite.DBData.DBDataElement.Implements;

import ThothCore.ThothLite.DBData.DBDataElement.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Purchasable;
import ThothCore.ThothLite.DBData.DBDataElement.Storing;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Purchase
        implements Purchasable {

    public static final int DELIVERED = 1;

    private String orderNumber;
    private String storeId;
    private Date deliveryDate;
    private boolean isDelivered;
    private List<Storing> purchasedProducts;

    public Purchase(String orderNumber, Identifiable store, Date deliveryDate, boolean isDelivered) {
        this.orderNumber = orderNumber;
        this.storeId = store.getId();
        this.deliveryDate = deliveryDate;
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
    public Date finishDate() {
        return deliveryDate;
    }

    @Override
    public String message() {
        return null;
    }

    @Override
    public void setFinishDate(Date finishDate) {
        this.deliveryDate = finishDate;
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
    public String getPartnerId() {
        return storeId;
    }

    @Override
    public void setPartner(Identifiable partner) {
        this.storeId = partner.getId();
    }

}
