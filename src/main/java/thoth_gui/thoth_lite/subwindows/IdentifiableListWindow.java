package thoth_gui.thoth_lite.subwindows;

import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_gui.thoth_lite.components.scenes.db_elements_view.list_view.IdentifiablesListView;


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
