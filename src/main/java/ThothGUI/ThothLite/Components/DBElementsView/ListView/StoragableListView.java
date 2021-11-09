package ThothGUI.ThothLite.Components.DBElementsView.ListView;

import ThothCore.ThothLite.DBData.DBDataElement.Implements.Currency;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;
import javafx.scene.control.ListView;

import java.util.List;

public class StoragableListView extends IdentifiablesListView<Storagable>{

    public StoragableListView(List<Storagable> datas) {
        super(datas);
    }

    @Override
    protected Storagable getIdentifiableInstance() {
        return new Storagable() {

            private String id = null;
            private String name = null;
            private Double price = 0.0;
            private Currency currency = null;
            private Listed type = null;

            @Override
            public String getId() {
                return id;
            }

            @Override
            public void setId(String id) {
                this.id = id;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public void setName(String name) {
                this.name = name;
            }

            @Override
            public Double getPrice() {
                return price;
            }

            @Override
            public void setPrice(Double price) {
                this.price = price;
            }

            @Override
            public Currency getCurrency() {
                return currency;
            }

            @Override
            public void setCurrency(Currency currency) {
                this.currency = currency;
            }

            @Override
            public Listed getType() {
                return type;
            }

            @Override
            public void setType(Listed type) {
                this.type = type;
            }

            @Override
            public String toString() {
                return "$classname{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", price=" + price +
                        ", currency=" + currency +
                        ", type=" + type +
                        '}';
            }
        };
    }

}
