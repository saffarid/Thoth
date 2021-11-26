package ThothGUI.ThothLite.Components.DBElementsView.ListView;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Finance;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import javafx.event.ActionEvent;

import java.util.List;

public class FinanceListView
        extends IdentifiablesListView<Finance> {

    protected FinanceListView(List<Finance> datas) {
        super(datas);
    }

    @Override
    protected void openCreateNewIdentifiable(ActionEvent event) {
        Finance financeInstance = new Finance() {
            private String id = "-1";
            private String currency = "new Currency";
            private Double course = 1d;

            @Override
            public String getId() {
                return id;
            }

            @Override
            public void setId(String id) { }
            @Override
            public String getCurrency() {
                return currency;
            }
            @Override
            public void setCurrency(String currency) {
                this.currency = currency;
            }
            @Override
            public Double getCourse() {
                return course;
            }
            @Override
            public void setCourse(Double course) {
                this.course = course;
            }
        };
        identifiableElementList.getItems().add( financeInstance );
    }

}
