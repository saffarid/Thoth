package ThothGUI.thoth_lite.components.db_elements_view.list_cell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.Exceptions.NotContainsException;
import ThothCore.ThothLite.ThothLite;
import ThothGUI.Apply;
import ThothGUI.Cancel;
import ThothGUI.thoth_lite.components.db_elements_view.list_view.RemoveItem;
import ThothGUI.thoth_styleconstants.Image;
import controls.Button;
import controls.Label;
import controls.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

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

    private Listed listed;

    private TextField value;
    private Label valueLabel;

    private RemoveItem removeItem;

    private Button remove;

    protected ListedViewCell(Listed listed) {
        super(ThothGUI.thoth_styleconstants.svg.List.getInstance(), listed.getValue(), "", "");
        this.listed = listed;
        ImageView point = getImageIcon(Image.POINT, 7.5, 7.5);

        value = new TextField( listed.getValue() );
        valueLabel = new Label();

        valueLabel.textProperty().bind(value.textProperty());

        setLeft(point);
        createContent();
        setRight(createPallete());

        setOnMouseClicked(this::mouseClick);
        setOnKeyPressed(this::keyPress);

        setPadding(new Insets(5, 2, 5, 2));
        setAlignment(point, Pos.CENTER);
    }

    @Override
    public void apply() {
        listed.setValue(value.getText());
        List<Listed> list = new LinkedList<>();
        list.add(listed);
        try {
            if (listed.getId().equals("-1")) {
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
        value.setText(listed.getValue());
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
                    getButton(Image.EDIT, imgButtonWidth, imgButtonHeight, this::toEditMode)
            );
//            if(listed.getId().equals("-1")) {
                remove = getButton(Image.TRASH, imgButtonWidth, imgButtonHeight, this::remove);
                remove.setDisable(!hasRemoveItem());
                pallete.getChildren().add( remove );
//            }
        } else {
            pallete.getChildren().setAll(
                    getButton(Image.CHECKMARK, imgButtonWidth, imgButtonHeight, event -> apply())
                    , getButton(Image.CLOSE, imgButtonWidth, imgButtonHeight, event -> cancel())
            );
        }

        return pallete;
    }

    protected Button getButton(
            String url
            , double width, double height
            , EventHandler<ActionEvent> event
    ) {
        Button node = ThothGUI.thoth_lite.components.controls.Button.getInstance(
                ThothGUI.thoth_lite.components.controls.ImageView.getInstance(url, width, height),
                event
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
        if(!listed.getId().equals("-1")) {
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
        }else{
            removeItem.removeItem(listed);
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
