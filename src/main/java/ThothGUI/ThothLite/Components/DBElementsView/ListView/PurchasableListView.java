package ThothGUI.ThothLite.Components.DBElementsView.ListView;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Purchasable;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import javafx.scene.control.ListView;

import java.util.List;

public class PurchasableListView extends IdentifiablesListView<Purchasable> {

    public PurchasableListView(List<Purchasable> datas) {
        super(datas);
        table = AvaliableTables.PURCHASABLE;
    }

    @Override
    protected Purchasable getIdentifiableInstance() {
        return null;
    }

}
