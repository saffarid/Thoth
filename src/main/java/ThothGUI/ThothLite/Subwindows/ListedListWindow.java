package ThothGUI.ThothLite.Subwindows;

import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothCore.ThothLite.Exceptions.NotContainsException;
import ThothCore.ThothLite.ThothLite;
import ThothGUI.OpenSubwindow;
import ThothGUI.ThothLite.ThothLiteWindow;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import layout.basepane.VBox;
import window.Subwindow;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Окно отображения таблиц с константами
 * */
public class ListedListWindow extends Subwindow {

    private static final String STYLESHEET_PATH = "/style/identifiable-list.css";

    private List<AvaliableTables> datas;
    private ListView<AvaliableTables> datasView;

    public ListedListWindow(String title) {
        super(title);

        datas = new LinkedList<>();
        datas.add(AvaliableTables.STORING);
        datas.add(AvaliableTables.COUNT_TYPES);
        datas.add(AvaliableTables.CURRENCIES);
        datas.add(AvaliableTables.INCOMES_TYPES);
        datas.add(AvaliableTables.ORDER_STATUS);
        datas.add(AvaliableTables.PRODUCT_TYPES);

        setId(title);
        setCenter(createContent());
    }

    private VBox createContent(){
        VBox vBox = new VBox();

        vBox.setPadding(new Insets(5));

        vBox.setFillWidth(true);
        vBox.setAlignment(Pos.CENTER);

        vBox.getChildren().setAll(
                createListView()
        );

        return vBox;
    }

    protected ListView<AvaliableTables> createListView(){
        datasView = new ListView<>();
        datasView.setPadding(new Insets(2));
        datasView.getItems().setAll(datas);
        datasView.setCellFactory(tListView -> new ViewCell());

        datasView.getStylesheets().add(getClass().getResource(STYLESHEET_PATH).toExternalForm());

        return datasView;
    }

    private class ViewCell
            extends ListCell<AvaliableTables> {

        private final static String STYLE_CLASS_IDENTIFIABLE_CELL = "identifiable-cell";
        private final static String STYLE_CLASS_CELL_CONTENT = "identifiable-cell-content";

        protected ImageView icon;
        protected javafx.scene.control.Label title;
        protected ImageView edit;

        protected AvaliableTables table;

        protected ViewCell( ) {
            super();

            setOnMouseClicked(this::cellClick);

            getStyleClass().add(STYLE_CLASS_IDENTIFIABLE_CELL);
            setPadding(new Insets(1, 0, 1, 0));
        }

        private void cellClick(MouseEvent event){
            try {
                ( (OpenSubwindow) ThothLiteWindow.getInstance()).openSubwindow( new IdentifiableListWindow( (ThothLite.getInstance().getTableName(table)) , table ) );
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (NotContainsException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private BorderPane createGraphic(){
            BorderPane res = new BorderPane();

            this.icon = setImageIcon(ThothGUI.thoth_styleconstants.Image.LIST);
            this.title = new javafx.scene.control.Label();
            this.edit = setImageIcon(ThothGUI.thoth_styleconstants.Image.ARROW_RIGHT);

            res.setLeft(this.icon);
            res.setCenter(this.title);
            res.setRight(this.edit);

            res.getStyleClass().add(STYLE_CLASS_CELL_CONTENT);
            BorderPane.setMargin(res, new Insets(0));

            return res;
        }

        private ImageView setImageIcon(String url) {
            ImageView node = new ImageView();
            node.setImage(
                    new Image(getClass().getResource(url).toExternalForm(), 40, 40, true, true)
            );
            return node;
        }

        private void setTextTitle(String text) {
            this.title.setText(text);
        }

        @Override
        protected void updateItem(AvaliableTables table, boolean b) {
            if(table != null) {
                super.updateItem(table, b);
                if(this.table == null) this.table = table;
                setGraphic(createGraphic());
                try {
                    setTextTitle( (ThothLite.getInstance().getTableName(table)) );
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (NotContainsException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
