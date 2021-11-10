package ThothGUI.ThothLite.Subwindows;

import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothGUI.ThothLite.Components.DBElementsView.ListView.IdentifiablesListView;
import window.Subwindow;


public class IdentifiableListWindow extends Subwindow {

    public IdentifiableListWindow(
            String title
            , AvaliableTables type
    ) {
        super(title);

        setId(title);
        setCenter( IdentifiablesListView.getInstance(
                type
        ));
    }

}
