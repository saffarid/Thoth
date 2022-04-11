package thoth_gui.config;

import thoth_core.thoth_lite.config.ConfigEnums;

import java.util.Locale;

public enum Locales implements ConfigEnums<Locale> {

    RU(new Locale.Builder().setLanguage("ru").build()),
    ENG(new Locale.Builder().setLanguage("en").build()),
    FR(new Locale.Builder().setLanguage("fr").build()),
    DE(new Locale.Builder().setLanguage("de").build()),
    IT(new Locale.Builder().setLanguage("it").build()),
    ;

    private Locale locale;
    Locales(Locale locale) {
        this.locale = locale;
    }

    @Override
    public String getName() {
        return locale.toLanguageTag();
    }

    @Override
    public Locale getValue() {
        return locale;
    }
}
