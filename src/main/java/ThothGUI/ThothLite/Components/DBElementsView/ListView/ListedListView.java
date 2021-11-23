package ThothGUI.ThothLite.Components.DBElementsView.ListView;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import javafx.event.ActionEvent;

import java.util.LinkedList;
import java.util.List;

public class ListedListView extends IdentifiablesListView<Listed>{

    private List<Listed> newListed;
    private List<Listed> removedListed;

    protected ListedListView(
            List<Listed> datas
            , AvaliableTables table
    ) {
        super(datas);
        this.table = table;
        newListed = new LinkedList<>();
        removedListed = new LinkedList<>();
    }

    @Override
    protected void openCreateNewIdentifiable(ActionEvent event) {
        Listed listedInstance = new Listed() {

            private String value = "default";

            @Override
            public String getValue() {
                return value;
            }

            @Override
            public void setValue(String value) {
                this.value = value;
            }

            @Override
            public String getId() {
                return null;
            }

            @Override
            public void setId(String id) {
            }
        };
        identifiableElementList.getItems().add( listedInstance );
        newListed.add(listedInstance);
    }


}
