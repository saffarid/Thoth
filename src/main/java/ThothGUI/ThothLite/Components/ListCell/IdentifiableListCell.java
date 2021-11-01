package ThothGUI.ThothLite.Components.ListCell;

import ThothCore.ThothLite.DBData.DBDataElement.*;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;

public interface IdentifiableListCell {

    void setImageIcon(String url);
    void setTextTitle(String text);
    void setTextSubtitle(String text);
    void setTextProperty(String text);


    static IdentifiableListCell getInstance(Identifiable identifiable){

        if (identifiable instanceof Storagable) {
            return new ProductListCell( (Storagable) identifiable );
        } else if (identifiable instanceof Storing) {
            return new StoringListCell( (Storing) identifiable );
        } else if (identifiable instanceof Purchasable) {
            return new PurchaseListCell( (Purchasable) identifiable );
        } else if (identifiable instanceof Projectable) {
            return new ProjectListCell( (Projectable) identifiable );
        } else if (identifiable instanceof Orderable) {
            return new OrderListCell( (Orderable) identifiable);
        }

        return null;
    }

}
