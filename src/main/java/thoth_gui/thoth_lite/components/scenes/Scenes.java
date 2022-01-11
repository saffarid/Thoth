package thoth_gui.thoth_lite.components.scenes;

public enum Scenes {
    HOME("home"),
    EXPENSES("expenses"),
    INCOMES("incomes"),
    PURCHASES("purchases"),
    STORAGABLE("storagable"),
    SYSTEM("system")
    ;

    private String sceneCode;

    Scenes(String sceneCode) {
        this.sceneCode = sceneCode;
    }

    public String getSceneCode() {
        return sceneCode;
    }
}
