package ThothGUI.ThothLite.Components.DBElementsView.IdentifiableCard;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storing;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothCore.ThothLite.Exceptions.NotContainsException;
import ThothCore.ThothLite.ThothLite;
import ThothGUI.thoth_styleconstants.Stylesheets;
import controls.Button;
import controls.ComboBox;
import controls.Label;
import controls.TextField;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

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

    private boolean identifiableIsNew;

    private SimpleListProperty<Storing> items;

    public CompositeListView(
            List<Storing> items
            , boolean identifiableIsNew
    ) {
        super();

        this.items = new SimpleListProperty<>(FXCollections.observableList(items));
        this.identifiableIsNew = identifiableIsNew;

        getChildren().addAll(
                createNewStoringRow()
                , createSortRow()
                , createList()
        );

        getStylesheets().add(getClass().getResource(Stylesheets.IDENTIFIABLE_LIST).toExternalForm());
    }

    private HBox createNewStoringRow() {
        HBox hBox = new HBox();

        hBox.setSpacing(5);
        hBox.setPadding(new Insets(2));

        if(!identifiableIsNew){
            hBox.setDisable(true);
        }

        ComboBox<Storagable> storagableComboBox = getStoragableComboBox();
        TextField count = getTextField(COUNT);
        ComboBox<Listed> countTypeComboBox = getCountTypeComboBox();
        Button addButton = getAddButton();

        addButton.setOnAction(actionEvent -> {
            Storing newStoring = new Storing() {

                @Override
                public void setIdInTable(Object idInTable) {

                }

                @Override
                public Object getIdInTable() {
                    return null;
                }

                private Storagable storagable;
                private String id;
                private Double count;
                private Listed countType;

                @Override
                public Storagable getStoragable() {
                    return storagable;
                }

                @Override
                public void setStorageable(Storagable storageable) {
                    this.storagable = storageable;
                }

                @Override
                public String getId() {
                    return id;
                }

                @Override
                public void setId(String id) {
                    this.id = id;
                }

                @Override
                public Double getCount() {
                    return count;
                }

                @Override
                public void setCount(Double count) {
                    this.count = count;
                }

                @Override
                public Listed getCountType() {
                    return countType;
                }

                @Override
                public void setCountType(Listed countType) {
                    this.countType = countType;
                }
            };

            newStoring.setStorageable(storagableComboBox.getValue());
            newStoring.setCount( Double.parseDouble(count.getText()) );
            newStoring.setCountType( countTypeComboBox.getValue() );

            //Нужна проверка на наличие продукта в списке
            //Флаг на наличине продукта в списке
            boolean alreadyExsist = false;

            for(Storing storing : items.getValue()){
                if (storing.getStoragable().equals(newStoring.getStoragable())){
                    alreadyExsist = true;
                }
            }

            //Если продукт уже есть в списке, то не добавляем его, необходимо выдавать оповещение что продукт уже добавлен.
            if(!alreadyExsist) {
                items.add(
                        newStoring
                );
            }

            storagableComboBox.setValue(null);
            count.setText("");
            countTypeComboBox.setValue(null);
        });

        hBox.getChildren().addAll(
                getLabel(STORAGABLE)
                , storagableComboBox
                , getLabel(COUNT_TYPE)
                , count
                , countTypeComboBox
                , addButton
        );

        return hBox;
    }

    private ListView<Storing> createList() {
        ListView<Storing> res = new ListView<>();
        setMargin(res, new Insets(2));

        res.setCellFactory(storingListView -> new CompositeCell());

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

    public List<Storing> getComposite(){
        return items.getValue();
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

        res.setId(COUNT_TYPE);

        res.setMinWidth(120);
        res.setPrefWidth(120);
        res.setMaxWidth(120);

        return res;
    }

    private Label getLabel(String text) {
        Label res = new Label(text);
        return res;
    }

    private ComboBox<SORT_BY> getSortComboBox() {
        ComboBox<SORT_BY> res = new ComboBox<>();

        for (SORT_BY sort : SORT_BY.values()) {
            res.getItems().add(sort);
        }

        res.valueProperty().addListener((observableValue, sort_by, t1) -> {

            switch (res.getValue()){
                case ID_UP:{
                    items.sort((o1, o2) -> o1.getStoragable().getId().compareTo(o2.getStoragable().getId()));
                    break;
                }
                case ID_DOWN:{
                    items.sort((o1, o2) -> o2.getStoragable().getId().compareTo(o1.getStoragable().getId()));
                    break;
                }
                case COUNT_UP:{
                    items.sort((o1, o2) -> o1.getCount().compareTo(o2.getCount()));
                    break;
                }
                case COUNT_DOWN:{
                    items.sort((o1, o2) -> o2.getCount().compareTo(o1.getCount()));
                    break;
                }
                case COUNT_TYPE_UP:{
                    items.sort((o1, o2) -> o1.getCountType().getValue().compareTo(o2.getCountType().getValue()));
                    break;
                }
                case COUNT_TYPE_DOWN:{
                    items.sort((o1, o2) -> o2.getCountType().getValue().compareTo(o1.getCountType().getValue()));
                    break;
                }
            }

        });

        res.setValue(SORT_BY.ID_UP);

        res.setMinWidth(120);
        res.setPrefWidth(120);
        res.setMaxWidth(120);

        res.setCellFactory(sort_byListView -> new SortedCell());
        res.setButtonCell(new SortedCell());

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

        res.setId(STORAGABLE);

        res.setMinWidth(120);
        res.setPrefWidth(120);
        res.setMaxWidth(120);
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

        res.setMinWidth(120);
        res.setPrefWidth(120);
        res.setMaxWidth(120);

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
            extends ListCell<SORT_BY> {
        @Override
        protected void updateItem(SORT_BY sort_by, boolean b) {
            if (sort_by != null) {
                super.updateItem(sort_by, b);
                setText(sort_by.id);
            }
        }
    }

    private class CompositeCell
            extends ListCell<Storing> {

        private final static String CLASS_NAME = "composite-cell";

        public CompositeCell() {
            setPadding(new Insets(1, 0, 1, 0));
            getStyleClass().addAll(CLASS_NAME);
        }

        @Override
        protected void updateItem(Storing storing, boolean b) {
            if (storing != null) {
                super.updateItem(storing, b);
                setGraphic(new CompositeCellView(storing));
            }
        }
    }

    private class CompositeCellView
            extends BorderPane {

        private final static String CLASS_NAME = "composite-cell-content";

        private final static String TEMPLATE_NAME = "%1s-%2s";
        private final static String TEMPLATE_COUNT = "%1s: %2s";
        private final static String COUNT = "count";
        private final static String COUNT_TYPE = "count_type";

        private Storing storing;

        public CompositeCellView(Storing storing) {
            this.storing = storing;

            setLeft(createPoint());
            setCenter(createInform());

            getStyleClass().addAll(CLASS_NAME);
        }

        private Pane createPoint() {
            VBox vBox = new VBox();

            vBox.getChildren().add(
                    new ImageView(
                            new Image(
                                    getClass().getResource(ThothGUI.thoth_styleconstants.Image.POINT).toExternalForm(), 9, 9, true, true
                            )
                    )
            );

            vBox.setPadding(new Insets(0,0,0,5));
            vBox.setAlignment(Pos.CENTER);

            return vBox;
        }

        private GridPane createInform() {
            GridPane res = new GridPane();

            res.setPadding(new Insets(0, 0, 0, 10));

            res.getRowConstraints().addAll(
                    new RowConstraints(21)
            );

            res.getColumnConstraints().addAll(
                      new ColumnConstraints(120, 120, Double.MAX_VALUE, Priority.ALWAYS, HPos.LEFT, true)
                    , new ColumnConstraints(120, 120, Double.MAX_VALUE, Priority.ALWAYS, HPos.LEFT, true)
                    , new ColumnConstraints(120, 120, Double.MAX_VALUE, Priority.ALWAYS, HPos.LEFT, true)
            );

            res.add(
                    new Label(String.format(TEMPLATE_NAME, storing.getStoragable().getId(), storing.getStoragable().getName()))
                    , 0, 0
            );
            res.add(
                    new Label(String.format(TEMPLATE_COUNT, COUNT, storing.getCount() ))
                    , 1, 0
            );
            res.add(
                    new Label(storing.getCountType().getValue())
                    , 2, 0
            );

            return res;
        }

    }

}
