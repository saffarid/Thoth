package thoth_gui.thoth_lite.components.scenes;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import layout.basepane.BorderPane;
import layout.custompane.NavigationMenu;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_gui.thoth_lite.components.controls.MenuButton;
import thoth_gui.thoth_lite.components.controls.ToolsPane;
import thoth_gui.thoth_lite.components.scenes.db_elements_view.list_view.IdentifiablesListView;
import window.Closeable;

public class ConfigDropdownList
    extends ThothSceneImpl
{

    private static ConfigDropdownList instance;

    private ConfigDropdownList() {
        this.id = Scenes.SYSTEM.name();
        tools = new SimpleObjectProperty<>(createToolsNode());
        content = new SimpleObjectProperty<>(createContentNode());
    }

    public static ConfigDropdownList getInstance() {
        if (instance == null) {
            instance = new ConfigDropdownList();
        }
        return instance;
    }

    @Override
    protected BorderPane createContentNode(){
        contentNode = new BorderPane();
        contentNode.setLeft(getNavigationMenu());
        return contentNode;
    }

    @Override
    protected Node createToolsNode(){
        toolsNode = new ToolsPane(Scenes.SYSTEM.name());

        return toolsNode;
    }

    private controls.MenuButton getMenuButton(AvaliableTables table) {
        return MenuButton.getInstance(
                table.name()
                , event -> {
                    IdentifiablesListView instance = IdentifiablesListView.getInstance(table);
                    contentNode.setCenter((Node) instance.getContentProperty().getValue());
                    tools.setValue((Node) instance.getToolsProperty().getValue());
                }
        );
    }

    private Node getNavigationMenu() {
        NavigationMenu menu = new NavigationMenu(
                  getMenuButton(AvaliableTables.COUNT_TYPES)
                , getMenuButton(AvaliableTables.CURRENCIES)
                , getMenuButton(AvaliableTables.EXPENSES_TYPES)
                , getMenuButton(AvaliableTables.INCOMES_TYPES)
                , getMenuButton(AvaliableTables.ORDER_STATUS)
                , getMenuButton(AvaliableTables.PRODUCT_TYPES)
                , getMenuButton(AvaliableTables.STORING)
        );
        return menu;
    }



    @Override
    public void close() {

    }

    @Override
    public void setCloseable(Closeable closeable) {
    }
}
