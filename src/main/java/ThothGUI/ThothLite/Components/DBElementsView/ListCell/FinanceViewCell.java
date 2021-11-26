package ThothGUI.ThothLite.Components.DBElementsView.ListCell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Finance;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothCore.ThothLite.Exceptions.NotContainsException;
import ThothCore.ThothLite.ThothLite;
import ThothGUI.Apply;
import ThothGUI.Cancel;
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
import javafx.scene.layout.HBox;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class FinanceViewCell
        extends IdentifiableViewCell
        implements Apply, Cancel
{

    private Finance finance;

    private HBox pallete;
    private HBox content;

    private LocalDateTime prevClick;
    private String bufferListedValue;

    private boolean modeIsEdit = false;

    private Label courseLabel;
    private Label currencyLabel;
    private TextField course;
    private TextField currency;

    protected FinanceViewCell(Finance finance) {
        super();
        this.finance = finance;

        ImageView point = getImageIcon(Image.POINT, 7.5, 7.5);

        currency = getTextField( this.finance.getCurrency() );
        currencyLabel = new Label();
        currencyLabel.textProperty().bind(currency.textProperty());
        course = getTextField( String.valueOf(this.finance.getCourse()) );
        courseLabel = new Label();
        courseLabel.textProperty().bind(course.textProperty());

        setLeft(point);
        createContent();
        setRight(createPallete());

        setTable(AvaliableTables.CURRENCIES);

        setPadding(new Insets(5, 2, 5, 2));
        setAlignment(point, Pos.CENTER);
    }

    @Override
    public void apply() {
        finance.setCurrency( currency.getText() );
        finance.setCourse( Double.parseDouble(course.getText()) );
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

    }

    private void changeStatusView() {
        modeIsEdit = !modeIsEdit;
        createPallete();
        createContent();
    }

    private void createContent() {
        if(content == null){
            content = new HBox();
            content.setPadding(new Insets(2, 5, 2, 15));
            content.setSpacing(10);
            content.setAlignment(Pos.CENTER_LEFT);
            setCenter( content );
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

    private void toEditMode(ActionEvent event) {
        changeStatusView();
    }

    private void remove(ActionEvent event) {

    }}
