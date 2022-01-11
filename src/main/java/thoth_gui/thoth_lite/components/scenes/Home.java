package thoth_gui.thoth_lite.components.scenes;

import layout.basepane.BorderPane;
import thoth_gui.thoth_lite.components.controls.Label;

public class Home
extends BorderPane {

    private static Home home;

    private Home() {
        super();
        setCenter(Label.getInstanse("Home page"));
    }

    public static Home getInstance(){
        if(home == null){
            home = new Home();
        }
        return home;
    }

}
