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
            , event -> toFromEditMode());
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

    private SimpleBooleanProperty modeIsEdit = new SimpleBooleanProperty(true);

    private HBox pallete;
    private GridPane content;

    private LocalDateTime prevClick;

    private controls.Label courseLabel;
    private controls.Label currencyLabel;
    private controls.TextField course;

    protected FinanceViewCell(Finance finance) {
        super();
        this.identifiable.setValue(finance);
        setTable(AvaliableTables.CURRENCIES);

        modeIsEdit.addListener((observableValue, aBoolean, t1) -> changeStatusView());
        modeIsEdit.set(false);

        setOnMouseClicked(this::mouseClick);
        setOnKeyPressed(this::keyPress);
    }

    @Override
    public void apply() {
        Finance finance = (Finance)this.identifiable.getValue();
        finance.setCourse(Double.parseDouble(course.getText()));
        List<Finance> list = new LinkedList<>();
        list.add(finance);
        try {
            ThothLite.getInstance().updateInTable(table, list);
            toFromEditMode();
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
        Finance finance = (Finance)this.identifiable.getValue();
        course.setText(String.valueOf(finance.getCourse()));
        toFromEditMode();
    }

    /**
     * Функция вызывается всякий раз как изменится флаг режима
     * */
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
                    toFromEditMode();
                } else {
                    prevClick = now;
                }
            }
        }
    }

    private void toFromEditMode() {
        modeIsEdit.set(!modeIsEdit.get());
    }

    @Override
    protected Node leftNode() {
        Node point = SvgWrapper.getInstance(Images.POINT(), 7.5, 7.5, 10, 10);
        setAlignment(point, Pos.CENTER);
        return point;
    }

    @Override
    protected Node centerNode() {
        Finance finance = (Finance)this.identifiable.getValue();
        currencyLabel = Label.getInstanse(
                String.format(
                        templateCurrency
                        , finance.getCurrency().getCurrencyCode()
                        , finance.getCurrency().getDisplayName())
        );
        currencyLabel.setTooltip(new Tooltip(currencyLabel.getText()));

        course = TextField.getInstance(
                String.valueOf(finance.getCourse())
        );
        courseLabel = Label.getInstanse();
        courseLabel.textProperty().bind(course.textProperty());

        content = new GridPane();

        content.setPadding(new Insets(2));
        content.setHgap(5);

        content
                .addRow(Priority.NEVER)
                .addColumn(300, 300, 700, Priority.ALWAYS, HPos.LEFT, true)
                .addColumn(100, 100, 100, Priority.NEVER, HPos.LEFT, true)
        ;

        currencyLabel.setWrapText(true);
        content.add(currencyLabel, 0, 0);
        content.add(this.course, 1, 0);
        content.add(courseLabel, 1, 0);

        return content;
    }

    @Override
    protected Node rightNode() {
        pallete = new HBox();
        pallete.setSpacing(5);
        BorderPane.setAlignment(pallete, Pos.CENTER);
        emptyFish.setDisable(true);
        emptyFish.setOpacity(0);
        pallete.setAlignment(Pos.CENTER);

        return pallete;
    }
}
