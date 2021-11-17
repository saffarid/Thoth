package ThothGUI.ThothLite.Components.DBElementsView.ListView;

import ThothCore.ThothLite.DBData.DBDataElement.Implements.Currency;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;

import java.util.List;

public class StoragableListView extends IdentifiablesListView<Storagable>{

    public StoragableListView(List<Storagable> datas) {
        super(datas);
        table = AvaliableTables.STORAGABLE;
    }

    @Override
    protected Storagable getIdentifiableInstance() {
        return null;
    }

}
