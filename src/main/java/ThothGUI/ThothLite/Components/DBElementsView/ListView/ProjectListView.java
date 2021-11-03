package ThothGUI.ThothLite.Components.DBElementsView.ListView;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Projectable;
import javafx.scene.control.ListView;

import java.util.List;

public class ProjectListView extends IdentifiablesListView<Projectable> {

    public ProjectListView(List<Projectable> datas) {
        super(datas);
    }

}
