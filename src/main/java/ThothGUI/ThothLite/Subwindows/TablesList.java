package ThothGUI.ThothLite.Subwindows;

import Database.Table;
import ThothCore.ThothLite.ThothLite;
import javafx.scene.control.ListView;
import window.Subwindow;

public class TablesList extends Subwindow {


    public TablesList(String title, ThothLite thoth) {
        super(title);

        ListView<Table> tablesList = new ListView<>();


        setCenter(tablesList);

    }


}
