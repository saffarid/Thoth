package ThothGUI.thoth_lite.components.db_elements_view.list_view;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;

@FunctionalInterface
public interface RemoveItem {

    void removeItem(Identifiable identifiable);

}
