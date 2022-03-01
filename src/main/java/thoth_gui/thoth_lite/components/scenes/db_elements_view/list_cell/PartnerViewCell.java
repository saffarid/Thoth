package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_cell;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import layout.basepane.BorderPane;
import layout.basepane.GridPane;
import layout.basepane.HBox;
import thoth_core.thoth_lite.ThothLite;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Finance;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Partnership;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_gui.Apply;
import thoth_gui.Cancel;
import thoth_gui.GuiLogger;
import thoth_gui.thoth_lite.components.controls.Button;
import thoth_gui.thoth_lite.components.controls.Label;
import thoth_gui.thoth_lite.components.controls.TextField;
import thoth_gui.thoth_styleconstants.svg.Images;
import tools.SvgWrapper;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.LinkedList;
import java.util.List;

public class PartnerViewCell
        extends IdentifiableViewCell
        implements Apply, Cancel {

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

    private controls.TextField name;
    private controls.Label nameLabel;
    private controls.TextField web;
    private controls.Label webLabel;
    private controls.TextField phone;
    private controls.Label phoneLabel;


    public PartnerViewCell(Partnership partner) {
        super();
        this.identifiable.setValue(partner);
        setTable(AvaliableTables.PARTNERS);

        modeIsEdit.addListener((observableValue, aBoolean, t1) -> changeStatusView());
        modeIsEdit.set(false);

        setOnMouseClicked(this::mouseClick);
        setOnKeyPressed(this::keyPress);

    }


    @Override
    protected Node leftNode() {
        Node point = SvgWrapper.getInstance(Images.POINT(), 7.5, 7.5, 10, 10);
        setAlignment(point, Pos.CENTER);
        return point;
    }

    @Override
    protected Node centerNode() {
        Partnership partner = (Partnership) this.identifiable.getValue();

        name = TextField.getInstance(partner.getName());
        nameLabel = Label.getInstanse();
        web = TextField.getInstance(partner.getWeb());
        webLabel = Label.getInstanse();
        phone = TextField.getInstance(partner.getPhone());
        phoneLabel = Label.getInstanse();

        nameLabel.textProperty().bind(name.textProperty());
        webLabel.textProperty().bind(web.textProperty());
        phoneLabel.textProperty().bind(phone.textProperty());

        content = new GridPane()
                .addRow(Priority.NEVER)
                .addColumn(Priority.ALWAYS)
                .addColumn(Priority.ALWAYS)
                .addColumn(Priority.ALWAYS)
                ;

        content.setHgap(5);

        content.setPadding(new Insets(2));

        nameLabel.setWrapText(true);
        content.add(nameLabel, 0, 0);
        content.add(name, 0, 0);
        content.add(webLabel, 1, 0);
        content.add(web, 1, 0);
        content.add(phoneLabel, 2, 0);
        content.add(phone, 2, 0);

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

    @Override
    public void apply() {
        Partnership partner = (Partnership) this.identifiable.getValue();
        partner.setName(name.getText());
        partner.setWeb(web.getText());
        partner.setPhone(phone.getText());
        List<Partnership> list = new LinkedList<>();
        list.add(partner);
        try {
            if (partner.getId().equals("-1")) {
                GuiLogger.log.info("Insert partner into table");
                //Вставляем запись в таблицу БД
                ThothLite.getInstance().insertToTable(table, list);
            } else {
                GuiLogger.log.info("Update partner into table");
                //Обновляем запись
                ThothLite.getInstance().updateInTable(table, list);
            }
            toFromEditMode();
        }
        catch (SQLException e) {
            GuiLogger.log.error(e.getMessage(), e);
        }
        catch (NotContainsException e) {
            GuiLogger.log.error(e.getMessage(), e);
        }
        catch (ClassNotFoundException e) {
            GuiLogger.log.error(e.getMessage(), e);
        }
    }

    @Override
    public void cancel() {
        Partnership partner = (Partnership)this.identifiable.getValue();
        name.setText(partner.getName());
        web.setText(partner.getWeb());
        phone.setText(partner.getPhone());
        toFromEditMode();
    }

    private void toFromEditMode() {
        modeIsEdit.set(!modeIsEdit.get());
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

    private void changeStatusView() {
        if (modeIsEdit.getValue()) {
            name.setOpacity(1);
            web.setOpacity(1);
            phone.setOpacity(1);
            nameLabel.setOpacity(0);
            webLabel.setOpacity(0);
            phoneLabel.setOpacity(0);
            pallete.getChildren().setAll(
                    acceptEdit, cancelEdit
            );
        } else {
            name.setOpacity(0);
            web.setOpacity(0);
            phone.setOpacity(0);
            nameLabel.setOpacity(1);
            webLabel.setOpacity(1);
            phoneLabel.setOpacity(1);
            pallete.getChildren().setAll(
                    toEdit, emptyFish
            );
        }
    }
}
