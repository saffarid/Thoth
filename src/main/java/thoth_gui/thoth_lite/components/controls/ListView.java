package thoth_gui.thoth_lite.components.controls;

public class ListView {

    public static controls.ListView getInstance(){
        controls.ListView res = new controls.ListView();
        res.setPlaceholder(Label.getInstanse("no_elements"));
        return res;
    }

}
