package thoth_gui.thoth_lite.components.converters;

import javafx.util.converter.NumberStringConverter;

public class StringDoubleConverter
        extends NumberStringConverter {

    public static final String priceRegEx = "^[0-9]*[.]?[0-9]{0,2}$";
    public static final String courseRegEx = "^[0-9]*[.]?[0-9]{0,4}$";
    public static final String countRegEx = "^[0-9]*[.]?[0-9]{0,4}$";

    public static final double priceMin = 0.01;
    public static final double courseMin = 0.0001;
    public static final double countMin = 0.0;

    @Override
    public Number fromString(String s) {
        return (s.equals("") || s.equals("."))?0.0:Double.parseDouble(s);
    }

}
