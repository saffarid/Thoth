package thoth_gui.thoth_lite.components.scenes.db_elements_view.identifiable_card;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import layout.basepane.HBox;
import layout.basepane.VBox;
import thoth_core.thoth_lite.ThothLite;
import thoth_core.thoth_lite.db_data.db_data_element.properties.*;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_gui.Apply;
import thoth_gui.Cancel;
import thoth_gui.thoth_lite.components.controls.*;
import thoth_gui.thoth_lite.components.controls.combo_boxes.FinanceComboBox;
import thoth_gui.thoth_lite.components.controls.combo_boxes.TypableComboBox;
import thoth_gui.thoth_lite.components.converters.StringDoubleConverter;
import thoth_gui.thoth_lite.tools.Properties;
import thoth_gui.thoth_lite.tools.TextCase;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FinOperationCard
        extends IdentifiableCard
        implements Apply, Cancel {

    private enum TextLabels {
        TITLE(Properties.getString("new_fin_op", TextCase.NORMAL)),
        FIN_OP_TYPE(Properties.getString("fin-op-type", TextCase.NORMAL)),
        VALUE(Properties.getString("value", TextCase.NORMAL)),
        DATE(Properties.getString("date", TextCase.NORMAL)),
        CURRENCY(Properties.getString("currency", TextCase.NORMAL)),
        COURSE(Properties.getString("course", TextCase.NORMAL)),
        COMMENT(Properties.getString("comment", TextCase.NORMAL)),
        USE_COURSE_FROM_FINANCE(Properties.getString("use_course_from_finance", TextCase.NORMAL));
        private String text;

        TextLabels(String id) {
            this.text = id;
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
    private controls.TextField course;
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
    private controls.TextArea comment;

    public FinOperationCard(AvaliableTables table) {
        super(null, table);
        this.id = this.table == AvaliableTables.EXPENSES ? "new-expenses" : "new-incomes";
        tools = new SimpleObjectProperty<>(createToolsNode());
    }

    private AvaliableTables categoryTable() {
        if (table == AvaliableTables.EXPENSES) {
            return AvaliableTables.EXPENSES_TYPES;
        } else {
            return AvaliableTables.INCOMES_TYPES;
        }
    }

    private void changeCourse() {
        if (courseFromFinance == null || course == null || financeComboBox.getValue() == null) return;
        if (courseFromFinance.isSelected()) {
            course.setText(String.valueOf(financeComboBox.getValue().getCourse()));
        }
    }

    @Override
    public void open() {
        ThothLite.getInstance().subscribeOnTable(AvaliableTables.CURRENCIES, financeComboBox);
        ThothLite.getInstance().subscribeOnTable(categoryTable(), category);
    }

    @Override
    public void close() {
        financeComboBox.close();
        category.close();
    }

    @Override
    protected Node createToolsNode() {
        return ((ToolsPane) super.createToolsNode()).setTitleText(TextLabels.TITLE.text);
    }

    protected Node createContentNode() {
        super.createContentNode();

        VBox vBox = new VBox();
        vBox.setFillWidth(true);

        valueProperty = new SimpleDoubleProperty();
        courseProperty = new SimpleDoubleProperty();

        category = TypableComboBox.getInstance(categoryTable(), null);
        value = getTextField(TextLabels.VALUE, String.valueOf(0.0));
        dateFinOp = thoth_gui.thoth_lite.components.controls.DatePicker.getInstance();
        financeComboBox = FinanceComboBox.getInstance();
        course = getTextField(TextLabels.COURSE, String.valueOf(0.0));
        courseFromFinance = thoth_gui.thoth_lite.components.controls.CheckBox.getInstance(TextLabels.USE_COURSE_FROM_FINANCE.text);
        comment = TextArea.getInstance();

        courseFromFinance.setIndeterminate(false);
        course.editableProperty().bind(courseFromFinance.selectedProperty().not());
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
                Row.getInstance(Label.getInstanse(TextLabels.FIN_OP_TYPE.text), category),
                Row.getInstance(Label.getInstanse(TextLabels.VALUE.text), value, financeComboBox),
                Row.getInstance(Label.getInstanse(TextLabels.DATE.text), dateFinOp),
                Row.getInstance(Label.getInstanse(TextLabels.COURSE.text), course, courseFromFinance),
                Row.getInstance(Label.getInstanse(TextLabels.COMMENT.text), comment)
        );

        contentNode.setCenter(vBox);

        return contentNode;
    }

    private controls.TextField getTextField(TextLabels id, String defaultText) {
        controls.TextField res = TextField.getInstance(defaultText);
        res.setId(id.text);

        res.textProperty().addListener((observableValue, s, t1) -> {
            if (t1.equals("")) return;

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
                default:
                    pattern = null;
            }

            Matcher matcher = pattern.matcher(t1);

            if (!matcher.matches()) {
                res.setText(s);
            }

        });

        res.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1) {
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
        ((FinancialAccounting) identifiable.getValue()).setCategory(category.getValue());
        ((FinancialAccounting) identifiable.getValue()).setValue(Double.parseDouble(value.getText()));
        ((FinancialAccounting) identifiable.getValue()).setDate(dateFinOp.getValue());
        ((FinancialAccounting) identifiable.getValue()).setFinance(financeComboBox.getValue());
        ((FinancialAccounting) identifiable.getValue()).setCourse(Double.parseDouble(course.getText()));
        ((FinancialAccounting) identifiable.getValue()).setComment(comment.getText());
    }

}
