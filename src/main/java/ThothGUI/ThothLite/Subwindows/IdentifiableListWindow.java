package ThothGUI.ThothLite.Subwindows;

import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothGUI.ThothLite.Components.DBElementsView.ListView.IdentifiablesListView;


public class IdentifiableListWindow
        extends Subwindow
{


    public IdentifiableListWindow(
            String title
            , AvaliableTables type
    ) {
        super(title);

        setId(title);
        IdentifiablesListView instanceListView = IdentifiablesListView.getInstance(
                type
        );
        setCenter(instanceListView);

    }

    @Override
    public void close() {
        closeSubwindow.closeSubwindow(this);
    }
}
