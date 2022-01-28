package styleconstants.imagesvg;

import javafx.scene.Group;

public class Empty {

    public static Group getInstance() {
        return new Group(
                TransparentBackground.getInstance()
        );
    }

}
