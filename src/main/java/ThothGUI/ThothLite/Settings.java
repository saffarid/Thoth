package ThothGUI.ThothLite;

import javafx.stage.Stage;
import window.SecondaryWindow;

public class Settings extends SecondaryWindow {

    public enum DEFAULT_SIZE {
        HEIGHT(500),
        WIDTH(800);
        private double size;
        DEFAULT_SIZE(double size) {
            this.size = size;
        }
        public double getSize() {
            return size;
        }
    }

    public Settings(Stage stage, String title) {
        super(stage, title);
    }
}
