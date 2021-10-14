package ThothGUI.ThothLite.Subwindows;

import ThothGUI.CloseSubwindow;
import window.Subwindow;

public class Purchases
        extends Subwindow {

    public Purchases(
            String title
            , CloseSubwindow closeSubwindow) {
        super(title);
        setId(title);
        setCloseEvent(event -> closeSubwindow.closeSubwindow(this));
    }

}
