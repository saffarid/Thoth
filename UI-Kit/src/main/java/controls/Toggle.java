package controls;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

public class Toggle extends StackPane {

    private final static String STYLE_SHEET = "/style/controls/toggle.css";
    private final static String STYLE_CLASS_TOGGLE = "toggle";
    private final static String STYLE_CLASS_POINT = "point";

    private Background toggleFalse;
    private Background toggleTrue;

    private FlowPane back;

    private Pane point;
    private SimpleBooleanProperty isTrue;

    public Toggle(boolean isTrue) {
        super();

        back = new FlowPane();
        point = new Pane();
        toggleTrue = new Background(new BackgroundFill(Paint.valueOf("#28A745"), new CornerRadii(12), null));
        toggleFalse = new Background(new BackgroundFill(Paint.valueOf("#424242"), new CornerRadii(12), null));

        StackPane.setAlignment(point, Pos.CENTER_LEFT);

        setBackground(toggleTrue);
        back.setBackground(toggleFalse);

        setOnMouseClicked(this::changeState);
        this.isTrue = new SimpleBooleanProperty(!isTrue);

        getChildren().addAll(
                back,
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
            Duration duration = new Duration(100);

            double newX = getWidth() - point.getWidth() - 3;

            if (t1) {
                timeline.getKeyFrames().addAll(
                        new KeyFrame(
                                Duration.ZERO, new KeyValue(point.translateXProperty(), 2)
                        )
                        , new KeyFrame(
                                duration, new KeyValue(point.translateXProperty(), newX)
                        )
//                        , new KeyFrame(
//                                Duration.ZERO, new KeyValue(backgroundProperty(), toggleFalse, Interpolator.LINEAR)
//                        )
//                        , new KeyFrame(
//                                duration, new KeyValue(backgroundProperty(), toggleTrue, Interpolator.LINEAR)
//                        )
                        ,new KeyFrame(
                                Duration.ZERO, new KeyValue(back.opacityProperty(), 1)
                        )
                        ,new KeyFrame(
                                duration, new KeyValue(back.opacityProperty(), 0)
                        )
                );
            } else {
                timeline.getKeyFrames().addAll(
                        new KeyFrame(
                                Duration.ZERO, new KeyValue(point.translateXProperty(), newX)
                        )
                        , new KeyFrame(
                                duration, new KeyValue(point.translateXProperty(), 2)
                        )
//                        , new KeyFrame(
//                                Duration.ZERO, new KeyValue(backgroundProperty(), toggleTrue, Interpolator.EASE_OUT)
//                        )
//                        , new KeyFrame(
//                                duration, new KeyValue(backgroundProperty(), toggleFalse, Interpolator.EASE_OUT)
//                        )
                        ,new KeyFrame(
                                Duration.ZERO, new KeyValue(back.opacityProperty(), 0)
                        )
                        ,new KeyFrame(
                                duration, new KeyValue(back.opacityProperty(), 1)
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
                isTrue.set(!isTrue.get());
                break;
            }
        }
    }
}
