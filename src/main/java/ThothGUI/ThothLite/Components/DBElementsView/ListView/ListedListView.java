package ThothGUI.ThothLite.Components.DBElementsView.ListView;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;

import java.util.List;

public class ListedListView extends IdentifiablesListView<Listed>{

    protected ListedListView(List<Listed> datas) {
        super(datas);
    }

    @Override
    protected Listed getIdentifiableInstance() {
        return null;
    }
}
