package ThothGUI.thoth_lite.dialog_windows;

public enum DialogWindowType {

    ALARM("alarm"),
    CONFIRM("confirm"),
    INFO("info"),
    WARNING("warning");

    private String type;

    DialogWindowType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
