package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_cell;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.Priority;
import layout.basepane.BorderPane;
import layout.basepane.GridPane;
import layout.basepane.HBox;
import thoth_gui.GuiLogger;
import thoth_gui.thoth_lite.components.controls.Button;
import thoth_gui.thoth_lite.components.controls.Label;
import thoth_gui.thoth_lite.components.controls.TextField;
import tools.SvgWrapper;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Typable;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_core.thoth_lite.ThothLite;
import thoth_gui.Apply;
import thoth_gui.Cancel;
import thoth_gui.thoth_lite.components.scenes.db_elements_view.list_view.RemoveItem;
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
    private final controls.Button remove;
    private final controls.Button trash = Button.getInstance(
            SvgWrapper.getInstance(Images.TRASH(), imgBtnSize, imgBtnSize, imgBtnViewBoxSize, imgBtnViewBoxSize)
            , event -> {
            });
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

    private LocalDateTime prevClick;

    private controls.TextField value;
//    private controls.Label valueLabel;

    private RemoveItem removeItem;

    protected ListedViewCell(Typable typable) {
        super();

        remove = (typable.getId().equals("new")) ?
                (trash) :
                (emptyFish);

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
            if (typable.getId().equals("new")) {
                //Вставляем запись в таблицу БД
                GuiLogger.log.info("Insert list-item into table");
                ThothLite.getInstance().insertToTable(table, list);
            } else {
                GuiLogger.log.info("Update list-item into table");
                //Обновляем запись
                ThothLite.getInstance().updateInTable(table, list);
            }
            toFromEditMode();
        } catch (SQLException | NotContainsException | ClassNotFoundException e) {
            GuiLogger.log.error(e.getMessage(), e);
        }
    }

    @Override
    public void cancel() {
        value.setText(((Typable) this.identifiable.getValue()).getValue());
        toFromEditMode();
    }

    private void changeStatusView() {
        if (modeIsEdit.getValue()) {
            pallete.getChildren().setAll(
                    acceptEdit, cancelEdit
            );
        } else {
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

        value = TextField.getInstance(((Typable) this.identifiable.getValue()).getValue());
        value.editableProperty().bind(modeIsEdit);

        GridPane pane = new GridPane()
                .addRow(Priority.NEVER)
                .addColumn(Priority.ALWAYS);
        pane.setPadding(new Insets(2, 0, 2, 0));
        pane.add(value, 0, 0);
        return pane;
    }

    @Override
    protected Node rightNode() {
        pallete = new HBox();
        pallete.setSpacing(5);
        BorderPane.setAlignment(pallete, Pos.CENTER);
        if (!this.identifiable.getValue().getId().equals("new")) {
            remove.setDisable(true);
            remove.setOpacity(0);
        } else {
            remove.setOnAction(event -> removeItem.removeItem(this.identifiable.getValue()));
        }
        pallete.setAlignment(Pos.CENTER);

        return pallete;
    }


    @Override
    public boolean hasRemoveItem() {
        return removeItem != null;
    }

    @Override
    public void setRemoveItem(RemoveItem removeItem) {
        this.removeItem = removeItem;
        if (remove != null) {
            remove.setDisable(!hasRemoveItem());
        }
    }
}
