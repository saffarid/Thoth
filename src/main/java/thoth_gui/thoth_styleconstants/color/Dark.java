package thoth_gui.thoth_styleconstants.color;

import javafx.scene.paint.Color;

public class Dark implements ColorTheme{

    private final String name = "dark";
    enum Colors{
        PRIMARY("#343A40"),
        SECONDARY("#23272B"),
        ;
        private Color colorCode;
        Colors(String colorCode) {
            this.colorCode = Color.valueOf(colorCode);
        }
    }

    @Override
    public String nameTheme() {
        return name;
    }

    @Override
    public Color PRIMARY() {
        return Colors.PRIMARY.colorCode;
    }
    @Override
    public Color PRIMARY_HOVER() {
        return null;
    }
    @Override
    public Color PRIMARY_DISABLED() {
        return null;
    }

    @Override
    public Color SECONDARY() {
        return Colors.SECONDARY.colorCode;
    }
    @Override
    public Color SECONDARY_HOVER() {
        return null;
    }
    @Override
    public Color SECONDARY_DISABLED() {
        return null;
    }
}
