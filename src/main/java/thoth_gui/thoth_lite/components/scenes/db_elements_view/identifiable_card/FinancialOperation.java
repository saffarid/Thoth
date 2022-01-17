package thoth_gui.thoth_lite.components.scenes.db_elements_view.identifiable_card;

import thoth_core.thoth_lite.db_data.db_data_element.properties.Finance;
import thoth_core.thoth_lite.db_data.db_data_element.properties.FinancialAccounting;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Typable;

import java.time.LocalDate;

public class FinancialOperation implements FinancialAccounting {
    @Override
    public Typable getCategory() {
        return null;
    }

    @Override
    public void setCategory(Typable typable) {

    }

    @Override
    public LocalDate getDate() {
        return null;
    }

    @Override
    public void setDate(LocalDate date) {

    }

    @Override
    public Finance getFinance() {
        return null;
    }

    @Override
    public void setFinance(Finance finance) {

    }

    @Override
    public Double getCourse() {
        return null;
    }

    @Override
    public void setCourse(Double course) {

    }

    @Override
    public Double getValue() {
        return null;
    }

    @Override
    public void setValue(Double value) {

    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public void setId(String id) {

    }

    @Override
    public String getComment() {
        return null;
    }

    @Override
    public void setComment(String comment) {

    }
}
