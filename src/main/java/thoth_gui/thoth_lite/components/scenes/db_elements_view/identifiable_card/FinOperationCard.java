package thoth_gui.thoth_lite.components.scenes.db_elements_view.identifiable_card;

import controls.ComboBox;
import controls.Label;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import layout.basepane.BorderPane;
import layout.basepane.VBox;
import thoth_core.thoth_lite.ThothLite;
import thoth_core.thoth_lite.db_data.db_data_element.properties.*;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_gui.Apply;
import thoth_gui.Cancel;
import thoth_gui.thoth_lite.components.controls.ButtonBar;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Currency;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FinOperationCard
        extends IdentifiableCard
        implements Apply, Cancel {

    private enum ControlsId{
        FIN_OP_TYPE("fin-op-type"),
        VALUE("value"),
        DATE("date"),
        CURRENCY("currency"),
        COURSE("course"),
        COMMENT("comment")
        ;
        private String id;
        ControlsId(String id) {
            this.id = id;
        }
    }

    private AvaliableTables tableCategory;

    /**
     * Категория финансовой операции
     * */
    private controls.ComboBox<Typable> category;
    /**
     * Сумма финансовой операции
     * */
    private controls.TextField value;
    /**
     * Дата совершения финансовой операции
     * */
    private DatePicker dateFinOp;
    /**
     * Валюта покупки
     * */
    private controls.ComboBox<Currency> currency;
    /**
     * Курс валюты
     * */
    private Double course;
    /**
     * Комментарий к операции
     * */
    private String comment;

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

    protected Node createContent() {
        contentNode = new BorderPane();

        VBox vBox = new VBox();
            category = getComboBox(ControlsId.FIN_OP_TYPE);
            value = getTextField(ControlsId.VALUE);
            dateFinOp = thoth_gui.thoth_lite.components.controls.DatePicker.getInstance();
            vBox.getChildren().addAll(
                    createRow(getLabel(ControlsId.FIN_OP_TYPE.id), category),
                    createRow(getLabel(ControlsId.VALUE.id), value),
                    createRow(getLabel(ControlsId.DATE.id), dateFinOp)
            );

        contentNode.setCenter(vBox);
        contentNode.setBottom(
                ButtonBar.getInstance(event -> apply(), event -> cancel())
        );

        return contentNode;
    }

    private Node createRow(
            Node titleNode
            , Node enterNode
    ) {
        VBox res = new VBox();

        res.setAlignment(Pos.TOP_LEFT);
        res.setFillWidth(true);
        res.setPadding(new Insets(2));

        res.getChildren().addAll(
                titleNode
                , enterNode
        );

        return res;
    }

    private ComboBox getComboBox(ControlsId id){
        ComboBox instance = thoth_gui.thoth_lite.components.controls.ComboBox.getInstance();

        instance.setId(id.id);

        switch (id){
            case FIN_OP_TYPE:{
                try {
                    instance.setCellFactory(listedListView -> new ComboBoxListedCell());
                    instance.setButtonCell(new ComboBoxListedCell());
                    instance.setItems( FXCollections.observableList(ThothLite.getInstance().getDataFromTable(categoryTable())) );
                } catch (NotContainsException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return instance;
    }

    private Label getLabel(String text) {
        Label res = thoth_gui.thoth_lite.components.controls.Label.getInstanse(text);
        res.setMinWidth(120);
        res.setPrefWidth(120);
        res.setMaxWidth(120);
        return res;
    }

    private controls.TextField getTextField(ControlsId id) {
        controls.TextField res = thoth_gui.thoth_lite.components.controls.TextField.getInstance();
        res.setId(id.id);

        res.textProperty().addListener((observableValue, s, t1) -> {
            switch (id) {
                case VALUE: {
                    if (!t1.equals("")) {
                        Pattern pattern = Pattern.compile("^[0-9]*[.]?[0-9]*$");
                        Matcher matcher = pattern.matcher(t1);

                        if (!matcher.matches()) {
                            res.setText(s);
                        }
                    }
                }
            }
        });

        res.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1 == false) {

                switch (id) {
                    case VALUE: {
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
        ((FinancialAccounting) identifiable).setDate(LocalDate.of(2021, Month.NOVEMBER, 20));
        ((FinancialAccounting) identifiable).setFinance(null);
        ((FinancialAccounting) identifiable).setCourse(1.);
        ((FinancialAccounting) identifiable).setComment("");
    }



}
