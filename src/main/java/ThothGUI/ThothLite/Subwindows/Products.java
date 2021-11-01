package ThothGUI.ThothLite.Subwindows;

import ThothCore.ThothLite.DBData.DBData;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.Currency;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.Product;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.Purchase;
import ThothCore.ThothLite.DBData.DBDataElement.Implements.StorageCell;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Partnership;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Purchasable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;
import ThothCore.ThothLite.StructureDescription;
import ThothCore.ThothLite.ThothLite;
import ThothGUI.CloseSubwindow;
import controls.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import layout.basepane.VBox;
import window.Subwindow;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Products
        extends Subwindow {

    private ListView<Storagable> listProducts;
    private ThothLite thoth;


    public Products(
            String title
            , CloseSubwindow closeSubwindow
            , ThothLite thoth) {
        super(title);

        this.thoth = thoth;

        setCloseEvent(event -> closeSubwindow.closeSubwindow(this));

        VBox vBox = new VBox();
        vBox.setSpacing(5);

        Button add = new Button("Добавить покупку");
        add.setOnAction(this::addPurchase);

        listProducts = new ListView<>();
        listProducts.getItems().setAll((List<Storagable>)thoth.getDataFromTable(StructureDescription.Products.TABLE_NAME));
        listProducts.setCellFactory(productListView -> new ProductCell());

        vBox.getChildren().addAll(add, listProducts);

        setCenter(vBox);
    }

    private void addPurchase(ActionEvent event) {

        Purchasable purchasable = new Purchase(
                "ifsq",
                (Partnership) DBData.getInstance().getTable(StructureDescription.Partners.TABLE_NAME).getById("1"),
                "2021-10-19",
                false
        );

        purchasable.addStoring(
                new StorageCell(
                        new Product(
                                "ksf13123",
                                "asdcx12354",
                                (Listed) DBData.getInstance().getTable(StructureDescription.ProductTypes.TABLE_NAME).getById("1"),
                                78.0,
                                (Currency) DBData.getInstance().getTable(StructureDescription.Currency.TABLE_NAME).getById("1")
                        ),
                        34.0,
                        (Listed) DBData.getInstance().getTable(StructureDescription.CountTypes.TABLE_NAME).getById("1")
                )
        );
        purchasable.addStoring(
                new StorageCell(
                        new Product(
                                "lgfhio",
                                "mkxcv",
                                (Listed) DBData.getInstance().getTable(StructureDescription.ProductTypes.TABLE_NAME).getById("1"),
                                78.0,
                                (Currency) DBData.getInstance().getTable(StructureDescription.Currency.TABLE_NAME).getById("1")
                        ),
                        34.0,
                        (Listed) DBData.getInstance().getTable(StructureDescription.CountTypes.TABLE_NAME).getById("1")
                )
        );

        List<Purchasable> datas = new LinkedList<>();
        datas.add(purchasable);

        try {
            thoth.insertToTable(StructureDescription.Purchases.TABLE_NAME, datas);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
