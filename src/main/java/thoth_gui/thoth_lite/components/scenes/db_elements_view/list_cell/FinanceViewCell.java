package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_cell;

import javafx.geometry.HPos;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import styleconstants.imagesvg.Close;
import tools.SvgWrapper;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Finance;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_core.thoth_lite.ThothLite;
import thoth_gui.Apply;
import thoth_gui.Cancel;
import thoth_gui.thoth_lite.components.scenes.db_elements_view.list_view.RemoveItem;
import thoth_gui.thoth_lite.dialog_windows.DialogWindow;
import thoth_gui.thoth_lite.dialog_windows.DialogWindowType;
import controls.Button;
import controls.Label;
import controls.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import thoth_gui.thoth_styleconstants.svg.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class FinanceViewCell
        extends IdentifiableViewCell
        implements Apply, Cancel {

    private final String templateCurrency = "%1s-%2s";

    private Finance finance;

    private HBox pallete;
    private GridPane content;

    private LocalDateTime prevClick;

    private boolean modeIsEdit = false;

    private Label courseLabel;
    private Label currencyLabel;
    private TextField course;


    protected FinanceViewCell(Finance finance) {
        super();
        this.finance = finance;

        Node point = SvgWrapper.getInstance(Images.POINT(), 7.5, 7.5);

        currencyLabel = thoth_gui.thoth_lite.components.controls.Label.getInstanse(
                String.format(
                        templateCurrency
                        , this.finance.getCurrency().getCurrencyCode()
                        , this.finance.getCurrency().getDisplayName())
        );
        currencyLabel.setTooltip(new Tooltip(currencyLabel.getText()));

        course = getTextField(
                String.valueOf(this.finance.getCourse())
        );
        courseLabel = thoth_gui.thoth_lite.components.controls.Label.getInstanse();
        courseLabel.textProperty().bind(course.textProperty());

        setLeft(point);
        createContent();
        setRight(createPallete());

        setTable(AvaliableTables.CURRENCIES);

        setOnMouseClicked(this::mouseClick);
        setOnKeyPressed(this::keyPress);

        setPadding(new Insets(5, 2, 5, 2));
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
        modeIsEdit = !modeIsEdit;
        createPallete();
        createContent();
    }

    private void createContent() {
        if (content == null) {
            content = new GridPane();
            content.setPadding(new Insets(2, 5, 2, 15));
            content.setHgap(5);
            setCenter(content);

            content.getRowConstraints().add(new RowConstraints());
            ColumnConstraints currency = new ColumnConstraints();
            ColumnConstraints course = new ColumnConstraints();

            currency.setFillWidth(true);
            course.setFillWidth(true);

            currency.setPercentWidth(60);
            course.setPercentWidth(40);

            currency.setHgrow(Priority.ALWAYS);
            course.setHgrow(Priority.ALWAYS);

            currency.setHalignment(HPos.RIGHT);
            course.setHalignment(HPos.LEFT);

            content.getColumnConstraints().addAll(
                    currency
                    , course
            );
            content.add(currencyLabel, 0, 0);
            content.add(this.course, 1, 0);
            content.add(courseLabel, 1, 0);
        }
        if (modeIsEdit) {
            course.setOpacity(1);
            courseLabel.setOpacity(0);
        } else {
            course.setOpacity(0);
            courseLabel.setOpacity(1);
        }
    }

    private Node createPallete() {
        if (pallete == null) {
            pallete = new HBox();

            pallete.setSpacing(5);
            pallete.setPadding(new Insets(2));
            setAlignment(pallete, Pos.CENTER);
        }
        double imgButtonWidth = 17;
        double imgButtonHeight = 17;
        if (!modeIsEdit) {
            pallete.getChildren().setAll(
                    getButton(SvgWrapper.getInstance(Images.EMPTY(), imgButtonWidth, imgButtonHeight), event -> {})
                    , getButton(SvgWrapper.getInstance(Images.EDIT(), imgButtonWidth, imgButtonHeight), this::toEditMode)
            );
        } else {
            pallete.getChildren().setAll(
                    getButton(SvgWrapper.getInstance(Images.CHECKMARK(), imgButtonWidth, imgButtonHeight), event -> apply())
                    , getButton(SvgWrapper.getInstance(Images.CLOSE(), imgButtonWidth, imgButtonHeight), event -> cancel())
            );
        }

        return pallete;
    }

    protected Button getButton(
            Node img
            , EventHandler<ActionEvent> event
    ) {
        Button node = thoth_gui.thoth_lite.components.controls.Button.getInstance(
                img
                , event
        );
        node.setPadding(new Insets(5));
        return node;
    }

    private void keyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case ENTER: {
                if (modeIsEdit) apply();
                break;
            }
            case ESCAPE: {
                if (modeIsEdit) cancel();
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
