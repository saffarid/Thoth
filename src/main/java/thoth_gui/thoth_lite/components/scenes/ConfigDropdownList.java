package thoth_gui.thoth_lite.components.scenes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import layout.basepane.BorderPane;
import layout.custompane.NavigationMenu;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_gui.thoth_lite.components.controls.MenuButton;
import thoth_gui.thoth_lite.components.scenes.db_elements_view.list_view.IdentifiablesListView;

public class ConfigDropdownList
        extends BorderPane {

    private static ConfigDropdownList instance;

    private ConfigDropdownList() {
        super();
        setLeft(getNavigationMenu());
    }

    public static ConfigDropdownList getInstance() {
        if (instance == null) {
            instance = new ConfigDropdownList();
        }
        return instance;
    }

    private controls.MenuButton getMenuButton(AvaliableTables table) {
        return MenuButton.getInstance(
                table.name()
                , event -> setCenter(IdentifiablesListView.getInstance(table))
        );
    }

    private Node getNavigationMenu() {
        NavigationMenu menu = new NavigationMenu(
                  getMenuButton(AvaliableTables.COUNT_TYPES)
                , getMenuButton(AvaliableTables.EXPENSES_TYPES)
                , getMenuButton(AvaliableTables.INCOMES_TYPES)
                , getMenuButton(AvaliableTables.ORDER_STATUS)
                , getMenuButton(AvaliableTables.PRODUCT_TYPES)
                , getMenuButton(AvaliableTables.STORING)
        );
        return menu;
    }
}
