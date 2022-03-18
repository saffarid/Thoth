package thoth_gui.config;

import thoth_core.thoth_lite.config.ConfigEnums;

public enum ColorTheme
    implements ConfigEnums<String>
{
    DARK("dark"),
    LIGHT("light");

    private String theme;
    ColorTheme(String theme) {
        this.theme = theme;
    }
    @Override
    public String getName() {
        return toString();
    }
    @Override
    public String getValue() {
        return theme;
    }
}
