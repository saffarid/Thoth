package ThothGUI.ThothLite.Components.DBElementsView.ListView;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Orderable;
import javafx.scene.control.ListView;

import java.util.List;

public class OrderListView extends IdentifiablesListView<Orderable> {

    public OrderListView(List<Orderable> datas) {
        super(datas);
    }

}
