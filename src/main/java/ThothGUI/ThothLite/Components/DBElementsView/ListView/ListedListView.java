package ThothGUI.ThothLite.Components.DBElementsView.ListView;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;

import java.util.List;

public class ListedListView extends IdentifiablesListView<Listed>{

    protected ListedListView(
            List<Listed> datas
            , AvaliableTables table
    ) {
        super(datas);
        this.table = table;
    }

    @Override
    protected Listed getIdentifiableInstance() {
        return null;
    }
}
