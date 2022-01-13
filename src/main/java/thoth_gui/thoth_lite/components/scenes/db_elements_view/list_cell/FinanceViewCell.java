package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_cell;

import styleconstants.imagesvg.Close;
import styleconstants.imagesvg.SvgWrapper;
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
import javafx.scene.layout.HBox;
import thoth_gui.thoth_styleconstants.svg.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class FinanceViewCell
        extends IdentifiableViewCell
        implements Apply, Cancel, RemoveItemFromList {

    private Finance finance;

    private HBox pallete;
    private HBox content;

    private LocalDateTime prevClick;

    private boolean modeIsEdit = false;

    private Label courseLabel;
    private Label currencyLabel;
    private TextField course;
    private TextField currency;

    private RemoveItem removeItem;

    private Button remove;

    protected FinanceViewCell(Finance finance) {
        super();
        this.finance = finance;

        Node point = SvgWrapper.getInstance(Images.POINT(), 7.5, 7.5);

        currency = getTextField(this.finance.getCurrency());
        currencyLabel = thoth_gui.thoth_lite.components.controls.Label.getInstanse();
        currencyLabel.textProperty().bind(currency.textProperty());
        course = getTextField(String.valueOf(this.finance.getCourse()));
        courseLabel = thoth_gui.thoth_lite.components.controls.Label.getInstanse();
        courseLabel.textProperty().bind(course.textProperty());

        setLeft( Point.getInstance(7.5, 7.5) );
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
        finance.setCurrency(currency.getText());
        finance.setCourse(Double.parseDouble(course.getText()));
        List<Finance> list = new LinkedList<>();
        list.add(finance);
        try {
            if (finance.getId().equals("-1")) {
                //Вставляем запись в таблицу БД
                ThothLite.getInstance().insertToTable(AvaliableTables.CURRENCIES, list);
            } else {
                //Обновляем запись
                ThothLite.getInstance().updateInTable(table, list);
            }
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
        currency.setText(finance.getCurrency());
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
            content = new HBox();
            content.setPadding(new Insets(2, 5, 2, 15));
            content.setSpacing(10);
            content.setAlignment(Pos.CENTER_LEFT);
            setCenter(content);
            setAlignment(content, Pos.CENTER);
        }
        if (modeIsEdit) {
            content.getChildren().setAll(
                    currency, course
            );
        } else {
            content.getChildren().setAll(
                    currencyLabel, courseLabel
            );
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
                    getButton( Edit.getInstance(imgButtonWidth, imgButtonHeight), this::toEditMode )
            );
//            if(finance.getId().equals("-1")) {
                remove = getButton( Trash.getInstance(imgButtonWidth, imgButtonHeight), this::remove );
                remove.setDisable( !hasRemoveItem() );
                pallete.getChildren().add( remove );
//            }
        } else {
            pallete.getChildren().setAll(
                    getButton(Checkmark.getInstance(imgButtonWidth, imgButtonHeight), event -> apply())
                    , getButton(Close.getInstance(), event -> cancel())
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

    @Override
    public boolean hasRemoveItem() {
        return removeItem != null;
    }

    private void keyPress(KeyEvent keyEvent) {
        switch(keyEvent.getCode()){
            case ENTER: {
                if(modeIsEdit) apply();
                break;
            }
            case ESCAPE:{
                if(modeIsEdit) cancel();
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

    private void remove(ActionEvent event) {
        List<Finance> list = new LinkedList<>();
        list.add(finance);
        if (!finance.getId().equals("-1")) {
            try {
                DialogWindow<ButtonType> instance = DialogWindow.getInstance(DialogWindowType.CONFIRM, "Вы подтверждаете удаление записи из БД?");
                Optional<ButtonType> optional = instance.showAndWait();
                if (optional.isPresent()) {
                    if (optional.get() == ButtonType.YES) {
                        ThothLite.getInstance().removeFromTable(AvaliableTables.CURRENCIES, list);
                        removeItem.removeItem(finance);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NotContainsException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            removeItem.removeItem(finance);
        }
    }

    @Override
    public void setRemoveItem(RemoveItem removeItem) {
        this.removeItem = removeItem;
        if(remove != null) {
            remove.setDisable(!hasRemoveItem());
        }
    }
}
