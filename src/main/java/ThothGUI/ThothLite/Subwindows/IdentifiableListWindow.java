package ThothGUI.ThothLite.Subwindows;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;
import ThothCore.ThothLite.DataTables;
import ThothGUI.CloseSubwindow;
import ThothGUI.ThothLite.Components.DBElementsView.ListView.IdentifiablesListView;
import ThothGUI.ThothLite.ThothLiteWindow;
import window.Subwindow;

import java.util.LinkedList;

public class IdentifiableListWindow extends Subwindow {

    public IdentifiableListWindow(
            String title
            , DataTables type
    ) {
        super(title);
        setId(title);
        setCloseEvent( event -> ((CloseSubwindow)ThothLiteWindow.getInstance()).closeSubwindow(this) );

        setCenter( IdentifiablesListView.getInstance(
                type
        ));
    }
}
