package thoth_gui.thoth_lite.components.db_elements_view.list_cell;

import styleconstants.imagesvg.Close;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Typable;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_core.thoth_lite.ThothLite;
import thoth_gui.Apply;
import thoth_gui.Cancel;
import thoth_gui.thoth_lite.components.db_elements_view.list_view.RemoveItem;
import controls.Button;
import controls.Label;
import controls.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import thoth_gui.thoth_styleconstants.svg.Checkmark;
import thoth_gui.thoth_styleconstants.svg.Edit;
import thoth_gui.thoth_styleconstants.svg.Point;
import thoth_gui.thoth_styleconstants.svg.Trash;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.*;
import java.util.LinkedList;
import java.util.List;

public class ListedViewCell
        extends IdentifiableViewCell
        implements Apply, Cancel, RemoveItemFromList {

    private HBox pallete;

    private LocalDateTime prevClick;

    private boolean modeIsEdit = false;

    private Typable typable;

    private TextField value;
    private Label valueLabel;

    private RemoveItem removeItem;

    private Button remove;

    protected ListedViewCell(Typable typable) {
        super(thoth_gui.thoth_styleconstants.svg.List.getInstance(), typable.getValue(), "", "");
        this.typable = typable;
        Node point = Point.getInstance(7.5, 7.5);

        value = new TextField(typable.getValue());
        valueLabel = new Label();

        valueLabel.textProperty().bind(value.textProperty());

        setLeft( point );
        createContent();
        setRight(createPallete());

        setOnMouseClicked(this::mouseClick);
        setOnKeyPressed(this::keyPress);

        setPadding(new Insets(5, 2, 5, 2));
        setAlignment(point, Pos.CENTER);
    }

    @Override
    public void apply() {
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
        value.setText(typable.getValue());
        changeStatusView();
    }

    private void changeStatusView() {
        modeIsEdit = !modeIsEdit;
        createPallete();
        createContent();
    }

    private void createContent() {
        if (modeIsEdit) {
            setCenter(value);
        } else {
            setCenter(valueLabel);
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
//            if(listed.getId().equals("-1")) {
            remove = getButton( Trash.getInstance(imgButtonWidth, imgButtonHeight), this::remove );
            remove.setDisable(!hasRemoveItem());
            pallete.getChildren().add(remove);
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

    private void remove(ActionEvent event) {
        if (!typable.getId().equals("-1")) {
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
            removeItem.removeItem(typable);
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
