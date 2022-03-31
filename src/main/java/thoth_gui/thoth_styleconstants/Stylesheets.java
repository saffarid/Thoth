package thoth_gui.thoth_styleconstants;

public enum Stylesheets {

    BUTTON("/style/button.css"),
    COLORS("/style/colors.css"),
    LIST_VIEW("/style/list_view.css"),
    DATE_PICKER("/style/date_picker.css"),
    CHECK_BOX("/style/check_box.css"),
    COMBO_BOX("/style/combo_box.css"),
    LABEL("/style/label.css"),
    MENU_BUTTON("/style/menu_button.css"),
    NAVIGATION_MENU("/style/navigation_menu.css"),
    PROGRERSS_BAR("/style/progress-bar.css"),
    SCROLL_BAR("/style/scroll_bar.css"),
    SVG("/style/svg.css"),
    TABLE_VIEW("/style/table_view.css"),
    TEXT_FIELD("/style/text_field.css"),
    TITLE("/style/title.css"),
    TWIN("/style/twin.css"),
    WINDOW("/style/window.css"),
    ;

    private String stylesheet;

    Stylesheets(String stylesheet) {
        this.stylesheet = stylesheet;
    }

    public String getStylesheet(){
        return getClass().getResource(stylesheet).toExternalForm();
    }

}
