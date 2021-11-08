package ThothGUI.ThothLite.Components.DBElementsView.IdentifiableCard;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;
import controls.Label;
import controls.TextField;
import controls.Twin;
import layout.basepane.VBox;

public class StoragableCard extends IdentifiableCard {

    protected StoragableCard(Identifiable identifiable) {
        super(identifiable);

        setCenter(createContent());
    }

    private VBox createContent(){
        VBox vBox = new VBox();

        identifiable = (Storagable) identifiable;

        vBox.getChildren().addAll(
                new Twin(getLabel("article"), getTextField())
                , new Twin(getLabel("name"), getTextField())
                , new Twin(getLabel("type"), getTextField())
        );

        return vBox;
    }

    private Label getLabel(String text){
        Label res = new Label(text);

        return res;
    }

    private TextField getTextField(){
        TextField res = new TextField();

        return res;
    }

    @Override
    public void apply() {

    }

    @Override
    public void cancel() {

    }
}
