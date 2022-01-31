package thoth_gui.thoth_lite.components.scenes.db_elements_view.identifiable_card;

import controls.Label;
import controls.TextArea;
import controls.TextField;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import layout.basepane.HBox;
import layout.basepane.VBox;
import thoth_core.thoth_lite.db_data.db_data_element.properties.*;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_gui.Apply;
import thoth_gui.Cancel;
import thoth_gui.thoth_lite.components.controls.combo_boxes.FinanceComboBox;
import thoth_gui.thoth_lite.components.controls.combo_boxes.TypableComboBox;
import thoth_gui.thoth_lite.components.converters.StringDoubleConverter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FinOperationCard
        extends IdentifiableCard
        implements Apply, Cancel {

    private enum ControlsId {
        FIN_OP_TYPE("fin-op-type"),
        VALUE("value"),
        DATE("date"),
        CURRENCY("currency"),
        COURSE("course"),
        COMMENT("comment");
        private String id;

        ControlsId(String id) {
            this.id = id;
        }
    }

    private AvaliableTables tableCategory;

    /**
     * Категория финансовой операции
     */
    private controls.ComboBox<Typable> category;
    /**
     * Сумма финансовой операции
     */
    private controls.TextField value;
    /**
     * Сумма финансовой операции
     */
    private DoubleProperty valueProperty;
    /**
     * Дата совершения финансовой операции
     */
    private DatePicker dateFinOp;
    /**
     * Валюта покупки
     */
    private controls.ComboBox<Finance> financeComboBox;
    /**
     * Курс валюты
     */
    private TextField course;
    /**
     * Курс валюты
     */
    private DoubleProperty courseProperty;
    /**
     * Подтягивание курса валюты из объекта финансов
     */
    private CheckBox courseFromFinance;
    /**
     * Комментарий к операции
     */
    private TextArea comment;

    public FinOperationCard(AvaliableTables table) {
        super(null, table);

    }

    private AvaliableTables categoryTable() {
        if (table == AvaliableTables.EXPENSES) {
            return AvaliableTables.EXPENSES_TYPES;
        } else {
            return AvaliableTables.INCOMES_TYPES;
        }
    }

    private void changeCourse() {
        if (courseFromFinance.isSelected()) {
            course.setText(String.valueOf(financeComboBox.getValue().getCourse()));
        }
    }

    protected Node createContent() {
        super.createContent();

        VBox vBox = new VBox();
        vBox.setFillWidth(true);

        valueProperty = new SimpleDoubleProperty();
        courseProperty = new SimpleDoubleProperty();

        category = TypableComboBox.getInstance(categoryTable(), null);
        value = getTextField(ControlsId.VALUE, String.valueOf(0.0));
        dateFinOp = thoth_gui.thoth_lite.components.controls.DatePicker.getInstance();
        financeComboBox = FinanceComboBox.getInstance();
        course = getTextField(ControlsId.COURSE, String.valueOf(0.0));
        courseFromFinance = thoth_gui.thoth_lite.components.controls.CheckBox.getInstance("use course from finance");
        comment = new TextArea();

        courseFromFinance.setIndeterminate(false);
        course.disableProperty().bind(courseFromFinance.selectedProperty());
        financeComboBox.valueProperty().addListener((observableValue, finance, t1) -> changeCourse());
        courseFromFinance.selectedProperty().addListener((observableValue, aBoolean, t1) -> changeCourse());

        Bindings.bindBidirectional(value.textProperty(), valueProperty, new StringDoubleConverter());

        Bindings.bindBidirectional(course.textProperty(), courseProperty, new StringDoubleConverter());

        apply.disableProperty().bind(
                category.valueProperty().isNull()
                        .or(valueProperty.lessThan(StringDoubleConverter.priceMin))
                        .or(financeComboBox.valueProperty().isNull())
                        .or(courseProperty.lessThan(StringDoubleConverter.courseMin))
        );

        vBox.getChildren().addAll(
                createRow(getLabel(ControlsId.FIN_OP_TYPE.id), category),
                createRow(getLabel(ControlsId.VALUE.id), value, financeComboBox),
                createRow(getLabel(ControlsId.DATE.id), dateFinOp),
                createRow(getLabel(ControlsId.COURSE.id), course, courseFromFinance),
                createRow(getLabel(ControlsId.COMMENT.id), comment)
        );

        contentNode.setCenter(vBox);

        return contentNode;
    }

    private Node createRow(
            Node titleNode
            , Node... enterNodes
    ) {
        VBox res = new VBox();

        res.setAlignment(Pos.TOP_LEFT);
        res.setFillWidth(true);
        res.setPadding(new Insets(2));

        if (enterNodes.length > 1) {
            HBox hBox = new HBox(enterNodes);
            hBox.setSpacing(5);

            res.getChildren().addAll(
                    titleNode
                    , hBox
            );
        } else {
            res.getChildren().addAll(
                    titleNode
                    , Arrays.stream(enterNodes).findFirst().get()
            );
        }


        return res;
    }

    private Label getLabel(String text) {
        Label res = thoth_gui.thoth_lite.components.controls.Label.getInstanse(text);
        res.setMinWidth(120);
        res.setPrefWidth(120);
        res.setMaxWidth(120);
        return res;
    }

    private controls.TextField getTextField(ControlsId id, String defaultText) {
        controls.TextField res = thoth_gui.thoth_lite.components.controls.TextField.getInstance(defaultText);
        res.setId(id.id);

        res.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.equals("")) {
                Pattern pattern = null;
                switch (id) {
                    case VALUE: {
                        pattern = Pattern.compile(StringDoubleConverter.priceRegEx);
                        break;
                    }
                    case COURSE: {
                        pattern = Pattern.compile(StringDoubleConverter.courseRegEx);
                        break;
                    }
                    default: pattern = null;
                }

                Matcher matcher = pattern.matcher(t1);

                if (!matcher.matches()) {
                    res.setText(s);
                }
            }
        });

        res.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1 == false) {
                switch (id) {
                    case VALUE:
                    case COURSE: {
                        if (res.getText().equals("")) {
                            res.setText(String.valueOf(0.0));
                        }
                    }
                }
            }
        });
        return res;
    }

    @Override
    protected Identifiable identifiableInstance() {
        return new FinancialAccounting() {
            /**
             * Идентификатор операции
             * */
            private String id;
            /**
             * Тип покупки
             * */
            private Typable type;
            /**
             * Сумма покупки
             * */
            private Double value;
            /**
             * Дата покупки
             * */
            private LocalDate date;
            /**
             * Валюта покупки
             * */
            private Finance currency;
            /**
             * Курс валюты на момент покупки
             * */
            private Double course;
            /**
             * Комментарий к покупке
             * */
            private String comment;

            @Override
            public Typable getCategory() {
                return type;
            }

            @Override
            public String getComment() {
                return comment;
            }

            @Override
            public Double getCourse() {
                return course;
            }

            @Override
            public LocalDate getDate() {
                return date;
            }

            @Override
            public Finance getFinance() {
                return currency;
            }

            @Override
            public String getId() {
                return id;
            }

            @Override
            public Double getValue() {
                return value;
            }

            @Override
            public void setCategory(Typable typable) {
                this.type = typable;
            }

            @Override
            public void setComment(String comment) {
                this.comment = comment;
            }

            @Override
            public void setCourse(Double course) {
                this.course = course;
            }

            @Override
            public void setDate(LocalDate date) {
                this.date = date;
            }

            @Override
            public void setFinance(Finance finance) {
                this.currency = finance;
            }

            @Override
            public void setId(String id) {
                this.id = id;
            }

            @Override
            public void setValue(Double value) {
                this.value = value;
            }
        };
    }

    @Override
    protected void updateIdentifiable() {
        ((FinancialAccounting) identifiable).setCategory(category.getValue());
        ((FinancialAccounting) identifiable).setValue(Double.parseDouble(value.getText()));
        ((FinancialAccounting) identifiable).setDate(dateFinOp.getValue());
        ((FinancialAccounting) identifiable).setFinance(financeComboBox.getValue());
        ((FinancialAccounting) identifiable).setCourse(Double.parseDouble(course.getText()));
        ((FinancialAccounting) identifiable).setComment(comment.getText());
    }


}
