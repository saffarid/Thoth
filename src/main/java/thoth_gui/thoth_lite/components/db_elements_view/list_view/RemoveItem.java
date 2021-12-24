package thoth_gui.thoth_lite.components.db_elements_view.list_view;

import thoth_core.thoth_lite.db_data.db_data_element.properties.Identifiable;

@FunctionalInterface
public interface RemoveItem {

    void removeItem(Identifiable identifiable);

}
