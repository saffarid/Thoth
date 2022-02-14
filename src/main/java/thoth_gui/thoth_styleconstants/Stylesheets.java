package thoth_gui.thoth_styleconstants;

public enum Stylesheets {

    DATE_PICKER("/style/date_picker.css"),
    CHECK_BOX("/style/check_box.css"),
    COLORS("/style/colors.css"),
    COMBO_BOX("/style/combo_box.css"),
    IDENTIFIABLE_LIST("/style/identifiable-list.css"),
    LABEL("/style/label.css"),
    LIST_VIEW("/style/list_view.css"),
    SCROLL_BAR("/style/scroll_bar.css"),
    TABLE_VIEW("/style/table_view.css"),
    TEXT_FIELD("/style/text_field.css"),
    TITLE("/style/title.css"),
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
