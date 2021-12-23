package ThothGUI.thoth_lite.components.db_elements_view.list_view;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Orderable;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;

import java.util.List;

public class OrderableListView extends IdentifiablesListView<Orderable> {

    public OrderableListView(List<Orderable> datas) {
        super(datas, AvaliableTables.ORDERABLE);
    }

}
