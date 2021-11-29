package ThothGUI.ThothLite.Components.DBElementsView.ListView;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Orderable;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothGUI.ThothLite.Components.DBElementsView.ListView.IdentifiablesListView;
import javafx.scene.control.ListView;

import java.util.List;

public class OrderableListView extends IdentifiablesListView<Orderable> {

    public OrderableListView(List<Orderable> datas) {
        super(datas, AvaliableTables.ORDERABLE);
    }

}
