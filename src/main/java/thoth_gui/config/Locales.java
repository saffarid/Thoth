package thoth_gui.config;

import thoth_core.thoth_lite.config.ConfigEnums;

import java.util.Locale;

public enum Locales implements ConfigEnums<Locale> {

    RUSSIAN(new Locale.Builder().setLanguage("ru").build()),
    ENGLISH(new Locale.Builder().setLanguage("en").build()),
    FRENCH(new Locale.Builder().setLanguage("fr").build()),
    GERMAN(new Locale.Builder().setLanguage("de").build()),
    ITALIAN(new Locale.Builder().setLanguage("it").build()),
    ;

    private Locale locale;
    Locales(Locale locale) {
        this.locale = locale;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public Locale getValue() {
        return locale;
    }
}
