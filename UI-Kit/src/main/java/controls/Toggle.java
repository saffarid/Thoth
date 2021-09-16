package controls;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

public class Toggle extends FlowPane {

    private final static String STYLE_SHEET = "/style/controls/toggle.css";
    private final static String STYLE_CLASS_TOGGLE = "toggle";
    private final static String STYLE_CLASS_POINT = "point";

    private Background toggleFalse;
    private Background toggleTrue;

    private Pane point;
    private SimpleBooleanProperty isTrue;

    public Toggle(boolean isTrue) {
        super();

        point = new Pane();
        toggleTrue = new Background(new BackgroundFill(Paint.valueOf("#28A745"), null, null));
        toggleFalse = new Background(new BackgroundFill(Paint.valueOf("#B00020"), null, null));

        setOnMouseClicked(this::changeState);
        this.isTrue = new SimpleBooleanProperty(!isTrue);

        getChildren().addAll(
                point
        );

        getStylesheets().addAll(
                getClass().getResource(STYLE_SHEET).toExternalForm()
        );
        getStyleClass().addAll(
                STYLE_CLASS_TOGGLE
        );
        point.getStyleClass().addAll(
                STYLE_CLASS_POINT
        );

        this.isTrue.addListener((observableValue, aBoolean, t1) -> {
            Timeline timeline = new Timeline();
            Duration duration = new Duration(250);

            double newX = getWidth() - getPadding().getLeft() - getPadding().getRight() - point.getWidth();

            if (t1) {
                timeline.getKeyFrames().addAll(
                        new KeyFrame(
                                Duration.ZERO, new KeyValue(point.translateXProperty(), 0)
                        )
                        , new KeyFrame(
                                duration, new KeyValue(point.translateXProperty(), newX)
                        )
                        , new KeyFrame(
                                Duration.ZERO, new KeyValue(backgroundProperty(), toggleFalse, Interpolator.EASE_OUT)
                        )
                        , new KeyFrame(
                                duration, new KeyValue(backgroundProperty(), toggleTrue, Interpolator.EASE_OUT)
                        )
                );
            } else {
                timeline.getKeyFrames().addAll(
                        new KeyFrame(
                                Duration.ZERO, new KeyValue(point.translateXProperty(), newX)
                        )
                        , new KeyFrame(
                                duration, new KeyValue(point.translateXProperty(), 0)
                        )
                        , new KeyFrame(
                                Duration.ZERO, new KeyValue(backgroundProperty(), toggleTrue, Interpolator.EASE_OUT)
                        )
                        , new KeyFrame(
                                duration, new KeyValue(backgroundProperty(), toggleFalse, Interpolator.EASE_OUT)
                        )
                );
            }
            timeline.play();
        });

        Platform.runLater(() -> {
            PauseTransition pauseTransition = new PauseTransition(
                    new Duration(100)
            );
            pauseTransition.setOnFinished(event -> {
                this.isTrue.set(isTrue);
            });
            pauseTransition.play();
        });

    }

    public boolean isIsTrue() {
        return isTrue.get();
    }

    public SimpleBooleanProperty isTrueProperty() {
        return isTrue;
    }

    private void changeState(MouseEvent mouseEvent) {
        switch (mouseEvent.getButton()) {
            case PRIMARY: {
                System.out.println("Клик мышой");
                isTrue.set(!isTrue.get());
                break;
            }
        }
    }
}
