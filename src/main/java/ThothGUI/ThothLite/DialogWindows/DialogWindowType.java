package ThothGUI.ThothLite.DialogWindows;

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
