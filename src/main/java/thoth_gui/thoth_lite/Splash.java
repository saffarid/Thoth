package thoth_gui.thoth_lite;

import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import layout.basepane.StackPane;
import main.Main;
import thoth_gui.thoth_styleconstants.Stylesheets;


public class Splash extends StackPane {

    private final Double HEIGHT = 360.;
    private final Double WIDTH = 640.;

    private final Double SCALE = 1.;
    private final String url = "/img/splash.png";

    private final Image image = new Image(Main.class.getResource(url).toExternalForm(), WIDTH*SCALE, HEIGHT*SCALE, false, true);

    public Splash() {
        super();
        getStylesheets().add(Stylesheets.PROGRERSS_BAR.getStylesheet());
        ImageView e1 = new ImageView(image);
        ProgressBar node = new ProgressBar();

        widthProperty().addListener((observableValue, number, t1) -> {
            if(t1 == null) return;
            node.setPrefWidth(t1.doubleValue());
        });

        getChildren().addAll(
                e1,
                node
        );

        StackPane.setAlignment(node, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(e1, Pos.CENTER);
    }


}
