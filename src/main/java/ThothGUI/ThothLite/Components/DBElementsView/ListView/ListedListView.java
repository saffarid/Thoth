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

    private enum ButtonText{
        APPLY("apply"),
        CANCEL("cancel");
        private String text;
        ButtonText(String text) {
            this.text = text;
        }
    }

    private List<Listed> newListed;
    private List<Listed> removedListed;

    protected Button apply;
    protected Button cancel;

    protected ListedListView(
            List<Listed> datas
            , AvaliableTables table
    ) {
        super(datas);
        this.table = table;
        newListed = new LinkedList<>();
        removedListed = new LinkedList<>();

        setBottom(createButtonBar());
    }

    public void apply() {

        try {
            ThothLite.getInstance().insertToTable(table, newListed);
        } catch (NotContainsException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void cancel() {

    }

    private ButtonBar createButtonBar(){
        ButtonBar buttonBar = new ButtonBar();
        buttonBar.setPadding(new Insets(2));
        apply = getButton(ButtonText.APPLY, event -> apply());
        cancel = getButton(ButtonText.CANCEL, event -> cancel());
        buttonBar.getButtons().addAll(
                apply
                , cancel
        );

        buttonBar.setStyle("" +
                "-fx-border-width: 1px 0 0 0;" +
                "-fx-border-color:grey;" +
                "-fx-border-style:solid;" +
                "");

        return buttonBar;
    }

    private Button getButton(
            ButtonText text
            , EventHandler<ActionEvent> event
    ){
        Button res = new Button(text.text);
        res.setId(text.text);
        res.setOnAction(event);
        return res;
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
        newListed.add(listedInstance);
    }

}
