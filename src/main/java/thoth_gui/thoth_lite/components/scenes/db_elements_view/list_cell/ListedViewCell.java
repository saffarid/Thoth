package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_cell;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import layout.basepane.BorderPane;
import layout.basepane.GridPane;
import layout.basepane.HBox;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Identifiable;
import thoth_gui.thoth_lite.components.controls.Button;
import thoth_gui.thoth_lite.components.controls.Label;
import thoth_gui.thoth_lite.components.controls.TextField;
import tools.BackgroundWrapper;
import tools.SvgWrapper;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Typable;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_core.thoth_lite.ThothLite;
import thoth_gui.Apply;
import thoth_gui.Cancel;
import thoth_gui.thoth_lite.components.scenes.db_elements_view.list_view.RemoveItem;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import thoth_gui.thoth_styleconstants.svg.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.*;
import java.util.LinkedList;
import java.util.List;

public class ListedViewCell
        extends IdentifiableViewCell
        implements Apply, Cancel, RemoveItemFromList {

    private final double imgBtnSize = 17;
    private final double imgBtnViewBoxSize = 20;

    private final controls.Button toEdit = Button.getInstance(
            SvgWrapper.getInstance(Images.EDIT(), imgBtnSize, imgBtnSize, imgBtnViewBoxSize, imgBtnViewBoxSize)
            , event -> toFromEditMode());
    private final controls.Button remove = Button.getInstance(
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

    private LocalDateTime prevClick;

    private controls.TextField value;
    private controls.Label valueLabel;

    private RemoveItem removeItem;

    protected ListedViewCell(Typable typable) {
        super();
        this.identifiable.setValue(typable);

        modeIsEdit.addListener((observableValue, aBoolean, t1) -> changeStatusView());
        modeIsEdit.set(false);

        setOnMouseClicked(this::mouseClick);
        setOnKeyPressed(this::keyPress);
    }

    @Override
    public void apply() {
        Typable typable = (Typable) this.identifiable.getValue();
        typable.setValue(value.getText());
        List<Typable> list = new LinkedList<>();
        list.add(typable);
        try {
            if (typable.getId().equals("-1")) {
                //Вставляем запись в таблицу БД
                ThothLite.getInstance().insertToTable(table, list);
            } else {
                //Обновляем запись
                ThothLite.getInstance().updateInTable(table, list);
            }
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
        value.setText(((Typable)this.identifiable.getValue()).getValue());
        toFromEditMode();
    }

    private void changeStatusView() {
        if (modeIsEdit.getValue()) {
            value.setOpacity(1);
            valueLabel.setOpacity(0);
            pallete.getChildren().setAll(
                    acceptEdit, cancelEdit
            );
        } else {
            value.setOpacity(0);
            valueLabel.setOpacity(1);
            pallete.getChildren().setAll(
                    toEdit, remove
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


        value = TextField.getInstance(((Typable)this.identifiable.getValue()).getValue());
        valueLabel = Label.getInstanse();
        valueLabel.textProperty().bind(value.textProperty());

        GridPane pane = new GridPane()
                .addRow(Priority.NEVER)
                .addColumn(Priority.ALWAYS)
                ;

        pane.add(value, 0, 0);
        pane.add(valueLabel, 0, 0);
        return pane;
    }

    @Override
    protected Node rightNode() {
        pallete = new HBox();
        pallete.setSpacing(5);
        BorderPane.setAlignment(pallete, Pos.CENTER);
        remove.setDisable(true);
        remove.setOpacity(0);
        pallete.setAlignment(Pos.CENTER);

        return pallete;
    }



    @Override
    public boolean hasRemoveItem() {
        return removeItem != null;
    }

    private void remove(ActionEvent event) {
        if (!this.identifiable.getValue().getId().equals("-1")) {
//            DialogWindow<ButtonType> instance = DialogWindow.getInstance(DialogWindowType.CONFIRM, "Вы подтверждаете удаление записи из БД?");
//            Optional<ButtonType> optional = instance.showAndWait();
//            if(optional.isPresent()){
//                if(optional.get() == ButtonType.YES){
//                    List<Listed> list = new LinkedList<>();
//                    list.add(listed);
//                    try {
//                        ThothLite.getInstance().removeFromTable(table, list);
//                        removeItem.removeItem(listed);
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    } catch (NotContainsException e) {
//                        e.printStackTrace();
//                    } catch (ClassNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
        } else {
            removeItem.removeItem(((Typable)this.identifiable.getValue()));
        }

    }

    @Override
    public void setRemoveItem(RemoveItem removeItem) {
        this.removeItem = removeItem;
        if (remove != null) {
            remove.setDisable(!hasRemoveItem());
        }
    }
}
