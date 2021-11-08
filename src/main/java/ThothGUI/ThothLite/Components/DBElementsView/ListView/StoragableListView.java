package ThothGUI.ThothLite.Components.DBElementsView.ListView;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;
import javafx.scene.control.ListView;

import java.util.List;

public class StoragableListView extends IdentifiablesListView<Storagable>{

    public StoragableListView(List<Storagable> datas) {
        super(datas);

    }

}
