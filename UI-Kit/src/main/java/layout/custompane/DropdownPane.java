package layout.custompane;

import controls.Label;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import layout.basepane.BorderPane;

public class DropdownPane extends BorderPane {

    private final static String STYLE_SHEET = "/style/layout/panes/custom/dropdown_pane.css";

    private final static String STYLE_CLASS_HEADER = "header";
    private final static String STYLE_CLASS_CONTENT = "content";

    protected BorderPane header;

    private Pane content;

    private boolean contentIsHide = true;

    private boolean heightIsSet = false;


    public DropdownPane(String header,
                        Pane node) {
        super();

        contentConfig(node);
        createHeader(header);

        getStylesheets().addAll(
                getClass().getResource(STYLE_SHEET).toExternalForm()
        );
    }

    public void bindHeader(StringProperty text){
        ((Label)this.header.getLeft()).textProperty().bindBidirectional(text);
    }

    private void createHeader(String header) {
        this.header = new BorderPane();

        Label textHeader = new Label(header);
        Button roll = new Button();
        roll.setGraphic(
                new ImageView(
                        new Image(
                                getClass().getResource("/image/icons/arrow.png").toExternalForm(), 20, 20, true, true)
                )
        );

        this.header.setLeft(textHeader);
        this.header.setRight(roll);

        roll.setOnAction(this::rollUpDown);
        this.header.setOnMouseClicked(mouseEvent -> {
            switch (mouseEvent.getButton()){
                case PRIMARY:{
                    Event.fireEvent(roll, new ActionEvent());
                    break;
                }
            }
        });

        setTop(this.header);

        this.header.getStyleClass().addAll(
                STYLE_CLASS_HEADER
        );

    }

    private void contentConfig(Pane node){

        setContent(node);

        setCenter(content);

        content.heightProperty().addListener((observableValue, number, t1) -> {
            if(!heightIsSet){

                if(contentIsHide) {
                    content.setPrefHeight(0);
                    content.setVisible(false);
                }else{
                    content.setPrefHeight(content.getHeight());
                }

                content.setMinHeight(0);
                content.setMaxHeight(content.getHeight());

                heightIsSet = true;
            }
        });

        content.getStyleClass().addAll(
                STYLE_CLASS_CONTENT
        );

    }

    private void rollUpDown(ActionEvent event) {

        Button roll = (Button) event.getSource();
        ImageView graphic = (ImageView) roll.getGraphic();
        Timeline timeline = new Timeline();

        Duration durationEnd = new Duration(250);

        if(contentIsHide){

            timeline.getKeyFrames().addAll(
                    new KeyFrame(
                            Duration.ZERO, new KeyValue(graphic.rotateProperty(), 0, Interpolator.EASE_BOTH)
                    )
                    ,new KeyFrame(
                            durationEnd, new KeyValue(graphic.rotateProperty(), 180, Interpolator.EASE_BOTH)
                    )


                    ,new KeyFrame(
                            Duration.ZERO, new KeyValue(content.prefHeightProperty(), 0, Interpolator.EASE_BOTH)
                    )
                    ,new KeyFrame(
                            durationEnd, new KeyValue(content.prefHeightProperty(), content.getMaxHeight(), Interpolator.EASE_BOTH)
                    )

                    ,new KeyFrame(
                            Duration.ZERO, new KeyValue(content.visibleProperty(), false)
                    )
                    ,new KeyFrame(
                            new Duration(durationEnd.toMillis()*0.05), new KeyValue(content.visibleProperty(), true)
                    )
                    ,new KeyFrame(
                            durationEnd, new KeyValue(content.visibleProperty(), true)
                    )
            );

        }else{
            timeline.getKeyFrames().addAll(
                    new KeyFrame(
                            Duration.ZERO, new KeyValue(graphic.rotateProperty(), 180, Interpolator.EASE_BOTH)
                    )
                    ,new KeyFrame(
                            durationEnd, new KeyValue(graphic.rotateProperty(), 0, Interpolator.EASE_BOTH)
                    )

                    ,new KeyFrame(
                            Duration.ZERO, new KeyValue(content.prefHeightProperty(), content.getMaxHeight(), Interpolator.EASE_BOTH)
                    )
                    ,new KeyFrame(
                            durationEnd, new KeyValue(content.prefHeightProperty(), 0, Interpolator.EASE_BOTH)
                    )

                    ,new KeyFrame(
                            Duration.ZERO, new KeyValue(content.visibleProperty(), true)
                    )
                    ,new KeyFrame(
                            new Duration(durationEnd.toMillis()*0.95), new KeyValue(content.visibleProperty(), false)
                    )
                    ,new KeyFrame(
                            durationEnd, new KeyValue(content.visibleProperty(), false)
                    )
            );
        }

        timeline.play();
        contentIsHide = !contentIsHide;
    }

    public void setContent(Pane node){
        content = node;
        if(node == null){
            content = new Pane();
        }
    }

}
