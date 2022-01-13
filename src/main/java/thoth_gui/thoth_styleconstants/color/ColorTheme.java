package thoth_gui.thoth_styleconstants.color;

import javafx.scene.paint.Color;

public interface ColorTheme {

    enum Theme{
        DARK,
        LIGHT
    }

    String nameTheme();

    Color PRIMARY();
    Color PRIMARY_HOVER();
    Color PRIMARY_DISABLED();

    Color SECONDARY();
    Color SECONDARY_HOVER();
    Color SECONDARY_DISABLED();


}
