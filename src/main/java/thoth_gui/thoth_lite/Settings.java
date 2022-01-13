package thoth_gui.thoth_lite;

import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.paint.Color;
import layout.BackgroundWrapper;
import layout.BorderWrapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import thoth_core.thoth_lite.config.Keys;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_core.thoth_lite.config.PeriodAutoupdateDatabase;
import thoth_core.thoth_lite.ThothLite;
import controls.ComboBox;
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
import thoth_gui.Apply;
import thoth_gui.Cancel;
import thoth_gui.config.Config;
import thoth_gui.thoth_lite.components.controls.Button;
import thoth_gui.thoth_lite.components.controls.ButtonBar;
import window.SecondaryWindow;

import thoth_gui.thoth_lite.components.controls.Label;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class Settings
        extends SecondaryWindow
        implements Apply
        , Cancel {



    enum DefaultSize {
        HEIGHT(500),
        WIDTH(800);
        private double size;

        DefaultSize(double size) {
            this.size = size;
        }

        public double getSize() {
            return size;
        }
    }

    enum ComboBoxesId {
        DELAY_REREAD_DATABASE("delay_reread_database"),
        FONT_FAMILY("font_family"),
        FONT_SIZE("font_size"),
        ;
        private String id;

        ComboBoxesId(String id) {
            this.id = id;
        }
    }

    enum SectorTitles {
        TEXT("text"),
        DATABASE("database"),
        DELIVERY("delivery"),
        ;
        private String title;

        SectorTitles(String title) {
            this.title = title;
        }
    }

    private HashMap<String, Object> jsonConfig;

    private Font newFont;

    public Settings(Stage stage, String title) {
        super(stage, title);
        try {
            jsonConfig = ThothLite.getInstance().getConfig();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NotContainsException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        setCenter( fillCenter() );

        javafx.scene.control.ButtonBar instance = ButtonBar.getInstance(
                event -> apply()
                , event -> cancel()
        );
        initStyle();
        setMargin(instance, new Insets(2));
        setBottom(
                instance
        );
    }

    @Override
    public void apply() {

    }

    @Override
    public void cancel() {
        close();
    }

    private Node databaseConfig() {
        VBox res = new VBox();
        res.setAlignment(Pos.TOP_LEFT);
        res.setPadding(new Insets(0, 0, 0, 5));
        res.setSpacing(5);

        res.getChildren().addAll(
                getTitle(SectorTitles.DATABASE.title)
                , gethBox(
                        Label.getInstanse("autoupdate")
                        , new Toggle(
                                (Boolean) ((JSONObject) jsonConfig.get(Keys.Section.DATABASE.getKey())).get(Keys.Database.AUTOUPDATE.getKey())
                        )
                )
                , gethBox(
                        Label.getInstanse("autoupdate after transaction")
                        , new Toggle(
                                (Boolean) ((JSONObject) jsonConfig.get(Keys.Section.DATABASE.getKey())).get(Keys.Database.UPDATE_AFTER_TRANS.getKey())
                        )
                )
                , gethBox(
                        Label.getInstanse(ComboBoxesId.DELAY_REREAD_DATABASE.id)
                        , getComboBox(ComboBoxesId.DELAY_REREAD_DATABASE)
                )

        );

        return res;
    }

    private Node deliveryConfig() {
        VBox res = new VBox();
        res.setAlignment(Pos.TOP_LEFT);
        res.setPadding(new Insets(0, 0, 0, 5));
        res.setSpacing(5);

        res.getChildren().addAll(
                getTitle(SectorTitles.DELIVERY.title)
                , gethBox(
                        Label.getInstanse("delivery system state")
                        , new Toggle(
                                (Boolean) ((HashMap<String, Object>) jsonConfig.get(Keys.Section.DELIVERY.getKey())).get(Keys.Delivery.IS_CHECKDAY_BEFORE_DELIVERY.getKey())
                        )
                )

        );

        return res;
    }

    private Node fillCenter() {
        VBox res = new VBox();
        res.setPadding(new Insets(2));
        res.setSpacing(2);
        res.setFillWidth(true);

        res.getChildren().addAll(
                fontConfig()
                , databaseConfig()
                , deliveryConfig()
        );

        return res;
    }

    private Node fontConfig() {
        VBox res = new VBox();
        res.setSpacing(5);

        FlowPane font = new FlowPane();
        font.setAlignment(Pos.CENTER_LEFT);
        font.setHgap(10);
        font.setVgap(2);
        font.setPadding(new Insets(2));

        HBox family = gethBox(
                Label.getInstanse(ComboBoxesId.FONT_FAMILY.id)
                , getComboBox(ComboBoxesId.FONT_FAMILY)
        );

        HBox size = gethBox(
                Label.getInstanse(ComboBoxesId.FONT_SIZE.id)
                , getComboBox(ComboBoxesId.FONT_SIZE)
        );

        font.getChildren().addAll(
                family
                , size
        );

        res.getChildren().addAll(
                getTitle(SectorTitles.TEXT.title)
                , font
        );

        return res;
    }

    private ComboBox getComboBox(ComboBoxesId id) {
        ComboBox res = new ComboBox();
        res.setId(id.id);

        switch (id) {
            case DELAY_REREAD_DATABASE: {
                res.setItems(
                        FXCollections.observableList(Arrays.asList(PeriodAutoupdateDatabase.values()))
                );
                res.setValue(
                        ((JSONObject) jsonConfig.get(Keys.Section.DATABASE.getKey())).get(Keys.Database.DELAY_AUTOUPDATE.getKey())
                );
                break;
            }
            case FONT_FAMILY: {
                res.setPrefWidth(150);
                CompletableFuture.supplyAsync(() -> {
                    GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
                    List<String> collect = Arrays.stream(graphicsEnvironment.getAllFonts())
                            .map(font1 -> font1.getFamily())
                            .distinct()
                            .collect(Collectors.toList());
                    return collect;
                }).thenAccept(strings -> {
                    Platform.runLater(() -> res.setItems(FXCollections.observableList(strings)));
                });
                break;
            }
            case FONT_SIZE: {
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

    private Node getTitle(String mes) {

        VBox res = new VBox();
        res.setPadding(new Insets(2));
        setMargin(res, new Insets(2, 2, 5, 2));
        res.setBorder(
                new BorderWrapper()
                        .addBottomBorder(1)
                        .setStyle(BorderStrokeStyle.SOLID)
                        .setColor(Color.GRAY)
                        .commit()
        );

        controls.Label label = Label.getInstanse(mes);
        label.setPadding(0, 0, 0, 5);

        res.getChildren().add(label);

        return res;
    }

    @Override
    protected void initStyle() {
        try {
            setBackground(
                    new BackgroundWrapper()
                            .setColor(Config.getInstance().getScene().getTheme().PRIMARY())
                            .commit()
            );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        setBorder(
                new BorderWrapper()
                        .addBorder(3)
                        .setColor(Color.GREY)
                        .setStyle(BorderStrokeStyle.SOLID)
                        .commit()
        );
    }

}
