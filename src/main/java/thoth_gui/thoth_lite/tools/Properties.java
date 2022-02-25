package thoth_gui.thoth_lite.tools;

import main.Main;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class Properties {

    private static final String loadTemplateExp = "Load properties-%1$s: exception-%2$s";
    private static final String keyTemplateExp = "Request property by key-%1$s: exception-%2$s";

    private static final String propertiesName = "thoth";
    private static  ResourceBundle bundle;

    public static void loadProperties(Locale locale){
        try{
            bundle = ResourceBundle.getBundle(propertiesName, locale);
        }catch (NullPointerException exp){
            Main.LOG.log(
                    Level.INFO,
                    String.format(loadTemplateExp, propertiesName, exp.getMessage())
            );
        }catch (MissingResourceException exp){
            Main.LOG.log(
                    Level.INFO,
                    String.format(loadTemplateExp, propertiesName, exp.getMessage())
            );
        }
    }

    public static String getString(
            String key
            , TextCase textCase
    ){
        try{
            String string = bundle.getString(key.toLowerCase());

            switch (textCase){
                case UPPER: return string.toUpperCase();
                case LOWER: return string.toLowerCase();
                case NORMAL: {
                    StringBuilder builder = new StringBuilder("");
                    builder.append(String.valueOf(string.charAt(0)).toUpperCase())
                            .append(string.substring(1))
                    ;
                    return builder.toString();
                }

                case DEFAULT: default: return string;
            }
        }catch (NullPointerException exp){
            Main.LOG.log(
                    Level.INFO,
                    String.format(keyTemplateExp, key, exp.getMessage())
            );
        }catch (MissingResourceException exp){
            Main.LOG.log(
                    Level.INFO,
                    String.format(keyTemplateExp, key, exp.getMessage())
            );
        }catch (ClassCastException exp){
            Main.LOG.log(
                    Level.INFO,
                    String.format(keyTemplateExp, key, exp.getMessage())
            );
        }
        return key;
    }

}