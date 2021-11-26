package ThothGUI.ThothLite.Components.DBElementsView.ListView;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothCore.ThothLite.Exceptions.NotContainsException;
import ThothCore.ThothLite.ThothLite;
import ThothGUI.ThothLite.Components.DBElementsView.IdentifiableCard.IdentifiableCard;
import controls.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

public class ListedListView extends IdentifiablesListView<Listed>{


    protected ListedListView(
            List<Listed> datas
            , AvaliableTables table
    ) {
        super(datas);
        this.table = table;
    }

    @Override
    protected void openCreateNewIdentifiable(ActionEvent event) {
        Listed listedInstance = new Listed() {

            private String id = "-1";
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
                return id;
            }

            @Override
            public void setId(String id) {
            }
        };
        identifiableElementList.getItems().add( listedInstance );
    }

}
