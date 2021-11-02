package ThothGUI.ThothLite.Components.DBElementsView.ListView;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import javafx.scene.control.ListView;
import layout.basepane.BorderPane;

public abstract class IdentifiablesListView
        extends BorderPane {


    protected ListView<? extends Identifiable> identifiableElementList;


    public IdentifiablesListView() {
        super();
    }
}
