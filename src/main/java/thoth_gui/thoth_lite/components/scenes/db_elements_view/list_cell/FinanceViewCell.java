package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_cell;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.HPos;
import javafx.scene.control.Tooltip;

import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import layout.basepane.BorderPane;
import layout.basepane.GridPane;
import layout.basepane.HBox;
import thoth_gui.thoth_lite.components.controls.Button;
import thoth_gui.thoth_lite.components.controls.Label;
import thoth_gui.thoth_lite.components.controls.TextField;
import thoth_gui.thoth_styleconstants.svg.Images;
import tools.BackgroundWrapper;
import tools.SvgWrapper;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Finance;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_core.thoth_lite.ThothLite;
import thoth_gui.Apply;
import thoth_gui.Cancel;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.LinkedList;
import java.util.List;


public class FinanceViewCell
        extends IdentifiableViewCell
        implements Apply, Cancel {

    private final String templateCurrency = "%1s-%2s";
    private final double imgBtnSize = 17;
    private final double imgBtnViewBoxSize = 20;

    private final controls.Button toEdit = Button.getInstance(
            SvgWrapper.getInstance(Images.EDIT(), imgBtnSize, imgBtnSize, imgBtnViewBoxSize, imgBtnViewBoxSize)
            , this::toEditMode);
    private final controls.Button emptyFish = Button.getInstance(
            SvgWrapper.getInstance(Images.EMPTY(), imgBtnSize, imgBtnSize, imgBtnViewBoxSize, imgBtnViewBoxSize)
            , event -> {
            });
    private final controls.Button acceptEdit = Button.getInstance(
            SvgWrapper.getInstance(Images.CHECKMARK(), imgBtnSize, imgBtnSize, imgBtnViewBoxSize, imgBtnViewBoxSize)
            , event -> apply());
    private final controls.Button cancelEdit = Button.getInstance(
            SvgWrapper.getInstance(Images.CLOSE(), imgBtnSize, imgBtnSize, imgBtnViewBoxSize, imgBtnViewBoxSize)
            , event -> cancel());

    private Finance finance;

    private HBox pallete;
    private GridPane content;

    private LocalDateTime prevClick;

    private SimpleBooleanProperty modeIsEdit = new SimpleBooleanProperty();

    private controls.Label courseLabel;
    private controls.Label currencyLabel;
    private controls.TextField course;


    protected FinanceViewCell(Finance finance) {
        super();
        this.finance = finance;

        modeIsEdit.addListener((observableValue, aBoolean, t1) -> changeStatusView());

        Node point = SvgWrapper.getInstance(Images.POINT(), 7.5, 7.5, 10, 10);

        currencyLabel = Label.getInstanse(
                String.format(
                        templateCurrency
                        , this.finance.getCurrency().getCurrencyCode()
                        , this.finance.getCurrency().getDisplayName())
        );
        currencyLabel.setTooltip(new Tooltip(currencyLabel.getText()));

        course = TextField.getInstance(
                String.valueOf(this.finance.getCourse())
        );
        courseLabel = Label.getInstanse();
        courseLabel.textProperty().bind(course.textProperty());

        setLeft(point);
        setCenter(createContent());
        setRight(createPallete());

        setTable(AvaliableTables.CURRENCIES);

        setOnMouseClicked(this::mouseClick);
        setOnKeyPressed(this::keyPress);

        setPadding(new Insets(2));
        setAlignment(point, Pos.CENTER);
    }

    @Override
    public void apply() {
        finance.setCourse(Double.parseDouble(course.getText()));
        List<Finance> list = new LinkedList<>();
        list.add(finance);
        try {
            ThothLite.getInstance().updateInTable(table, list);
            changeStatusView();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NotContainsException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancel() {
        course.setText(String.valueOf(finance.getCourse()));
        changeStatusView();
    }

    private void changeStatusView() {
        if (modeIsEdit.getValue()) {
            course.setOpacity(1);
            courseLabel.setOpacity(0);
            pallete.getChildren().setAll(
                    acceptEdit, cancelEdit
            );
        } else {
            course.setOpacity(0);
            courseLabel.setOpacity(1);
            pallete.getChildren().setAll(
                    toEdit, emptyFish
            );
        }
    }

    private Node createContent() {
        content = new GridPane();
        content.setGridLinesVisible(true);
        content.setPadding(new Insets(5));
        content.setHgap(5);

        content
                .addRow(Priority.NEVER)
                .addColumn(300, 300, Double.MAX_VALUE, Priority.ALWAYS, HPos.LEFT, true)
                .addColumn(100, 100, 100, Priority.NEVER, HPos.LEFT, true)
        ;

        currencyLabel.setWrapText(true);
        content.add(currencyLabel, 0, 0);
        content.add(this.course, 1, 0);
        content.add(courseLabel, 1, 0);

        return content;
    }

    private Node createPallete() {

        pallete = new HBox();
        pallete.setSpacing(5);
        BorderPane.setAlignment(pallete, Pos.CENTER);
        pallete.setBackground(new BackgroundWrapper().setColor(Color.GREY).commit());
        emptyFish.setDisable(true);
        emptyFish.setOpacity(0);
        pallete.setAlignment(Pos.CENTER);

        return pallete;
    }

    private void keyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case ENTER: {
                if (modeIsEdit.getValue()) apply();
                break;
            }
            case ESCAPE: {
                if (modeIsEdit.getValue()) cancel();
                break;
            }
        }
    }

    private void mouseClick(MouseEvent mouseEvent) {
        switch (mouseEvent.getButton()) {
            case PRIMARY: {
                LocalDateTime now = LocalDateTime.now();
                long aLong = now.getLong(ChronoField.SECOND_OF_DAY);
                if ((prevClick != null) && (aLong - prevClick.getLong(ChronoField.SECOND_OF_DAY)) < 1) {
                    changeStatusView();
                } else {
                    prevClick = now;
                }
            }
        }
    }

    private void toEditMode(ActionEvent event) {
        changeStatusView();
    }

}
