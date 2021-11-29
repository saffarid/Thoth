package ThothGUI.ThothLite.Components.DBElementsView.ListView;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storing;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothGUI.ThothLite.Components.DBElementsView.ListView.IdentifiablesListView;
import javafx.scene.control.ListView;

import java.util.List;

public class StoringListView extends IdentifiablesListView<Storing> {

    public StoringListView(List<Storing> datas) {
        super(datas, AvaliableTables.STORING);
    }


}
