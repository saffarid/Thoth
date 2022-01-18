package thoth_gui.thoth_lite.components.scenes.db_elements_view.identifiable_card;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import layout.basepane.BorderPane;
import layout.basepane.VBox;
import thoth_core.thoth_lite.ThothLite;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Finance;
import thoth_core.thoth_lite.db_data.db_data_element.properties.FinancialAccounting;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Identifiable;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Typable;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_gui.Apply;
import thoth_gui.Cancel;
import thoth_gui.thoth_lite.components.controls.ButtonBar;
import thoth_gui.thoth_lite.components.controls.ComboBox;
import thoth_gui.thoth_lite.components.controls.TextField;
import window.Closeable;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;

public class FinOperationCard
        extends IdentifiableCard
        implements Apply, Cancel {

    private AvaliableTables tableCategory;
    private controls.ComboBox<Typable> category;
    private controls.TextField value;

    public FinOperationCard(AvaliableTables table) {
        super(null, table);
        this.table = table;
        extracted();

    }

    private AvaliableTables extracted() {
        if (table == AvaliableTables.EXPENSES) {
            return AvaliableTables.EXPENSES_TYPES;
        } else {
            return AvaliableTables.INCOMES_TYPES;
        }
    }

    protected Node createContent() {
        contentNode = new BorderPane();

        VBox vBox = new VBox();
        try {
            category = ComboBox.getInstance( FXCollections.observableList(ThothLite.getInstance().getDataFromTable(extracted())) );
            value = TextField.getInstance();
            vBox.getChildren().addAll(
                    category,
                    value
            );
        } catch (NotContainsException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        contentNode.setCenter(vBox);
        contentNode.setBottom(
                ButtonBar.getInstance(event -> apply(), event -> cancel())
        );

        return contentNode;
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
