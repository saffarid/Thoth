package thoth_gui.thoth_lite.components.controls;

import java.time.LocalDate;

public class DatePicker {

    public static javafx.scene.control.DatePicker getInstance(){
        javafx.scene.control.DatePicker instance = new javafx.scene.control.DatePicker(LocalDate.now());
        return instance;
    }

    public static javafx.scene.control.DatePicker getInstance(LocalDate date){
        javafx.scene.control.DatePicker instance = new javafx.scene.control.DatePicker(date);
        return instance;
    }

}
