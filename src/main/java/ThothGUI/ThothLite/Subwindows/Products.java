package ThothGUI.ThothLite.Subwindows;

import ThothCore.ThothLite.DBData.DBDataElement.Implements.Product;
import ThothCore.ThothLite.ThothLite;
import ThothGUI.CloseSubwindow;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import window.Subwindow;

public class Products
        extends Subwindow {

    private ListView<Product> listProducts;

    public Products(
            String title
            , CloseSubwindow closeSubwindow
            , ThothLite thoth) {
        super(title);

        setCloseEvent(event -> closeSubwindow.closeSubwindow(this));

        listProducts = new ListView<>();
        listProducts.getItems().setAll(thoth.getProducts());
        listProducts.setCellFactory(productListView -> new ProductCell());
        setCenter(listProducts);
    }

    class ProductCell extends ListCell<Product> {

        @Override
        protected void updateItem(Product product, boolean b) {
            if(product != null){
                super.updateItem(product, b);
                setText(product.getArticle());
            }
        }
    }
}
