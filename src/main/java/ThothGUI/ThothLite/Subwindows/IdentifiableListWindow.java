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
        content = IdentifiablesListView.getInstance(
                type
        );
        setCenter(content);

    }

}
