package ThothGUI.thoth_lite.components.db_elements_view.list_view;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Projectable;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;

import java.util.List;

public class ProjectableListView extends IdentifiablesListView<Projectable> {

    public ProjectableListView(List<Projectable> datas) {
        super(datas, AvaliableTables.PROJECTABLE);
    }

}
