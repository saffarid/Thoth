package ThothGUI.ThothLite.Components.DBElementsView.ListCell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.Exceptions.NotContainsException;
import ThothCore.ThothLite.ThothLite;
import ThothGUI.Apply;
import ThothGUI.Cancel;
import ThothGUI.ThothLite.Components.DBElementsView.ListView.RemoveItem;
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

    protected ListedViewCell(Listed listed) {
        super(Image.LIST, listed.getValue(), "", "");
        this.listed = listed;
        ImageView point = getImageIcon(Image.POINT, 7.5, 7.5);

        value = new TextField( listed.getValue() );
        valueLabel = new Label();

        valueLabel.textProperty().bind(value.textProperty());

        setLeft(point);
        createContent();
        setRight(createPallete());

        setOnMouseClicked(this::mouseClick);

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
                    , getButton(Image.TRASH, imgButtonWidth, imgButtonHeight, this::remove)
            );
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
        Button node = new Button(
                getImageIcon(url, width, height)
        );

        node.setOnAction(event);
        node.setPadding(new Insets(5));
        return node;
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
            List<Listed> list = new LinkedList<>();
            list.add(listed);
            try {
                ThothLite.getInstance().removeFromTable(table, list);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NotContainsException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        removeItem.removeItem(listed);
    }

    @Override
    public boolean hasRemoveItem() {
        return removeItem != null;
    }

    @Override
    public void setRemoveItem(RemoveItem removeItem) {
        this.removeItem = removeItem;
    }
}
