package thoth_gui.config;

import thoth_core.thoth_lite.config.ConfigEnums;

import java.util.Locale;

public enum Locales implements ConfigEnums<Locale> {

    RU(new Locale.Builder().setLanguage("ru").setRegion("RU").build()),
    ENG(Locale.UK),

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
