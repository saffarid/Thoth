package thoth_gui.thoth_lite.components.controls;

import javafx.collections.ObservableList;
import org.json.simple.parser.ParseException;
import thoth_gui.config.Config;

import java.io.IOException;

public class ComboBox {

    private static void bindFont(controls.ComboBox node) {

    }

    public static controls.ComboBox getInstance(){
        controls.ComboBox comboBox = new controls.ComboBox();
        bindFont(comboBox);
        return comboBox;
    }

    public static controls.ComboBox getInstance(ObservableList list){
        controls.ComboBox instance = getInstance();
        instance.setItems(list);
        return instance;
    }

}
