package ThothGUI.thoth_lite.subwindows;

import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothGUI.thoth_lite.components.db_elements_view.list_view.IdentifiablesListView;


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
