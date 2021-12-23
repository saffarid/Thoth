package ThothGUI.thoth_lite.components.db_elements_view.list_view;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storing;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;

import java.util.List;

public class StoringListView extends IdentifiablesListView<Storing> {

    public StoringListView(List<Storing> datas) {
        super(datas, AvaliableTables.STORING);
    }


}
