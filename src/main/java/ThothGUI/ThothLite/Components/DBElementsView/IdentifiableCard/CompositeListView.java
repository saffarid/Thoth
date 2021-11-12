package ThothGUI.ThothLite.Components.DBElementsView.IdentifiableCard;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storing;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothCore.ThothLite.Exceptions.NotContainsException;
import ThothCore.ThothLite.ThothLite;
import controls.Button;
import controls.Label;
import controls.TextField;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.List;

public class CompositeListView
        extends VBox {

    private final static String TITLE = "composite";
    private final static String STORAGABLE = "storagable";
    private final static String COUNT = "count";
    private final static String COUNT_TYPE = "count_type";
    private final static String SEARCH = "search";
    private final static String SORT = "sort";
    private final static String SORT_BY_ID = "sort_by_id";
    private final static String SORT_BY_COUNT = "sort_by_count";
    private final static String SORT_BY_COUNT_TYPE = "sort_by_count_type";

    public CompositeListView(List<Storing> items) {
        super();

        getChildren().addAll(
                createNewStoringRow()
                , createSortRow()
        );
    }

    private HBox createNewStoringRow(){
        HBox hBox = new HBox();

        hBox.setSpacing(5);
        hBox.setPadding(new Insets(2));

        hBox.getChildren().addAll(
                getLabel(STORAGABLE)
                , getStoragableComboBox()
                , getLabel(COUNT_TYPE)
                , getTextField(COUNT)
                , getCountTypeComboBox()
                , getAddButton()
        );

        return hBox;
    }

    private HBox createSortRow(){
        HBox hBox = new HBox();

        hBox.getChildren().addAll(
            getLabel(SORT)
            , getSortComboBox()
        );

        return hBox;
    }

    private Button getAddButton(){
        Button res = new Button(
                new ImageView(
                        new Image(
                                getClass().getResource(ThothGUI.thoth_styleconstants.Image.PLUS).toExternalForm(), 26, 26, true, true
                        )
                )
        );

        return res;
    }

    private ComboBox<Storagable> getStoragableComboBox(){

        ComboBox<Storagable> res = new ComboBox<>();

        try {
            res.setItems(FXCollections.observableList( (List<Storagable>) ThothLite.getInstance().getDataFromTable(AvaliableTables.STORAGABLE)));
        } catch (NotContainsException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        res.setCellFactory(storagableListView -> new StoragableCell());
        res.setButtonCell(new StoragableCell());

        res.setMinWidth(120);
        res.setPrefWidth(120);
        res.setMaxWidth(120);
        return res;
    }

    private ComboBox<Listed> getCountTypeComboBox(){
        ComboBox<Listed> res = new ComboBox<>();

        try {
            res.setItems( FXCollections.observableList( (List<Listed>) ThothLite.getInstance().getDataFromTable(AvaliableTables.COUNT_TYPES)) );
        } catch (NotContainsException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        res.setCellFactory(listedListView -> new ListedCell());
        res.setButtonCell(new ListedCell());

        res.setMinWidth(120);
        res.setPrefWidth(120);
        res.setMaxWidth(120);

        return res;
    }

    private ComboBox<String> getSortComboBox(){
        ComboBox<String> res = new ComboBox<>();

        return res;
    }

    private Label getLabel(String text){
        Label res = new Label(text);
        return res;
    }

    private TextField getTextField(String id){
        return getTextField(id, null);
    }

    private TextField getTextField(
            String id
            , String text
    ){
        TextField res = new TextField();
        res.setId(id);

        if(text != null) res.setText(text);

        return res;
    }

    private class StoragableCell
            extends ListCell<Storagable>{

        private final String TEMPLATE = "%1s-%2s";

        @Override
        protected void updateItem(Storagable storagable, boolean b) {
            if(storagable != null){
                super.updateItem(storagable, b);

                setText(
                        String.format(TEMPLATE, storagable.getId(), storagable.getName())
                );
            }
        }
    }

    private class ListedCell
            extends ListCell<Listed>{

        @Override
        protected void updateItem(Listed listed, boolean b) {
            if(listed != null){
                super.updateItem(listed, b);

                setText( listed.getValue() );
            }
        }
    }

}
