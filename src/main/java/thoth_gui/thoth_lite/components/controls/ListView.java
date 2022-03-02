package thoth_gui.thoth_lite.components.controls;

import thoth_gui.thoth_lite.tools.TextCase;

public class ListView {

    public static controls.ListView getInstance(){
        controls.ListView res = new controls.ListView();
        res.setPlaceholder(Label.getInstanse("no_elements", TextCase.NORMAL));
        return res;
    }

}
