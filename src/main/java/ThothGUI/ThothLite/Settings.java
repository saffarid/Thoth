package ThothGUI.ThothLite;

import ThothCore.ThothLite.PeriodAutoupdateDatabase;
import controls.ComboBox;
import controls.Label;
import controls.Toggle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import layout.basepane.HBox;
import layout.basepane.VBox;
import window.SecondaryWindow;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class Settings extends SecondaryWindow {

    enum DEFAULT_SIZE {
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
    enum COMBO_BOXES_ID{
        DELAY_REREAD_DATABASE("delay_reread_database"),
        FONT_FAMILY("font_family"),
        FONT_SIZE("font_size"),
        ;
        private String id;
        COMBO_BOXES_ID(String id) {
            this.id = id;
        }
    }

    private Font newFont;

    public Settings(Stage stage, String title) {
        super(stage, title);

        setCenter(fillCenter());

    }

    private Node fillCenter(){
        VBox res = new VBox();
        res.setPadding(new Insets(2));
        res.setSpacing(2);
        res.setFillWidth(true);

        res.getChildren().addAll(
                fontConfig()
                , databaseConfig()
        );

        return res;
    }

    private Node databaseConfig(){
        VBox res = new VBox();
        res.setAlignment(Pos.TOP_LEFT);

        res.getChildren().addAll(
                getTitle("database")
                , gethBox(new Label("autoupdate"), new Toggle(false))
                , gethBox(new Label("autoupdate after transaction"), new Toggle(false))
                , gethBox(new Label(COMBO_BOXES_ID.DELAY_REREAD_DATABASE.id), getComboBox(COMBO_BOXES_ID.DELAY_REREAD_DATABASE))

        );

        return res;
    }
    private Node fontConfig(){
        VBox res = new VBox();

        FlowPane font = new FlowPane();
        font.setAlignment(Pos.CENTER_LEFT);
        font.setHgap(10);
        font.setVgap(2);
        font.setPadding(new Insets(2));

        HBox family = gethBox(
                new Label(COMBO_BOXES_ID.FONT_FAMILY.id)
                , getComboBox(COMBO_BOXES_ID.FONT_FAMILY)
        );

        HBox size = gethBox(
                new Label(COMBO_BOXES_ID.FONT_SIZE.id)
                , getComboBox(COMBO_BOXES_ID.FONT_SIZE)
        );

        font.getChildren().addAll(
                family
                , size
        );

        res.getChildren().addAll(
                getTitle("font")
                , font
        );

        return res;
    }

    private ComboBox getComboBox(COMBO_BOXES_ID id){
        ComboBox res = new ComboBox();
        res.setId(id.id);

        switch (id){
            case DELAY_REREAD_DATABASE:{
                res.setItems(
                        FXCollections.observableList(Arrays.asList(PeriodAutoupdateDatabase.values()))
                );
                break;
            }
            case FONT_FAMILY:{
                res.setPrefWidth(150);
                CompletableFuture.supplyAsync(() -> {
                    GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
                    List<String> collect = Arrays.stream(graphicsEnvironment.getAllFonts())
                            .map(font1 -> font1.getFamily())
                            .distinct()
                            .collect(Collectors.toList());
                    return collect;
                }).thenAccept(strings -> {
                    Platform.runLater(() -> res.setItems( FXCollections.observableList(strings) ));
                });
                break;
            }
            case FONT_SIZE:{
                for (int size = 10; size < 24; size += 2) {
                    res.getItems().add(size);
                }
                break;
            }
        }

        return res;
    }
    private HBox gethBox(Node titleControl, Node control) {
        HBox family = new HBox();
        family.setAlignment(Pos.CENTER_LEFT);
        family.setSpacing(4);
        family.getChildren().addAll(
                titleControl
                , control
        );
        return family;
    }

    private Node getTitle(String mes){

        Label label = new Label(mes);
        label.setPadding(0, 0, 0, 5);
        VBox res = new VBox();
        res.setPadding(new Insets(2));
        setMargin(res, new Insets(2,2,5,2));
        res.getChildren().add(label);
        res.setStyle("-fx-border-color:gray;" +
                "-fx-border-style:solid;" +
                "-fx-border-width:0 0 1px 0;");
        return res;
    }

}
