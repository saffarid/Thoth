package ThothGUI.ThothLite.Subwindows;

import ThothCore.ThothLite.DBData.DBDataElement.Storagable;
import ThothCore.ThothLite.StructureDescription;
import ThothCore.ThothLite.ThothLite;
import ThothGUI.CloseSubwindow;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import window.Subwindow;

import java.util.List;

public class Products
        extends Subwindow {

    private ListView<Storagable> listProducts;

    public Products(
            String title
            , CloseSubwindow closeSubwindow
            , ThothLite thoth) {
        super(title);

        setCloseEvent(event -> closeSubwindow.closeSubwindow(this));

        listProducts = new ListView<>();
        listProducts.getItems().setAll((List<Storagable>)thoth.getDataFromTable(StructureDescription.Products.TABLE_NAME));
        listProducts.setCellFactory(productListView -> new ProductCell());
        setCenter(listProducts);
    }

    class ProductCell extends ListCell<Storagable> {

        @Override
        protected void updateItem(Storagable product, boolean b) {
            if(product != null){
                super.updateItem(product, b);
                setText(product.getId());
            }
        }
    }
}
