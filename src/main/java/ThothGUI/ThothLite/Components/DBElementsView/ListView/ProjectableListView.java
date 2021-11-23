package ThothGUI.ThothLite.Components.DBElementsView.ListView;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Projectable;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import javafx.scene.control.ListView;

import java.util.List;

public class ProjectableListView extends IdentifiablesListView<Projectable> {

    public ProjectableListView(List<Projectable> datas) {
        super(datas);
        table = AvaliableTables.PROJECTABLE;
    }

}
