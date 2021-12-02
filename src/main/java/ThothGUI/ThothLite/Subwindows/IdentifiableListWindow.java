package ThothGUI.ThothLite.Subwindows;

import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothGUI.CloseSubwindow;
import ThothGUI.Closeable;
import ThothGUI.ThothLite.Components.DBElementsView.ListView.IdentifiablesListView;
import window.Subwindow;


public class IdentifiableListWindow
        extends Subwindow
        implements Closeable
{

    private CloseSubwindow closeSubwindow;

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

        setCloseEvent(event -> {
            closeSubwindow.closeSubwindow(this);
            instanceListView.onComplete();
        });
    }


    @Override
    public void setClose(CloseSubwindow close) {
        this.closeSubwindow = close;
    }
}
