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
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class CompositeListView
        extends VBox {

    private final static String TITLE = "composite";
    private final static String STORAGABLE = "storagable";
    private final static String COUNT = "count";
    private final static String COUNT_TYPE = "count_type";
    private final static String SEARCH = "search";
    private final static String SORT = "sort";

    private enum SORT_BY {
        ID_UP("sort_by_id_up"),
        ID_DOWN("sort_by_id_down"),
        COUNT_UP("sort_by_count_up"),
        COUNT_DOWN("sort_by_count_down"),
        COUNT_TYPE_UP("sort_by_count_type_up"),
        COUNT_TYPE_DOWN("sort_by_count_type_down");
        private String id;

        SORT_BY(String id) {
            this.id = id;
        }
    }

    private final static String SORT_BY_ID = "sort_by_id";
    private final static String SORT_BY_COUNT = "sort_by_count";
    private final static String SORT_BY_COUNT_TYPE = "sort_by_count_type";

    private SimpleListProperty<Storing> items;

    public CompositeListView(List<Storing> items) {
        super();

        this.items = new SimpleListProperty<>( FXCollections.observableList( items ) );

        getChildren().addAll(
                createNewStoringRow()
                , createSortRow()
                , createList()
        );
    }

    private HBox createNewStoringRow() {
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

    private ListView<Storing> createList(){
        ListView<Storing> res = new ListView<>();
        setMargin(res, new Insets(2));

        res.itemsProperty().bind(items);

        return res;
    }

    private HBox createSortRow() {
        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.setPadding(new Insets(2));

        hBox.getChildren().addAll(
                getLabel(SORT)
                , getSortComboBox()
        );

        return hBox;
    }

    private Button getAddButton() {
        Button res = new Button(
                new ImageView(
                        new Image(
                                getClass().getResource(ThothGUI.thoth_styleconstants.Image.PLUS).toExternalForm(), 26, 26, true, true
                        )
                )
        );

        return res;
    }

    private ComboBox<Storagable> getStoragableComboBox() {

        ComboBox<Storagable> res = new ComboBox<>();

        try {
            res.setItems(FXCollections.observableList((List<Storagable>) ThothLite.getInstance().getDataFromTable(AvaliableTables.STORAGABLE)));
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

    private ComboBox<Listed> getCountTypeComboBox() {
        ComboBox<Listed> res = new ComboBox<>();

        try {
            res.setItems(FXCollections.observableList((List<Listed>) ThothLite.getInstance().getDataFromTable(AvaliableTables.COUNT_TYPES)));
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

    private ComboBox<SORT_BY> getSortComboBox() {
        ComboBox<SORT_BY> res = new ComboBox<>();

        for (SORT_BY sort : SORT_BY.values()) {
            res.getItems().add( sort );
        }

        res.setCellFactory(sort_byListView -> new SortedCell());
        res.setButtonCell(new SortedCell());
        return res;
    }

    private Label getLabel(String text) {
        Label res = new Label(text);
        return res;
    }

    private TextField getTextField(String id) {
        return getTextField(id, null);
    }

    private TextField getTextField(
            String id
            , String text
    ) {
        TextField res = new TextField();
        res.setId(id);

        if (text != null) res.setText(text);

        return res;
    }

    private class StoragableCell
            extends ListCell<Storagable> {

        private final String TEMPLATE = "%1s-%2s";

        @Override
        protected void updateItem(Storagable storagable, boolean b) {
            if (storagable != null) {
                super.updateItem(storagable, b);

                setText(
                        String.format(TEMPLATE, storagable.getId(), storagable.getName())
                );
            }
        }
    }

    private class ListedCell
            extends ListCell<Listed> {

        @Override
        protected void updateItem(Listed listed, boolean b) {
            if (listed != null) {
                super.updateItem(listed, b);

                setText(listed.getValue());
            }
        }
    }

    private class SortedCell
            extends ListCell<SORT_BY>{
        @Override
        protected void updateItem(SORT_BY sort_by, boolean b) {
            if(sort_by != null) {
                super.updateItem(sort_by, b);
                setText(sort_by.id);
            }
        }
    }
}
