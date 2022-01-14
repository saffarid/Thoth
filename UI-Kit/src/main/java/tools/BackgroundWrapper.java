package tools;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;

/**
 * Класс обёртка для упрощённого создания фона узла.
 * @version 1.0
 * @author PavlovNO
 * */
public class BackgroundWrapper {

    private Paint color;
    private CornerRadii radii = new CornerRadii(0);
    private double topInsets = 0;
    private double rightInsets = 0;
    private double bottomInsets = 0;
    private double leftInsets = 0;

    public BackgroundWrapper() {

    }

    public Background commit() {
        return
                new Background(
                    new BackgroundFill(
                            color
                            , radii
                            , new Insets(topInsets, rightInsets, bottomInsets, leftInsets)
                    )
                );
    }

    public BackgroundWrapper setColor(Paint color) {
        this.color = color;
        return this;
    }

    public BackgroundWrapper setInsets(double i) {
        topInsets = i;
        rightInsets = i;
        bottomInsets = i;
        leftInsets = i;
        return this;
    }

    public BackgroundWrapper setTopInsets(double i) {
        topInsets = i;
        return this;
    }

    public BackgroundWrapper setRightInsets(double i) {
        rightInsets = i;
        return this;
    }

    public BackgroundWrapper setBottomInsets(double i) {
        bottomInsets = i;
        return this;
    }

    public BackgroundWrapper setLeftInsets(double i) {
        leftInsets = i;
        return this;
    }

    public BackgroundWrapper setRadius(double r) {
        radii = new CornerRadii(r);
        return this;
    }

}
