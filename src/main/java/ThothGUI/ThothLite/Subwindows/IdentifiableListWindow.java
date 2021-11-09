package ThothGUI.ThothLite.Subwindows;

import ThothCore.ThothLite.DataTables;
import ThothGUI.ThothLite.Components.DBElementsView.ListView.IdentifiablesListView;
import window.Subwindow;


public class IdentifiableListWindow extends Subwindow {

    public IdentifiableListWindow(
            String title
            , DataTables type
    ) {
        super(title);

        setId(title);
        setCenter( IdentifiablesListView.getInstance(
                type
        ));
    }
}
