package ThothGUI.ThothLite.Components.DBElementsView.ListView;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Purchasable;
import javafx.scene.control.ListView;

import java.util.List;

public class PurchaseListView extends IdentifiablesListView<Purchasable> {

    public PurchaseListView(List<Purchasable> datas) {
        super(datas);
    }

}
