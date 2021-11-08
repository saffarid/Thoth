package ThothGUI.ThothLite.Subwindows;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;
import ThothCore.ThothLite.DataType;
import ThothGUI.CloseSubwindow;
import ThothGUI.ThothLite.Components.DBElementsView.ListView.IdentifiablesListView;
import ThothGUI.ThothLite.ThothLiteWindow;
import window.Subwindow;

import java.util.LinkedList;

public class IdentifiableListWindow extends Subwindow {

    public IdentifiableListWindow(
            String title
            , DataType type
    ) {
        super(title);
        setId(title);
        setCloseEvent( event -> ((CloseSubwindow)ThothLiteWindow.getInstance()).closeSubwindow(this) );

        setCenter( IdentifiablesListView.getInstance(
                type
                , new LinkedList<Storagable>()
        ));
    }
}
